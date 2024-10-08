package fr.istic.parser;

import fr.istic.map.Case;
import fr.istic.map.Coordinate;
import fr.istic.map.Grid;
import fr.istic.map.MapMetadata;
import fr.istic.terrain.Terrain;
import fr.istic.terrain.type.HQ;
import fr.istic.unit.Unit;
import fr.istic.PathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Classe servant a fr.istic.parser une carte
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class MapParser {

    /**
     * @return Une liste de toutes les cartes disponibles
     */
    public static List<MapMetadata> listAvailableMaps() {

        final List<MapMetadata> maps = new ArrayList<>();
        final File[] metadataFiles = new File(PathUtil.MAPS_FOLDER).listFiles();

        // S'il existe au moins un fichier dans le dossier
        if (metadataFiles != null) {

            // Pour chaque fichier
            for (File metadataFile : metadataFiles) {

                // Si ce fichier existe, et que c'est un fichier avec l'extension ".meta"
                if (metadataFile.exists() && metadataFile.isFile() && metadataFile.getName().endsWith(".meta")) {

                    // On recupere le chemin de ce fichier
                    final String mapPath = metadataFile.getAbsolutePath().substring(0, metadataFile.getAbsolutePath().length() - 5) + PathUtil.MAP_EXTENSION;

                    // Si ce chemin existe
                    if (new File(mapPath).exists()) {

                        // On parse ses metadonnees
                        final MapMetadata metadata = parseMetadata(metadataFile, mapPath);

                        // Si les metadonnees existent, on les ajoute a la liste des cartes
                        if (metadata != null) {
                            maps.add(metadata);
                        }
                        else {
                            System.out.println("Error while parsing metadata file " + metadataFile.getAbsolutePath());
                        }

                    }
                    else {
                        System.out.println("Missing fr.istic.map file for metadata file " + metadataFile.getAbsolutePath());
                    }
                }
            }

        }

        return maps;

    }

    /**
     * Methode statique
     * Parse une carte. Cree la grille de jeu, y ajoute toutes les cases, ainsi que les unites qu'elles contiennent
     *
     * @param mapMetadata La fr.istic.map a construire
     *
     * @return une grille de jeu
     */
    public static Grid parseMap(MapMetadata mapMetadata) {

        final File file = new File(mapMetadata.getMapPath());

        final int playerCount = mapMetadata.getPlayerCount();
        final int mapWidth = mapMetadata.getWidth();
        final int mapHeight = mapMetadata.getHeight();

        final Case[][] grid = new Case[mapHeight][mapWidth];
        final boolean[] foundHQ = new boolean[playerCount];

        try (Scanner sc = new Scanner(file)) {

            for (int i = mapHeight - 1; i >= 0; i--) { // Inversed because the fr.istic.map is stored from top to bottom (starting for x=0, y=0 in the top left corner)

                if (!sc.hasNextLine())
                    throw new IOException("Invalid fr.istic.map file : Unit line " + i + " is missing");
                String unitLine = sc.nextLine();

                if (!sc.hasNextLine())
                    throw new IOException("Invalid fr.istic.map file : Terrain line " + i + " is missing"); // If the file is not well formatted, we stop parsing
                String terrainLine = sc.nextLine();

                String[] unitSplit = unitLine.split(",");
                String[] terrainSplit = terrainLine.split(",");

                if (unitSplit.length != mapWidth || terrainSplit.length != mapWidth)
                    throw new IOException("Invalid fr.istic.map file : Invalid element count in fr.istic.unit or fr.istic.terrain line " + (mapHeight - i));

                // Loop through cases of the line
                for (int j = 0; j < mapWidth; j++) {

                    String unitCase = unitSplit[j];
                    String terrainCase = terrainSplit[j];

                    Unit unit = Unit.parse(unitCase); // Parse the fr.istic.unit. If the fr.istic.unit is null, it means that there is no fr.istic.unit on this case
                    Terrain terrain = Terrain.parse(terrainCase);

                    if (terrain == null)
                        throw new IOException("Invalid fr.istic.map file : Invalid fr.istic.terrain at " + j + "c, " + (mapHeight - i) + "l");

                    if (terrain instanceof HQ) {

                        HQ hq = (HQ) terrain;

                        // Check if an HQ has already been found for this player
                        if (foundHQ[hq.getOwner().getValue() - 1])
                            throw new IOException("Invalid fr.istic.map file : Multiple HQ for player #" + hq.getOwner().getValue());

                        foundHQ[hq.getOwner().getValue() - 1] = true;

                    }

                    Coordinate coordinate = new Coordinate(j, i);

                    grid[i][j] = new Case(coordinate, terrain);
                    grid[i][j].setUnit(unit);

                }

            }

            // Check if all HQ have been found
            for (int j = 0; j < playerCount; j++) {
                if (!foundHQ[j]) throw new IOException("Invalid fr.istic.map file : Missing HQ for player #" + (j + 1));
            }

        }
        catch (IOException ignored) {
            System.out.println("Unable to parse fr.istic.map data from file: " + file.getAbsolutePath());
            ignored.printStackTrace();
            return null;
        }

        return new Grid(grid);

    }

    /**
     * Methode statique
     * Parse les meta donnees d'un fichier
     *
     * @param file    Le fichier a fr.istic.parser
     * @param mapPath Le chemin vers la carte
     *
     * @return Un objet MapMetadata
     */
    public static MapMetadata parseMetadata(File file, String mapPath) {

        if (!file.getName().endsWith(".meta")) return null;

        final Map<String, String> metadataParsing = new HashMap<>();

        try (Scanner sc = new Scanner(file)) {
            // Tant qu'on n'est pas au bout du fichier
            while (sc.hasNextLine()) {

                // On coupe la ligne au niveau du "="
                String line = sc.nextLine().trim();
                String[] split = line.split("=");

                if (split.length == 2) {
                    // Les 2 morceau de ligne seront la cle et la valeur dans la fr.istic.map
                    String key = split[0].trim();
                    String value = split[1].trim();

                    if (!key.isEmpty() || !value.isEmpty()) metadataParsing.put(key, value);

                }

            }

        }
        catch (FileNotFoundException ignored) {
            System.out.println("Unable to parse metadata from file: " + file.getAbsolutePath());
            return null;
        }

        return MapMetadata.fromMap(metadataParsing, mapPath);

    }

}