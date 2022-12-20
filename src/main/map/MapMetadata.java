package main.map;

import main.map.Grid;

import java.util.Map;

public class MapMetadata {

    private final String path;
    private final Map<String, String> metadata;

    private Grid grid;
    private boolean isLoaded;

    public MapMetadata(String path, Map<String, String> metadata) {

        this.path = path;
        this.metadata = metadata;

    }

    public String getPath() {
        return path;
    }

    public int getHeight() {
        return Integer.parseInt(metadata.get("height"));
    }

    public int getWidth() {
        return Integer.parseInt(metadata.get("width"));
    }

    public int getPlayerCount() {
        return Integer.parseInt(metadata.get("players"));
    }

    public String getName() {
        return metadata.get("name");
    }

}
