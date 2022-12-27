package main.map;

import java.io.File;
import java.util.Map;

public class MapMetadata {

    private final String name;
    private final String description;
    private final int playerCount;
    private final int width;
    private final int height;
    private final String icon;
    private final String mapPath;


    public MapMetadata(String name, String description, int playerCount, int width, int height, String icon, String mapPath) {

        this.name = name;
        this.description = description;
        this.playerCount = playerCount;
        this.width = width;
        this.height = height;
        this.icon = icon;
        this.mapPath = mapPath;

    }


    public static MapMetadata fromMap(Map<String, String> metadata, String mapPath) {

        if(!validateMetadata(metadata)) return null;

        final String name = metadata.get("name");
        final String description = metadata.get("description");
        final int playerCount = Integer.parseInt(metadata.get("players"));
        final int width = Integer.parseInt(metadata.get("width"));
        final int height = Integer.parseInt(metadata.get("height"));
        final String icon = metadata.get("icon");

        return new MapMetadata(name, description, playerCount, width, height, icon, mapPath);

    }

    private static boolean validateMetadata(Map<String, String> metadata) {

        if (metadata == null) return false;

        if (!metadata.containsKey("name")) return false;
        if (!metadata.containsKey("description")) return false;
        if (!metadata.containsKey("players")) return false;
        if (!metadata.containsKey("width")) return false;
        if (!metadata.containsKey("height")) return false;
        if (!metadata.containsKey("icon")) return false;

        final File file = new File(metadata.get("icon"));
        if (!file.exists()) return false;

        try {
            Integer.parseInt(metadata.get("players"));
            Integer.parseInt(metadata.get("width"));
            Integer.parseInt(metadata.get("height"));
        } catch (NumberFormatException e) {
            return false;
        }

        return true;

    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getPlayerCount() {
        return this.playerCount;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getMapPath() {
        return this.mapPath;
    }

}
