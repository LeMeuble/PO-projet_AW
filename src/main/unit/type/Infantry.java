package main.unit.type;

import main.Player;
import main.unit.Unit;
import main.weapon.Weapon;

public class Infantry extends Unit {

    int maxPM = 3;
    int PM;
    int unitMovementType = 1;

    int maxHealth = 10;
    int health;

    Weapon[] weapons;

    int price = 1500;
    int ammo;
    int fuel;

    boolean hasPlayed;
    Player owner;
    // Idem que pour les dégats, on utilise un tableau ? Une liste ?
    int[] movementTable;

    public Infantry(Player owner){
            super(owner);
    }

}
