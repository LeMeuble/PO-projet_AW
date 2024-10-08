package fr.istic.weapon.type;

import fr.istic.unit.Unit;
import fr.istic.unit.UnitType;
import fr.istic.weapon.RangedWeapon;

/**
 * Classe representant un mortier
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Mortar extends RangedWeapon {

    private static final int DEFAULT_AMMO = 3;
    private static final int MIN_REACH = 2;
    private static final int MAX_REACH = 3;

    /**
     * Enumeration du multiplicateur de degats en fonction de l'unite
     */
    private enum DamageMultiplier {

        ON_INFANTRY(UnitType.INFANTRY, 0.4f),
        ON_BAZOOKA(UnitType.BAZOOKA, 0.5f),
        ON_TANK(UnitType.TANK, 0.7f),
        ON_ANTIAIR(UnitType.ANTI_AIR, 0.7f),
        ON_HELICOPTER(UnitType.HELICOPTER, 0.0f),
        ON_BOMBER(UnitType.BOMBER, 0.0f),
        ON_ARTILLERY(UnitType.ARTILLERY, 0.7f),
        ON_CONVOY(UnitType.CONVOY, 0.7f),
        ON_SAMLAUNCHER(UnitType.SAM_LAUNCHER, 0.7f),
        ON_AIRCRAFT_CARRIER(UnitType.AIRCRAFT_CARRIER, 0.6f),
        ON_CORVETTE(UnitType.CORVETTE, 0.8f),
        ON_CRUISER(UnitType.CRUISER, 0.7f),
        ON_DREADNOUGHT(UnitType.DREADNOUGHT, 0.4f),
        ON_LANDINGSHIP(UnitType.LANDING_SHIP, 0.7f),
        ON_SUBMARINE(UnitType.SUBMARINE, 0.5f);


        private final UnitType unitType;
        private final float multiplier;

        DamageMultiplier(UnitType unitType, float multiplier) {

            this.unitType = unitType;
            this.multiplier = multiplier;

        }

        /**
         * Renvoie le multiplicateur de degats en fonction de l'unite cible
         *
         * @param unit L'unite cible
         *
         * @return Le multiplicateur des degats infliges a cette unite
         */
        public static Mortar.DamageMultiplier fromUnit(UnitType unit) {

            for (Mortar.DamageMultiplier d : Mortar.DamageMultiplier.values()) {

                if (d.unitType == unit) {
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
     * Constructeur de Mortar
     */
    public Mortar() {
        super();
    }

    @Override
    public int getMinReach() {
        return Mortar.MIN_REACH;
    }

    @Override
    public int getMaxReach() {
        return Mortar.MAX_REACH;
    }

    @Override
    public int getDefaultAmmo() {
        return Mortar.DEFAULT_AMMO;
    }

    /**
     * Renvoie le multiplicateur de degats infliges a une unite cible
     *
     * @param unit L'unite cible
     *
     * @return Un multiplicateur de degats, ou 0 si l'unite n'existe pas dans l'enumeration
     */
    @Override
    public float getMultiplierOn(Unit unit) {
        DamageMultiplier damage = DamageMultiplier.fromUnit(unit.getType());
        return damage != null ? damage.getMultiplier() : 0.0f;
    }

}
