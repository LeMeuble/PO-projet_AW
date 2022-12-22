package ressources;

import main.Player;
import main.map.Case;
import main.map.Grid;
import main.map.MapMetadata;
import main.terrain.Terrain;
import main.terrain.type.HQ;
import main.unit.Unit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MapParsing {


    public static List<MapMetadata> listAvailableMaps() {

        final List<MapMetadata> maps = new ArrayList<>();
        final File[] files = new File(PathUtil.MAPS_FOLDER).listFiles();

        for (File file : files) {

            if (file.isFile() && file.getName().endsWith(".meta")) {

                Map<String, String> metadata = parseMetadata(file);
                if(validateMetadata(metadata)) {

                    maps.add(new MapMetadata(file.getAbsolutePath().replace(".meta", ".awdcmap"), metadata));

                } else {

                    System.out.println("Invalid metadata for map: " + file.getName());

                }

            }
        }

        return maps;

    }

    private static Map<String, String> parseMetadata(File file) {

        Map<String, String> metadata;

        if(!file.getName().endsWith(".meta")) return null;
        try (Scanner sc = new Scanner(file)) {

            metadata = new HashMap<>();

            while (sc.hasNextLine()) {

                String line = sc.nextLine();

                String[] split = line.split("=");

                if(split.length == 2 && !split[0].isEmpty() && !split[1].isEmpty()) {

                    metadata.put(split[0].trim(), split[1].trim());

                }

            }

        } catch (FileNotFoundException ignored) {
            ignored.printStackTrace();
            System.out.println("Unable to parse metadata from file: " + file.getAbsolutePath());
            return null;
        }

        return metadata;

    }

    private static boolean validateMetadata(Map<String, String> metadata) {

        if(metadata == null) return false;

        if(!metadata.containsKey("name")) return false;
        if(!metadata.containsKey("description")) return false;
        if(!metadata.containsKey("players")) return false;
        if(!metadata.containsKey("width")) return false;
        if(!metadata.containsKey("height")) return false;
        if(!metadata.containsKey("icon")) return false;

        final File file = new File(metadata.get("icon"));
        if(!file.exists()) return false;

        try {
            Integer.parseInt(metadata.get("players"));
            Integer.parseInt(metadata.get("width"));
            Integer.parseInt(metadata.get("height"));

        } catch (NumberFormatException e) {
            return false;
        }

        return true;

    }

    private static Terrain parseTerrain(String t) {

        if(t == null) return null;

        String format = t.trim();
        if(format.startsWith("{") && format.endsWith("}")) {

            String[] split = format.substring(1, format.length() - 1).trim().split(";");

            if(split.length == 3) {

                for (int i = 0; i < split.length; i++) {
                    split[i] = split[i].trim();
                }

                Terrain.Type type = Terrain.Type.fromCharacter(split[0].charAt(0));

                if(type != null) {

                    Player.Type owner = !split[2].equals(".") ? Player.Type.fromValue(Integer.parseInt(split[2])) : null;
                    Terrain terrain = type.newInstance(owner);

                    if(terrain != null) {

                        int variation = split[1].contains(".") ? 0 : Integer.parseInt(split[1]);
                        terrain.setTextureVariation(variation);

                        return terrain;

                    }

                }

            }

        }

        return null;

    }

    private static Unit parseUnit(String u) {

        if(u == null) return null;

        String format = u.trim();
        if(format.startsWith("[") && format.endsWith("]")) {

            String[] split = format.substring(1, format.length() - 1).split(";");

            if(split.length == 2 && !split[0].equals(".")) {

                Unit.Type type = Unit.Type.fromCharacter(split[0].charAt(0));
                Player.Type player = Player.Type.fromValue(Integer.parseInt(split[1]));

                if(type != null && player != null) return type.newInstance(player);

            }

        }
        return null;

    }

    public static Grid parseMap(MapMetadata map) {

        final String path = map.getPath();
        final File file = new File(path);

        final int playerCount = map.getPlayerCount();
        final int mapWidth = map.getWidth();
        final int mapHeight = map.getHeight();

        final Case[][] grid = new Case[mapHeight][mapWidth];

        final boolean[] foundHQ = new boolean[playerCount];

        try (Scanner sc = new Scanner(file)) {

            for (int i = mapHeight - 1; i >= 0; i--) {

                if(!sc.hasNextLine()) throw new IOException("Invalid map file : Unit line " + i);
                String unitLine = sc.nextLine();

                if(!sc.hasNextLine()) throw new IOException("Invalid map file : Terrain line " + i); // If the file is not well formatted, we stop parsing
                String terrainLine = sc.nextLine();

                String [] unitParsed = unitLine.split(",");
                String [] terrainParsed = terrainLine.split(",");

                if(unitParsed.length != mapWidth || terrainParsed.length != mapWidth) {
                    throw new IOException("Invalid map file : Invalid element in unit or terrain line " + i);
                }

                for (int j = 0; j < mapWidth; j++) {

                    String unitTemp = unitParsed[j];
                    String terrainTemp = terrainParsed[j];

                    Unit unit = parseUnit(unitTemp); // Parse the unit. If the unit is null, it means that there is no unit on this case
                    Terrain terrain = parseTerrain(terrainTemp);

                    if(terrain == null) throw new IOException("Invalid map file : Invalid terrain at " + j + "c, " + i + "l");

                    if(terrain instanceof HQ) {

                        HQ hq = (HQ) terrain;

                        if(foundHQ[hq.getOwner().getValue() - 1]) throw new IOException("Invalid map file : Multiple HQ for player #" + hq.getOwner().getValue());
                        foundHQ[hq.getOwner().getValue() - 1] = true;

                    }

                    grid[i][j] = new Case(j, i, terrain);
//                    grid[i][j].setUnit(unit);

                }

            }

            for (int j = 0; j < playerCount; j++) {
                if(!foundHQ[j]) throw new IOException("Invalid map file : Missing HQ for player #" + (j + 1));
            }

        } catch (IOException ignored) {
            ignored.printStackTrace();
            System.out.println("Unable to parse map data from file: " + file.getAbsolutePath());
            return null;
        }

        System.out.println("Map parsed successfully");

        return new Grid(grid);

    }

}