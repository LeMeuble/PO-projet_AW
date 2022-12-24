package main.unit;

import main.Player;
import main.terrain.Case;
import main.terrain.Terrain;
import main.unit.type.*;
import main.weapon.Weapon;
import main.weather.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite representant une unite
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public abstract class Unit {

    /**
     * Unumeration de tous les types d'unites possibles
     * Contient leur nom en String, leur prix et leurs points de mouvements
     */
    public enum Type {

        INFANTRY("Infanterie", 1500, 3),
        BAZOOKA("Bazooka", 3500, 2),
        BOMBARDIER("Bombardier", 20000, 7),
        CONVOY("Convoit", 5000, 6),
        DCA("DCA", 6000, 6),
        HELICOPTER("Helico", 12000, 6),
        TANK("Tank", 7000, 6),
        ARTILLERY("Artillerie", 6000, 5);

        private final String name;
        private final int price;
        private final int PM;

        // Todo : Les commentaires des Enums

        Type(String name, int price, int PM) {
            this.name = name;
            this.price = price;
            this.PM = PM;
        }

        public static Type fromName(String name) {

            for(Type type : Type.values()) {

                if(type.name.equals(name)) {
                    return type;
                }

            }
            return null;

        }

        public int getPrice() {
            return this.price;
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

        public int getDefaultPM() {
            return this.PM;
        }
    }

    private int maxPM;
    private int PM;

    private double health;

    private List<Weapon> weapons;

    private int fuel;
    private boolean hasPlayed;
    private boolean hasMoved;
    private boolean isAlive;
    final private Player.Type owner;
    final private Type type;

    /**
     * Constructeur d'une unite
     * Initialise toutes les valeurs par defaut,
     * @param type
     * @param owner
     */
    public Unit(Type type, Player.Type owner) {

        this.owner = owner;
        this.PM = type.getDefaultPM();
        this.health = 10;
        this.hasPlayed = false;
        this.hasMoved = false;
        this.type = type;
        this.weapons = new ArrayList<>();
        this.isAlive = true;

    }

    public abstract boolean canMoveTo(Case destination, Weather weather);


    public abstract int getMovementCostTo(Terrain terrain, Weather weather);


    public abstract String getFile();

    public String toString() {
        return this.type.name.toLowerCase();
    }

    public Player.Type getOwner() {

        return this.owner;

    }

    public int getPM() {
        return this.PM;
    }

    public void addWeapon(Weapon w) {
        this.weapons.add(w);
    }

    /**
     * Retire un certain nombre de points de vie a cette unite
     * @param amount Le nombre de points de vies a enlever
     */
    public void receiveDamage(double amount) {
        this.health -= amount;
        if(this.health <= 0) {
            this.isAlive = false;
            System.out.println("ded");
        }
    }

    /**
     * Calcule des degats infliges par cette unite
     * @return
     */
    public double calculateDamage(float multiplier) {
        return multiplier * Math.ceil(this.health);
    }

    public void attack(Unit target) {

        Type targetType = target.getType();
        Weapon bestWeapon = this.bestWeaponAgainst(targetType);

        target.receiveDamage(calculateDamage(bestWeapon.getMultiplierOn(targetType)));

    }

    public Weapon bestWeaponAgainst(Unit.Type unitType) {

        Weapon bestWeapon = null;

        for(Weapon w : this.weapons) {

            if(bestWeapon == null && w.canBeUsedOn(unitType)) {
                bestWeapon = w;
            }
            if(w.canBeUsedOn(unitType) && w.getMultiplierOn(unitType) > bestWeapon.getMultiplierOn(unitType)) {
                bestWeapon = w;
            }

        }
        return bestWeapon;
    }

    public Type getType() {
        return this.type;
    }

    public boolean hasPlayed() {
        return this.hasPlayed;
    }

    public boolean hasMoved() {
        return this.hasMoved;
    }

    public double getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getFuel() {
        return this.fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public abstract int getMinReach();
    public abstract int getMaxReach();

    public boolean isAlive() {
        return this.isAlive;
    }

    public void setPlayed(boolean state) {
        this.hasPlayed = state;
    }
    public void setMoved(boolean state) {
        this.hasMoved = state;
    }

    public void reset() {

        this.hasMoved = false;
        this.hasPlayed = false;
        this.PM = this.type.getDefaultPM();

    }

}
