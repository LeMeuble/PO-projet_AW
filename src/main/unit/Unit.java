package main.unit;

import main.Player;
import main.map.Case;
import main.unit.type.*;
import main.weapon.Weapon;

public abstract class Unit {

    public enum Type {

        INFANTRY('i'),
        BAZOOKA('z'),
        BOMBER('b'),
        CONVOY('c'),
        ANTIAIR('d'),
        HELICOPTER('h'),
        TANK('t'),
        ARTILLERY('a');

        private final char character;

        Type(char character) {
            this.character = character;
        }

        public static Type fromCharacter(char character) {

            for(Type type : Type.values()) {

                if(type.character == character) {
                    return type;
                }

            }
            return null;

        }

        public String getName() {
            return this.name().toLowerCase();
        }

        public Unit newInstance(Player.Type p) {

            switch (this) {
                case INFANTRY:
                    return new Infantry(p);
                case BAZOOKA:
                    return new Bazooka(p);
                case BOMBER:
                    return new Bomber(p);
                case CONVOY:
                    return new Convoy(p);
                case ANTIAIR:
                    return new AntiAir(p);
                case HELICOPTER:
                    return new Helicopter(p);
                case TANK:
                    return new Tank(p);
                case ARTILLERY:
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
    final protected Player.Type owner;
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
