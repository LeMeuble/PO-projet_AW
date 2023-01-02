package main.weapon.type;

import main.unit.Unit;
import main.unit.UnitType;
import main.weapon.RangedWeapon;

/**
 * Classe representant un missile Sol-Air
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class GroundToAirMissile extends RangedWeapon {

    private static final int DEFAULT_AMMO = 2;
    private static final int MIN_REACH = 3;
    private static final int MAX_REACH = 6;

    private enum DamageMultiplier {

        ON_INFANTRY(UnitType.INFANTRY, 0.0f),
        ON_BAZOOKA(UnitType.BAZOOKA, 0.0f),
        ON_TANK(UnitType.TANK, 0.0f),
        ON_ANTIAIR(UnitType.ANTIAIR, 0.0f),
        ON_HELICOPTER(UnitType.HELICOPTER, 1.2f),
        ON_BOMBER(UnitType.BOMBER, 1.2f),
        ON_ARTILLERY(UnitType.ARTILLERY, 0.0f),
        ON_CONVOY(UnitType.CONVOY, 0.0f),
        ON_SAMLAUNCHER(UnitType.SAMLAUNCHER, 0.0f);


        private final UnitType unitType;
        private final float multiplier;

        DamageMultiplier(UnitType unitType, float multiplier) {

            this.unitType = unitType;
            this.multiplier = multiplier;

        }

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

    @Override
    public float getMultiplierOn(Unit unit) {
        DamageMultiplier damage = DamageMultiplier.fromUnit(unit.getType());
        return damage != null ? damage.getMultiplier() : 0.0f;
    }

}
