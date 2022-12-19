package main.weapon;

import main.unit.Unit;

import java.util.HashMap;

public abstract class Weapon {

    private int ammo;

    public Weapon(int ammo) {

        this.ammo = ammo;

    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

}
