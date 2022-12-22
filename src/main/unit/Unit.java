package main.unit;

import main.Player;
import main.terrain.Case;
import main.terrain.Terrain;
import main.unit.type.*;
import main.weapon.Weapon;
import main.weather.Weather;

import java.util.ArrayList;
import java.util.List;

public abstract class Unit {

    public enum Type {

        INFANTRY("Infanterie"),
        BAZOOKA("Bazooka"),
        BOMBARDIER("Bombardier"),
        CONVOY("Convoit"),
        DCA("DCA"),
        HELICOPTER("Helico"),
        TANK("Tank"),
        ARTILLERY("Artillerie");

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
                case INFANTRY:
                    return new Infantry(p);
                case BAZOOKA:
                    return new Bazooka(p);
                case BOMBARDIER:
                    return new Bombardier(p);
                case CONVOY:
                    return new Convoy(p);
                case DCA:
                    return new DCA(p);
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

    private List<Weapon> weapons;

    private int fuel;
    private boolean hasPlayed;
    private boolean hasMoved;
    private boolean isAlive;
    final private Player.Type owner;
    final private Type type;

    public Unit(Type type, Player.Type owner) {

        this.owner = owner;
        this.PM = 10;
        this.health = 10;
        this.hasPlayed = false;
        this.hasMoved = false;
        this.type = type;
        this.weapons = new ArrayList<>();
        this.isAlive = true;

    }

    public abstract boolean canMoveTo(Case destination, Weather weather);


    public abstract int getMovementCostTo(Terrain terrain, Weather weather);

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

    public abstract void inflictDamage(Unit target);

    public abstract String getFile();

    public String toString() {

        return this.getClass().getSimpleName();

    }

    public Player.Type getOwner() {

        return this.owner;

    }

    public int getPM() {
        return this.PM;
    }

}
