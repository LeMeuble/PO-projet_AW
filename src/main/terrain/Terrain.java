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
    public enum Type {

        PLAIN("Plaine"),
        FOREST("Foret"),
        MOUNTAIN("Montagne"),
        WATER("Eau"),
        FACTORY("Usine"),
        CITY("Ville"),
        HQ("QG");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public static Type fromName(String name) {

            for(Type type : Type.values()) {

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

    /**
     * Retourne le chemin vers le fichier associe au terrain
     * @return Chemin du fichier
     */
    public abstract String getFile();

}
