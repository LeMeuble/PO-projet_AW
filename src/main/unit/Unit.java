package main.unit;

import main.Player;
import main.terrain.Case;
import main.weapon.Weapon;

public abstract class Unit {

    int maxPM;
    int PM;

    int health;

    Weapon[] weapons;

    int price;
    int ammo;
    int fuel;

    boolean hasPlayed;
    Player owner;
    // Idem que pour les dégats, on utilise un tableau ? Une liste ?
    int[] movementTable;

    public enum UnitType {

        Infantry,
        Bazooka,
        Bombardier,
        Convoy,
        DCA,
        Helicopter,
        Tank,
        Artillery;

    }

    public Unit(Player owner) {

        this.owner = owner;
        this.PM = this.maxPM;
        this.health = 10;
        this.hasPlayed = false;

    }

    public boolean canMoveTo(Case destination) {

        return true;

    }

    public double calculateDamage() {

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

}
