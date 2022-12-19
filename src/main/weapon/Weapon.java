package main.weapon;

import main.unit.Unit;

import java.util.HashMap;

public abstract class Weapon {

    private int ammo;

    public Weapon(int ammo) {

        this.ammo = ammo;

    }

    // Reste à voir comment on fait la table des dégats : un tableau ? Une liste ?
    int[] damages;
    HashMap<Unit, Double> damagesMultiplier;

}
