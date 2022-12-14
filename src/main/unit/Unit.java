package main.unit;

import main.Player;
import main.terrain.Case;
import main.weapon.Weapon;

public abstract class Unit {

    int maxPM;
    int PM;
    int unitMovementType; // Utiliser des enums ?
    int maxHealth;
    int health;

    Weapon[] weapons;

    int price;
    int ammo;
    int fuel;

    boolean hasPlayed;
    Player owner;
    // Idem que pour les dégats, on utilise un tableau ? Une liste ?
    int[] movementTable;

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

        return 1;

    }

    public void receiveDamage(int amount) {
        // Dégats reçus en int ou en double ? Conversion avant ?
        this.health -= amount;

    }



}
