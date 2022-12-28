package main.terrain;

import main.Player;
import main.terrain.type.*;
import main.weather.Weather;

/**
 * Classe abstraite representant un terrain
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public abstract class Terrain {

    public enum Type {

        PLAIN('P', false, "plain", true),
        FOREST('F', false, "forest", true),
        MOUNTAIN('M', false, "mountain", true),
        WATER('W', false, "water", true),
        OBSTACLE('O', false, "obstacle", true),
        FACTORY('f', true, "factory.png", false),
        CITY('c', true, "city.png", false),
        HQ('h', true, "hq.png", false);

        private final char character;
        private final boolean isProperty;
        private final String fileName;
        private final boolean isDirectory;

        Type(char character, boolean isProperty, String fileName, boolean isDirectory) {
            this.character = character;
            this.isProperty = isProperty;
            this.fileName = fileName;
            this.isDirectory = isDirectory;
        }

        public static Type fromCharacter(char character) {

            for(Type type : Type.values()) {

                if(type.character == character) {
                    return type;
                }

            }
            return null;

        }

        public boolean isProperty() {
            return this.isProperty;
        }

        public String getFileName() {
            return this.fileName;
        }

        public Terrain newInstance(Player.Type p) {

            switch (this) {
                case PLAIN:
                    return new Plain();
                case FOREST:
                    return new Forest();
                case MOUNTAIN:
                    return new Mountain();
                case WATER:
                    return new Water();
                case OBSTACLE:
                    return new Obstacle();
                case FACTORY:
                    return p != null ? new Factory(p) : null;
                case CITY:
                    return p != null ? new City(p) : null;
                case HQ:
                    return p != null ? new HQ(p) : null;

            }

            return null;

        }

    }

    private int textureVariation;

    /**
     * Constructeur du Terrain sans variation de texture
     * La valeur est definie a 0
     */
    public Terrain() {
        this(0);
    }

    /**
     * Constructeur du Terrain avec une variation de texture
     * @param textureVariation L'entier de la variation de texture
     */
    public Terrain(int textureVariation) {

        this.textureVariation = textureVariation;

    }

    /**
     * @return Un entier representant une variation de la texture
     */
    public int getTextureVariation() {

        return this.textureVariation;

    }

    /**
     * Definit la variation de la texture
     * @param textureVariation Un entier
     */
    public void setTextureVariation(int textureVariation) {

        this.textureVariation = textureVariation;

    }

    /**
     * Retourne le chemin vers le fichier associe au terrain
     * @return Chemin du fichier
     */
    public abstract String getFile(Weather weather, boolean isFoggy);
    public abstract Terrain.Type getType();

}
