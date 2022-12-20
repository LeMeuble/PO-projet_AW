package main.terrain;

import main.Player;
import main.terrain.type.*;

/**
 * Classe abstraite representant un terrain
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public abstract class Terrain {

    /**
     * Enumeration de tous les types de terrains existants
     */
    public enum TypeLegacy {

        PLAIN("Plaine"),
        FOREST("Foret"),
        MOUNTAIN("Montagne"),
        WATER("Eau"),
        FACTORY("Usine"),
        CITY("Ville"),
        HQ("QG");

        private final String name;

        TypeLegacy(String name) {
            this.name = name;
        }

        public static TypeLegacy fromName(String name) {

            for(TypeLegacy type : TypeLegacy.values()) {

                if(type.name.equals(name)) {
                    return type;
                }

            }
            return null;

        }

        public Terrain newInstance() {

            switch (this) {
                case PLAIN:
                    return new Plain();
                case FOREST:
                    return new Forest();
                case MOUNTAIN:
                    return new Mountain();
                case WATER:
                    return new Water();
            }

            return null;

        }

        public Terrain newInstance(Player.Type p) {

            switch (this) {
                case FACTORY:
                    return new Factory(p);
                case CITY:
                    return new City(p);
                case HQ:
                    return new HQ(p);
            }

            return newInstance();

        }

    }

    public enum Type {

        PLAIN('P', false),
        FOREST('F', false),
        MOUNTAIN('M', false),
        WATER('W', false),
        OSBSTACLE('O', false),
        FACTORY('f', true),
        CITY('c', true),
        HQ('h', true);

        private final char character;
        private final boolean isProperty;

        Type(char character, boolean isProperty) {
            this.character = character;
            this.isProperty = isProperty;
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

        public Terrain newInstance() {

            switch (this) {
                case PLAIN:
                    return new Plain();
                case FOREST:
                    return new Forest();
                case MOUNTAIN:
                    return new Mountain();
                case WATER:
                    return new Water();
            }

            return null;

        }

        public Terrain newInstance(Player.Type p) {

            switch (this) {
                case FACTORY:
                    return new Factory(p);
                case CITY:
                    return new City(p);
                case HQ:
                    return new HQ(p);
            }

            return newInstance();

        }

    }

    private int textureVariation;

    public Terrain() {

        this(0);

    }

    public Terrain(int textureVariation) {

        this.textureVariation = 0;

    }

    /**
     * Retourne le chemin vers le fichier associe au terrain
     * @return Chemin du fichier
     */
    public abstract String getFile();

    public int getTextureVariation() {

        return this.textureVariation;

    }

    public void setTextureVariation(int textureVariation) {

        this.textureVariation = textureVariation;

    }

}
