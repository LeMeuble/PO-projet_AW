package fr.istic.weapon;

import fr.istic.unit.Unit;

/**
 * Classe abstraite representant une arme et
 * ses munitions
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public abstract class Weapon {

    private int ammo;

    /**
     * Constructeur de Weapon
     */
    public Weapon() {
        this.ammo = this.getDefaultAmmo();
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

    /**
     * @return true si l'arme a des munitions
     */
    public boolean hasAmmo() {
        return ammo > 0;
    }

    /**
     * Reinitialise les munitions de l'arme a sa valeur par defaut
     */
    public void reload() {
        this.ammo = this.getDefaultAmmo();
    }

    /**
     * Verifie si l'arme peut etre utilisee sur l'unite cible
     *
     * @param unit L'unite cible
     *
     * @return true si l'arme va infliger des degats a cette unite
     */
    public boolean canBeUsedOn(Unit unit) {
        return this.getMultiplierOn(unit) > 0.0f;
    }

    public abstract int getMinReach();

    public abstract int getMaxReach();

    public abstract int getDefaultAmmo();

    public abstract float getMultiplierOn(Unit unit);

}
