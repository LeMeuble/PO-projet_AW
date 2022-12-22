package main.weapon;

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
