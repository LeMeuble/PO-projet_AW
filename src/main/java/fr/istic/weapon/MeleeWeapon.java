package fr.istic.weapon;

/**
 * Classe abstraite representant une arme de corps a corps (arme ayant une portee d'une case)
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public abstract class MeleeWeapon extends Weapon {

    public static final int MIN_REACH = 1;
    public static final int MAX_REACH = 1;

    /**
     * Constructeur d'une arme de corps a corps
     */
    public MeleeWeapon() {
        super();
    }

    @Override
    public int getMinReach() {
        return MeleeWeapon.MIN_REACH;
    }

    @Override
    public int getMaxReach() {
        return MeleeWeapon.MAX_REACH;
    }

}
