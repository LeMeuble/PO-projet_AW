package fr.istic.weapon.type;

import fr.istic.unit.Unit;
import fr.istic.unit.UnitType;
import fr.istic.weapon.RangedWeapon;

/**
 * Classe representant un missile Sol-Air
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class GroundToAirMissile extends RangedWeapon {

    private static final int DEFAULT_AMMO = 2;
    private static final int MIN_REACH = 3;
    private static final int MAX_REACH = 6;

    /**
     * Enumeration du multiplicateur de degats en fonction de l'unite
     */
    private enum DamageMultiplier {

        ON_INFANTRY(UnitType.INFANTRY, 0.0f),
        ON_BAZOOKA(UnitType.BAZOOKA, 0.0f),
        ON_TANK(UnitType.TANK, 0.0f),
        ON_ANTIAIR(UnitType.ANTI_AIR, 0.0f),
        ON_HELICOPTER(UnitType.HELICOPTER, 1.2f),
        ON_BOMBER(UnitType.BOMBER, 1.2f),
        ON_ARTILLERY(UnitType.ARTILLERY, 0.0f),
        ON_CONVOY(UnitType.CONVOY, 0.0f),
        ON_SAMLAUNCHER(UnitType.SAM_LAUNCHER, 0.0f),
        ON_AIRCRAFT_CARRIER(UnitType.AIRCRAFT_CARRIER, 0f),
        ON_CORVETTE(UnitType.CORVETTE, 0f),
        ON_CRUISER(UnitType.CRUISER, 0f),
        ON_DREADNOUGHT(UnitType.DREADNOUGHT, 0f),
        ON_LANDINGSHIP(UnitType.LANDING_SHIP, 0f),
        ON_SUBMARINE(UnitType.SUBMARINE, 0f);


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
        public static GroundToAirMissile.DamageMultiplier fromUnit(UnitType unit) {

            for (GroundToAirMissile.DamageMultiplier d : GroundToAirMissile.DamageMultiplier.values()) {

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
     * Constructeur de GroundToAirMissile
     */
    public GroundToAirMissile() {
        super();
    }

    @Override
    public int getMinReach() {
        return GroundToAirMissile.MIN_REACH;
    }

    @Override
    public int getMaxReach() {
        return GroundToAirMissile.MAX_REACH;
    }

    @Override
    public int getDefaultAmmo() {
        return GroundToAirMissile.DEFAULT_AMMO;
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
