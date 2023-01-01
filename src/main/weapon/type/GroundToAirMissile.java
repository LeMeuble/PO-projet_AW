package main.weapon.type;

import main.unit.UnitType;
import main.weapon.RangedWeapon;

/**
 * Classe representant un missile Sol-Air
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class GroundToAirMissile extends RangedWeapon {


    public static int DEFAULT_AMMO = 2;

    public enum DamageMultiplier {

        ON_INFANTRY(UnitType.INFANTRY, 0.0f),
        ON_BAZOOKA(UnitType.BAZOOKA, 0.0f),
        ON_TANK(UnitType.TANK, 0.0f),
        ON_ANTIAIR(UnitType.ANTIAIR, 0.0f),
        ON_HELICOPTER(UnitType.HELICOPTER, 1.2f),
        ON_BOMBER(UnitType.BOMBER, 1.2f),
        ON_CONVOY(UnitType.CONVOY, 0.0f);


        private final UnitType unit;
        private final float multiplier;

        DamageMultiplier(UnitType unit, float multiplier) {

            this.unit = unit;
            this.multiplier = multiplier;

        }

        public static GroundToAirMissile.DamageMultiplier fromUnit(UnitType unit) {

            for (GroundToAirMissile.DamageMultiplier d : GroundToAirMissile.DamageMultiplier.values()) {

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

    public GroundToAirMissile() {

        super(GroundToAirMissile.DEFAULT_AMMO);

    }

    @Override
    public boolean canBeUsedOn(UnitType unitType) {

        return this.getMultiplierOn(unitType) != 0.0f;

    }

    @Override
    public float getMultiplierOn(UnitType unitType) {
        GroundToAirMissile.DamageMultiplier damage = GroundToAirMissile.DamageMultiplier.fromUnit(unitType);
        return damage != null ? damage.getMultiplier() : 0.0f;
    }

}
