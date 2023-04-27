package fr.istic.weapon.type;

import fr.istic.unit.Unit;
import fr.istic.unit.UnitType;
import fr.istic.weapon.MeleeWeapon;

public class Torpedo extends MeleeWeapon {

    private static final int DEFAULT_AMMO = 2;

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
        ON_CORVETTE(UnitType.CORVETTE, 1f),
        ON_CRUISER(UnitType.CRUISER, 0.55f),
        ON_DREADNOUGHT(UnitType.DREADNOUGHT, 1f),
        ON_LANDINGSHIP(UnitType.LANDING_SHIP, 1f),
        ON_SUBMARINE(UnitType.SUBMARINE, 0.55f);

        private final UnitType unitType;
        private final float multiplier;

        DamageMultiplier(UnitType unitType, float multiplier) {

            this.unitType = unitType;
            this.multiplier = multiplier;

        }

        public static Torpedo.DamageMultiplier fromUnit(UnitType unit) {

            for (Torpedo.DamageMultiplier d : Torpedo.DamageMultiplier.values()) {

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
     * Constructeur de Torpedo
     */
    public Torpedo() {
        super();
    }

    @Override
    public int getDefaultAmmo() {
        return Torpedo.DEFAULT_AMMO;
    }

    @Override
    public float getMultiplierOn(Unit unit) {
        Torpedo.DamageMultiplier damage = Torpedo.DamageMultiplier.fromUnit(unit.getType());
        return damage != null ? damage.getMultiplier() : 0.0f;
    }


}
