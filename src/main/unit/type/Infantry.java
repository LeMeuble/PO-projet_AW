package main.unit.type;

import main.Player;
import main.unit.Unit;
import main.weapon.Weapon;

public class Infantry extends Unit {

    int maxPM = 3;
    int PM;

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

    public Infantry() {

        this.PM = 3;

    }

}
