package main.unit.type;

import main.Player;
import main.unit.Unit;
import main.weapon.Weapon;

public class Artillery extends Unit {

    int maxPM = 5;
    int PM;
    int unitMovementType = 2;

    int maxHealth = 10;
    int health;

    Weapon[] weapons;

    int price = 6000;
    int ammo;
    int fuel;

    boolean hasPlayed;
    Player owner;
    // Idem que pour les dégats, on utilise un tableau ? Une liste ?
    int[] movementTable;

    public Artillery(Player owner) {

        super(owner);

    }

}
