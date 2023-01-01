package main.weapon.type;

import main.unit.UnitType;
import main.weapon.Weapon;

/**
 * Classe representant une bombe
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class Bombs extends Weapon {

    public static int DEFAULT_AMMO = 3;

    /**
     * Enumeration du multiplicateur de degats en fonction du type de l'unite cible
     */
    public enum DamageMultiplier {

        ON_INFANTRY(UnitType.INFANTRY, 1f),
        ON_BAZOOKA(UnitType.BAZOOKA, 1f),
        ON_TANK(UnitType.TANK, 1f),
        ON_ANTIAIR(UnitType.ANTIAIR, 0.7f),
        ON_HELICOPTER(UnitType.HELICOPTER, 0.0f),
        ON_BOMBER(UnitType.BOMBER, 0.0f),
        ON_CONVOY(UnitType.CONVOY, 1f);

        private final UnitType unit;
        private final float multiplier;

        DamageMultiplier(UnitType unit, float multiplier) {

            this.unit = unit;
            this.multiplier = multiplier;

        }

        public static DamageMultiplier fromUnit(UnitType unit) {

            for (DamageMultiplier d : DamageMultiplier.values()) {

                if(d.unit == unit) {
                    return d;
                }

            }
            return null;

        }

        public float getMultiplier() {
            return this.multiplier;
        }

    }

    /**
     * Constructeur de Bombs
     */
    public Bombs() {

        super(Bombs.DEFAULT_AMMO);

    }

    /**
     * Verifie si la bombe peut être utilisee sur une unite cible
     * @param unitType L'unite cible
     * @return true si l'arme peut être utilisee, false sinon
     */

    // Todo : Verifier si y'a pas moyen de mettre cette fonction un cran plus haut
    @Override
    public boolean canBeUsedOn(UnitType unitType) {
        /**
         * Si une arme ne peut pas etre utilisee, le multiplicateur de degats est de 0
         * Voir {@link #getMultiplierOn(UnitType)}
         */
        return this.getMultiplierOn(unitType) != 0.0f;

    }

    /**
     * Renvoie le multiplicateur de degats d'une bombe en fonction d'une unite cible
     * @param unitType Le type de l'unite cible
     * @return Le multiplicateur de degats
     */
    @Override
    public float getMultiplierOn(UnitType unitType) {
        DamageMultiplier damage = DamageMultiplier.fromUnit(unitType);
        // Si les degats sont null, cela veut dire que la bombe ne peut pas toucher l'unite cible
        // On renvoie donc un multiplicateur valant 0
        return damage != null ? damage.getMultiplier() : 0.0f;
    }

}
