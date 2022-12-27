package main.unit;

import main.Player;
import main.terrain.Terrain;
import main.terrain.type.Factory;
import main.unit.type.*;
import main.weapon.Weapon;
import main.weather.Weather;

import java.util.ArrayList;
import java.util.List;

public abstract class Unit {

    public enum Type {

        INFANTRY('i', 1500, 3),
        BAZOOKA('z', 3500, 2),
        BOMBER('b', 20000, 7),
        CONVOY('c', 5000, 6),
        ANTIAIR('d', 6000, 6),
        HELICOPTER('h', 12000, 6),
        TANK('t', 7000, 6),
        ARTILLERY('a', 6000, 5);

        private final char character;
        private final int price;
        private final int movementPoint;

        Type(char character, int price, int movementPoint) {
            this.character = character;
            this.price = price;
            this.movementPoint = movementPoint;
        }

        public static Type fromCharacter(char character) {

            for (Type type : Type.values()) {

                if (type.character == character) {
                    return type;
                }

            }
            return null;

        }

        public int getPrice() {
            return this.price;
        }

        public String getName() {
            return this.name().toLowerCase();
        }

        public int getMovementPoint() {
            return this.movementPoint;
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


    protected final Player.Type owner;
    private double health;
    private List<Weapon> weapons;
    private int fuel;
    private boolean hasPlayed;
    private boolean hasMoved;
    private boolean isAlive;

    /**
     * Constructeur d'une unite
     * Initialise toutes les valeurs par defaut,
     * @param owner
     */
    public Unit(Player.Type owner) {

        this.owner = owner;
        this.health = 10;
        this.weapons = new ArrayList<>();
        this.fuel = 100;
        this.hasPlayed = false;
        this.hasMoved = false;
        this.isAlive = true;

    }


    public Player.Type getOwner() {
        return this.owner;
    }

    public double getHealth() {
        return this.health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void damageBy(double damage) {
        this.health -= damage;

        if (this.health <= 0.0d) {
            this.isAlive = false;
            this.health = 0.0d;
        }
    }

    public int getMovementPoint() { // movement could be static because never change
        return this.getType().getMovementPoint();
    }

    public void addWeapon(Weapon weapon) {
        this.weapons.add(weapon);
    }

    public int getFuel() {
        return this.fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public boolean hasPlayed() {
        return this.hasPlayed;
    }

    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    public boolean hasMoved() {
        return this.hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    public void prepareForNextTurn() {
        this.hasPlayed = false;
        this.hasMoved = false;
    }

    public void attack(Unit target) {

        Type targetType = target.getType();
        Weapon bestWeapon = this.bestWeaponAgainst(targetType);

        target.damageBy(calculateDamage(bestWeapon.getMultiplierOn(targetType)));

    }

    public double calculateDamage(float multiplier) {
        return multiplier * Math.ceil(this.health);
    }

    public Weapon bestWeaponAgainst(Unit.Type unitType) {

        Weapon bestWeapon = null;
        for (Weapon w : this.weapons) {

            if (bestWeapon == null && w.canBeUsedOn(unitType)) {
                bestWeapon = w;
            }
            if (w.canBeUsedOn(unitType) && w.getMultiplierOn(unitType) > bestWeapon.getMultiplierOn(unitType)) {
                bestWeapon = w;
            }

        }
        return bestWeapon;
    }


    public String toString() {

        return this.getClass().getSimpleName();

    }

    public abstract Type getType();

    public abstract int getMinReach();
    public abstract int getMaxReach();

    public abstract String getFile(int frame);

    public abstract boolean canMoveTo(Terrain destination, Weather weather);

    public abstract int getMovementCostTo(Terrain destination, Weather weather);

}
