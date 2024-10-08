package fr.istic.terrain;

import fr.istic.game.Player;
import fr.istic.terrain.type.*;

/**
 * Enumeration des types de fr.istic.terrain
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public enum TerrainType {

    AIRPORT('a', Airport.class),
    BEACH('B', Beach.class),
    CITY('c', City.class),
    FOREST('F', Forest.class),
    FACTORY('f', FactoryTerrain.class),
    HQ('h', HQ.class),
    MOUNTAIN('M', Mountain.class),
    OBSTACLE('O', Obstacle.class),
    PLAIN('P', Plain.class),
    PORT('p', Port.class),
    WATER('W', Water.class);

    private final char character;
    private final Class<? extends Terrain> terrainClass;

    TerrainType(char character, Class<? extends Terrain> terrainClass) {
        this.character = character;
        this.terrainClass = terrainClass;
    }

    /**
     * Renvoie un objet representant un type de fr.istic.terrain, contenant un caractere et une classe associee, en fonction d'un
     * caractere d'entree
     *
     * @param character Un caractere
     *
     * @return Un objet TerrainType
     */
    public static TerrainType fromCharacter(char character) {

        for (TerrainType type : TerrainType.values()) {

            if (type.character == character) return type;

        }
        return null;

    }

    /**
     * Cree une nouvelle instance de fr.istic.terrain, appartenant a un joueur
     *
     * @param p Le joueur proprietaire
     *
     * @return Une nouvelle instance d'un fr.istic.terrain
     */
    public Terrain newInstance(Player.Type p) {

        try {
            // Si le fr.istic.terrain est constructible depuis une classe propriete
            if (Property.class.isAssignableFrom(this.terrainClass)) {
                return p != null ? this.terrainClass.getConstructor(Player.Type.class).newInstance(p) : null;
            }
            else {
                return this.terrainClass.newInstance();
            }
        }
        catch (Exception ignored) {
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
