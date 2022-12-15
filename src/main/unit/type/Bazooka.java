package main.unit.type;

import main.Player;
import main.unit.OnFoot;
import main.unit.Unit;
import main.weapon.Weapon;
import ressources.Chemins;

public class Bazooka extends OnFoot {


    int maxPM = 2;
    int PM;

    int maxHealth = 10;
    int health;
    int unitMovementType = 1;

    Weapon[] weapons;

    int price = 3500;
    int ammo;
    int fuel;

    boolean hasPlayed;
    Player owner;
    // Idem que pour les dégats, on utilise un tableau ? Une liste ?
    int[] movementTable;

    public Bazooka(Player owner){
        super(owner);
    }
}
