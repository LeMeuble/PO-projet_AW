package fr.istic.weapon.type;

import fr.istic.unit.Unit;
import fr.istic.unit.UnitType;
import fr.istic.weapon.RangedWeapon;

public class AntiShipMissile extends RangedWeapon {

    private static final int DEFAULT_AMMO = 1;
    private static final int MIN_REACH = 1;
    private static final int MAX_REACH = 2;

    /**
     * Enumeration du multiplicateur de degats en fonction de l'unite
     */
    private enum DamageMultiplier {

        ON_INFANTRY(UnitType.INFANTRY, 0.0f),
        ON_BAZOOKA(UnitType.BAZOOKA, 0.0f),
        ON_TANK(UnitType.TANK, 0.0f),
        ON_ANTIAIR(UnitType.ANTI_AIR, 0.0f),
        ON_HELICOPTER(UnitType.HELICOPTER, 0.0f),
        ON_BOMBER(UnitType.BOMBER, 0.0f),
        ON_ARTILLERY(UnitType.ARTILLERY, 0.0f),
        ON_CONVOY(UnitType.CONVOY, 0.0f),
        ON_SAMLAUNCHER(UnitType.SAM_LAUNCHER, 0.0f),
        ON_AIRCRAFT_CARRIER(UnitType.AIRCRAFT_CARRIER, 1f),
        ON_CORVETTE(UnitType.CORVETTE, 1.5f),
        ON_CRUISER(UnitType.CRUISER, 1.2f),
        ON_DREADNOUGHT(UnitType.DREADNOUGHT, 1f),
        ON_LANDINGSHIP(UnitType.LANDING_SHIP, 1f),
        ON_SUBMARINE(UnitType.SUBMARINE, 0.8f);


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
        public static AntiShipMissile.DamageMultiplier fromUnit(UnitType unit) {

            for (AntiShipMissile.DamageMultiplier d : AntiShipMissile.DamageMultiplier.values()) {

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
     * Constructeur de AntiShipMissile
     */
    public AntiShipMissile() {
        super();
    }

    @Override
    public int getMinReach() {
        return MIN_REACH;
    }

    @Override
    public int getMaxReach() {
        return MAX_REACH;
    }

    @Override
    public int getDefaultAmmo() {
        return DEFAULT_AMMO;
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
        AntiShipMissile.DamageMultiplier damage = AntiShipMissile.DamageMultiplier.fromUnit(unit.getType());
        return damage != null ? damage.getMultiplier() : 0.0f;
    }

}
