package main.unit;

import main.game.Player;
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

    private static final double HELICOPTER_SNOWY_MOVEMENT_MULTIPLIER = 2/3d;


    /**
     * Unumeration de tous les types d'unites possibles
     * Contient leur nom en String, leur prix et leurs points de mouvements
     */
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


    /**
     * @return Le joueur proprietaire de l'unite
     */
    public Player.Type getOwner() {
        return this.owner;
    }

    /**
     * @return La vie de l'unite
     */
    public double getHealth() {
        return this.health;
    }

    /**
     * Definit la vie de l'unite
     * @param health La nouvelle valeur de la vie
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Inflige des degats à l'unite
     * @param damage Le nombre de degats a infliger
     */
    public void damageBy(double damage) {
        this.health -= damage;

        // Si l'unite n'a plus de vie, on la tue
        if (this.health <= 0.0d) {
            this.isAlive = false;
            this.health = 0.0d;
        }
    }

    // Todo : Commentaire de ça et ameliorer (shitty code v2)
    public int getMovementPoint(Weather weather) {

        if(this instanceof Flying && weather == Weather.SNOWY) {
            return (int) (this.getType().getMovementPoint() * HELICOPTER_SNOWY_MOVEMENT_MULTIPLIER);
        }

        return this.getType().getMovementPoint();
    }

    /**
     * Ajoute une arme dans la liste des armes de l'unite
     * @param weapon L'arme a ajouter
     */
    public void addWeapon(Weapon weapon) {
        this.weapons.add(weapon);
    }

    /**
     * @return Le fuel de l'unite
     */
    public int getFuel() {
        return this.fuel;
    }

    /**
     * Definit le fuel de l'unite
     * @param fuel La nouvelle valeur du fuel
     */
    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    /**
     * @return true si l'unite a joue, false sinon
     */
    public boolean hasPlayed() {
        return this.hasPlayed;
    }

    /**
     * Definit si l'unite a joue ou non
     * @param hasPlayed Un boolean
     */
    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    /**
     * @return true si l'unite a bouge, false sinon
     */
    public boolean hasMoved() {
        return this.hasMoved;
    }

    /**
     * Definit si l'unite a bouge ou non
     * @param hasMoved Un boolean 
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * @return true si l'unite est en vie, false sinon
     */
    public boolean isAlive() {
        return this.isAlive;
    }

    /**
     * Definit si l'unite est vivant ou non
     * @param alive Un boolean
     */
    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    /**
     * Reinitialise les valeurs d'actions de l'unite
     */
    public void prepareForNextTurn() {
        this.hasPlayed = false;
        this.hasMoved = false;
    }

    /**
     * Permet a cette unite d'en attaquer une autre
     * Choisit automatiquement l'arme la plus efficace contre la cible
     * @param target La cible a attaquer
     */
    public void attack(Unit target) {

        Type targetType = target.getType();
        Weapon bestWeapon = this.bestWeaponAgainst(targetType);
         // Todo : Verifier si c'est les bonnes fonctions qui sont appelees
        target.damageBy(calculateDamage(bestWeapon.getMultiplierOn(targetType)));

    }

    /**
     * Calcule les degats infligees a la cible
     * @param multiplier Le multiplicateur de degats
     * @return Le nombre de degats infliges
     */
    public double calculateDamage(float multiplier) {
        return multiplier * Math.ceil(this.health);
    }

    /**
     * Choisit la meilleure arme de l'unite courante contre l'unite cible
     * @param unitType Le type de l'unite cible
     * @return La meilleure arme utilisable
     */
    public Weapon bestWeaponAgainst(Unit.Type unitType) {

        Weapon bestWeapon = null;
        // Cherche parmi toutes les armes de l'unite courante
        for (Weapon w : this.weapons) {

            //Si pas de meilleure arme, erifie si l'arme peut être utilisee contre la cible
            if (bestWeapon == null && w.canBeUsedOn(unitType)) {
                bestWeapon = w;
            }

            // Sinon, verifie si l'arme peut être utilisee sur la cible, et si son multiplicateur est plus eleve que l'arme deja selectionne
            if (w.canBeUsedOn(unitType) && w.getMultiplierOn(unitType) > bestWeapon.getMultiplierOn(unitType)) {
                bestWeapon = w;
            }

        }
        return bestWeapon;
    }

    // Todo : De la doc pour des abstracts ?
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
