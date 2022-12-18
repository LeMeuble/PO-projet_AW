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

        Plain("Plaine"),
        Forest("Foret"),
        Mountain("Montagne"),
        Water("Eau"),
        Factory("Usine"),
        City("Ville"),
        HQ("QG");

        private String name;

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
                case Plain:
                    return new Plain();
                case Forest:
                    return new Forest();
                case Mountain:
                    return new Mountain();
                case Water:
                    return new Water();
            }

            return null;

        }

        public Terrain newInstance(Player.Type p) {

            switch (this) {
                case Factory:
                    return new Factory(p);
                case City:
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
