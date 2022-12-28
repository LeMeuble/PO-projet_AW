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
    FACTORY('f', Factory.class),
    CITY('c', City.class),
    HQ('h', HQ.class);

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
                return this.terrainClass.getConstructor(Player.Type.class).newInstance(p);
            } else {
                return this.terrainClass.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public String getDirectoryName() {
        return this.name().toLowerCase();
    }

    public String getFileName() {
        return this.name().toLowerCase() + ".png";
    }

}
