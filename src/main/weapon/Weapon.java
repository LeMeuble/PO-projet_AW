package main.weapon;

import main.unit.UnitType;

/**
 * Classe abstraite representant une arme et
 * ses munitions
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public abstract class Weapon {

    private int ammo;

    /**
     * Constructeur de Weapon
     * @param ammo Le nombre de munitions de l'arme
     */
    public Weapon(int ammo) {

        this.ammo = ammo;

    }

    /**
     * Obtenir le nombre de munitions restantes
     * pour l'arme
     *
     * @return Le nombre de munitions restantes
     */
    public int getAmmo() {
        return ammo;
    }

    /**
     * Changer le nombre de munitions pour
     * cette arme
     *
     * @param ammo Le nouveau nombre de munitions
     */
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public abstract boolean canBeUsedOn(UnitType unitType);
    public abstract float getMultiplierOn(UnitType unitType);

}
