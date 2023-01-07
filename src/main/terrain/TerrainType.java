package main.terrain;

import main.game.Player;
import main.terrain.type.*;

/**
 * Enumeration des types de terrain
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 */
public enum TerrainType {

    PLAIN('P', Plain.class),
    FOREST('F', Forest.class),
    MOUNTAIN('M', Mountain.class),
    WATER('W', Water.class),
    OBSTACLE('O', Obstacle.class),
    CITY('c', City.class),
    HQ('h', HQ.class),
    FACTORY('f', FactoryTerrain.class),
    PORT('p', Port.class),
    AIRPORT('a', Airport.class);

    private final char character;
    private final Class<? extends Terrain> terrainClass;

    TerrainType(char character, Class<? extends Terrain> terrainClass) {
        this.character = character;
        this.terrainClass = terrainClass;
    }

    public static TerrainType fromCharacter(char character) {

        for (TerrainType type : TerrainType.values()) {

            if (type.character == character) return type;

        }
        return null;

    }

    public Terrain newInstance(Player.Type p) {

        try {
            if (Property.class.isAssignableFrom(this.terrainClass)) {
                return p != null ? this.terrainClass.getConstructor(Player.Type.class).newInstance(p) : null;
            } else {
                return this.terrainClass.newInstance();
            }
        }
        catch (Exception ignored) {}

        return null;

    }

    public String getDirectoryName() {
        return this.name().toLowerCase();
    }

    public String getFileName() {
        if(this == TerrainType.AIRPORT) return "factory.png";
        if(this == TerrainType.PORT) return "factory.png";
        return this.name().toLowerCase() + ".png";
    }

}
