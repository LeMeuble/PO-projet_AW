package main.parser;

import main.map.Case;
import main.map.Grid;
import main.map.MapMetadata;
import main.terrain.Terrain;
import main.terrain.type.HQ;
import main.unit.Unit;
import ressources.PathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MapParser {


    public static List<MapMetadata> listAvailableMaps() {

        final List<MapMetadata> maps = new ArrayList<>();
        final File[] metadataFiles = new File(PathUtil.MAPS_FOLDER).listFiles();

        if (metadataFiles != null) {

            for (File metadataFile : metadataFiles) {

                if (metadataFile.exists() && metadataFile.isFile() && metadataFile.getName().endsWith(".meta")) {

                    final String mapPath = metadataFile.getAbsolutePath().substring(0, metadataFile.getAbsolutePath().length() - 5) + PathUtil.MAP_EXTENSION;

                    if (new File(mapPath).exists()) {

                        final MapMetadata metadata = parseMetadata(metadataFile, mapPath);

                        if (metadata != null) {
                            maps.add(metadata);
                        } else {
                            System.out.println("Error while parsing metadata file " + metadataFile.getAbsolutePath());
                        }

                    } else {
                        System.out.println("Missing map file for metadata file " + metadataFile.getAbsolutePath());
                    }
                }
            }

        }

        // TODO: "Path not found" exception

        return maps;

    }

    public static Grid parseMap(MapMetadata mapMetadata) {

        final File file = new File(mapMetadata.getMapPath());

        final int playerCount = mapMetadata.getPlayerCount();
        final int mapWidth = mapMetadata.getWidth();
        final int mapHeight = mapMetadata.getHeight();

        final Case[][] grid = new Case[mapHeight][mapWidth];
        final boolean[] foundHQ = new boolean[playerCount];

        try (Scanner sc = new Scanner(file)) {

            for (int i = mapHeight - 1; i >= 0; i--) { // Inversed because the map is stored from top to bottom (starting for x=0, y=0 in the top left corner)

                if (!sc.hasNextLine())
                    throw new IOException("Invalid map file : Unit line " + i + " is missing");
                String unitLine = sc.nextLine();

                if (!sc.hasNextLine())
                    throw new IOException("Invalid map file : Terrain line " + i + " is missing"); // If the file is not well formatted, we stop parsing
                String terrainLine = sc.nextLine();

                String[] unitSplit = unitLine.split(",");
                String[] terrainSplit = terrainLine.split(",");

                if (unitSplit.length != mapWidth || terrainSplit.length != mapWidth)
                    throw new IOException("Invalid map file : Invalid element count in unit or terrain line " + (mapHeight - i));

                // Loop through cases of the line
                for (int j = 0; j < mapWidth; j++) {

                    String unitCase = unitSplit[j];
                    String terrainCase = terrainSplit[j];

                    Unit unit = Unit.parse(unitCase); // Parse the unit. If the unit is null, it means that there is no unit on this case
                    Terrain terrain = Terrain.parse(terrainCase);

                    if (terrain == null)
                        throw new IOException("Invalid map file : Invalid terrain at " + j + "c, " + (mapHeight - i) + "l");

                    if (terrain instanceof HQ) {

                        HQ hq = (HQ) terrain;

                        // Check if an HQ has already been found for this player
                        if (foundHQ[hq.getOwner().getValue() - 1])
                            throw new IOException("Invalid map file : Multiple HQ for player #" + hq.getOwner().getValue());

                        foundHQ[hq.getOwner().getValue() - 1] = true;

                    }

                    grid[i][j] = new Case(j, i, terrain);
                    grid[i][j].setUnit(unit);

                }

            }

            // Check if all HQ have been found
            for (int j = 0; j < playerCount; j++) {
                if (!foundHQ[j]) throw new IOException("Invalid map file : Missing HQ for player #" + (j + 1));
            }

        } catch (IOException ignored) {
            System.out.println("Unable to parse map data from file: " + file.getAbsolutePath());
            return null;
        }

        return new Grid(grid);

    }

    public static MapMetadata parseMetadata(File file, String mapPath) {

        if (!file.getName().endsWith(".meta")) return null;

        final Map<String, String> metadataParsing = new HashMap<>();

        try (Scanner sc = new Scanner(file)) {

            while (sc.hasNextLine()) {

                String line = sc.nextLine().trim();
                String[] split = line.split("=");

                if (split.length == 2) {

                    String key = split[0].trim();
                    String value = split[1].trim();

                    if (!key.isEmpty() || !value.isEmpty()) metadataParsing.put(key, value);

                }

            }

        } catch (FileNotFoundException ignored) {
            System.out.println("Unable to parse metadata from file: " + file.getAbsolutePath());
            return null;
        }

        return MapMetadata.fromMap(metadataParsing, mapPath);

    }

}