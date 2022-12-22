package main.weapon;

import main.unit.Unit;

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

    public abstract boolean canBeUsedOn(Unit.Type unitType);
    public abstract float getMultiplierOn(Unit.Type unitType);

}
