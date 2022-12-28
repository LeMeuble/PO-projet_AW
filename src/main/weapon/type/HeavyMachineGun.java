package main.weapon.type;

import main.unit.UnitType;
import main.weapon.Weapon;

public class HeavyMachineGun extends Weapon {

    public static int DEFAULT_AMMO = Integer.MAX_VALUE;

    public enum DamageMultiplier {

        ON_INFANTRY(UnitType.INFANTRY, 1f),
        ON_BAZOOKA(UnitType.BAZOOKA, 0.8f),
        ON_TANK(UnitType.TANK, 0.3f),
        ON_ANTIAIR(UnitType.ANTIAIR, 0.3f),
        ON_HELICOPTER(UnitType.HELICOPTER, 1.1f),
        ON_BOMBER(UnitType.BOMBER, 0.7f),
        ON_CONVOY(UnitType.CONVOY, 0.5f);

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

    public HeavyMachineGun() {

        super(HeavyMachineGun.DEFAULT_AMMO);

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
