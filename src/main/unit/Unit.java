package main.unit;

import main.Player;
import main.weapon.Weapon;

public abstract class Unit {

    int maxPM;
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





}
