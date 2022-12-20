package ressources;

import java.util.Map;

public class GameMap {

    private final String path;
    private final Map<String, String> metadata;

    public GameMap(String path, Map<String, String> metadata) {

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

}
