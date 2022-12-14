package main.unit.type;

import main.Player;
import main.unit.Unit;
import main.weapon.Weapon;

public class Helicopter extends Unit {

    int maxPM = 6;
    int PM;
    int unitMovementType = 3;

    int maxHealth = 10;
    int health;

    Weapon[] weapons;

    int price = 12000;
    int ammo;
    int fuel;

    boolean hasPlayed;
    Player owner;
    // Idem que pour les dégats, on utilise un tableau ? Une liste ?
    int[] movementTable;

    public Helicopter(Player owner){
        super(owner);
    }

}
