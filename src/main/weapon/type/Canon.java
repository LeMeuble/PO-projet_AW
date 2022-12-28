package main.weapon.type;

import main.unit.UnitType;
import main.weapon.Weapon;

public class Canon extends Weapon {

    public static int DEFAULT_AMMO = 5;

    public enum DamageMultiplier {

        ON_INFANTRY(UnitType.INFANTRY, 0.45f),
        ON_BAZOOKA(UnitType.BAZOOKA, 0.45f),
        ON_TANK(UnitType.TANK, 0.55f),
        ON_ANTIAIR(UnitType.ANTIAIR, 0.6f),
        ON_HELICOPTER(UnitType.HELICOPTER, 0.3f),
        ON_BOMBER(UnitType.BOMBER, 0.0f),
        ON_CONVOY(UnitType.CONVOY, 0.7f);

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

    public Canon() {

        super(Canon.DEFAULT_AMMO);

    }

    @Override
    public boolean canBeUsedOn(UnitType unitType) {

        return this.getMultiplierOn(unitType) != 0.0f;

    }

    @Override
    public float getMultiplierOn(UnitType unitType) {
        DamageMultiplier damage = DamageMultiplier.fromUnit(unitType);
        return damage != null ? damage.getMultiplier() : 0.0f;
    }

}
