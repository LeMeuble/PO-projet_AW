package main.unit;

import main.Player;
import main.terrain.Case;
import main.unit.type.*;
import main.weapon.Weapon;

public abstract class Unit {

    public enum Type {

        Infantry("Infanterie"),
        Bazooka("Bazooka"),
        Bombardier("Bombardier"),
        Convoy("Convoit"),
        DCA("DCA"),
        Helicopter("Helico"),
        Tank("Tank"),
        Artillery("Artillerie");

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

        public Unit newInstance(Player.Type p) {

            switch (this) {
                case Infantry:
                    return new Infantry(p);
                case Bazooka:
                    return new Bazooka(p);
                case Bombardier:
                    return new Bombardier(p);
                case Convoy:
                    return new Convoy(p);
                case DCA:
                    return new DCA(p);
                case Helicopter:
                    return new Helicopter(p);
                case Tank:
                    return new Tank(p);
                case Artillery:
                    return new Artillery(p);
            }

            return null;

        }

    }

    int maxPM;
    int PM;

    double health;

    Weapon[] weapons;
    int price; // statique / dans l'enum
    int ammo;

    int fuel;
    boolean hasPlayed;
    protected Player.Type owner;
    // Idem que pour les dégats, on utilise un tableau ? Une liste ?

    int[] movementTable;

    public Unit(Player.Type owner) {

        this.owner = owner;
        this.PM = this.maxPM;
        this.health = 10;
        this.hasPlayed = false;

    }

    public boolean canMoveTo(Case destination) {

        return true;

    }


    /**
     * Calcule des degats infliges par cette unite
     * @return
     */
    public abstract double calculateDamage();

    /**
     * Retire un certain nombre de points de vie a cette unite
     * @param amount Le nombre de points de vies a enlever
     */
    public abstract void receiveDamage(int amount);

    public abstract String getFile();

    public String toString() {

        return this.getClass().getSimpleName();

    }

    public Player.Type getOwner() {

        return this.owner;

    }

}
