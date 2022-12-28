package main.weapon.type;

import main.unit.UnitType;
import main.weapon.Weapon;

public class LightMachineGun extends Weapon {

    public static int DEFAULT_AMMO = Integer.MAX_VALUE;

    int maxAmmo = 9;

    public enum DamageMultiplier {

        ON_INFANTRY(UnitType.INFANTRY, 0.6f),
        ON_BAZOOKA(UnitType.BAZOOKA, 0.55f),
        ON_TANK(UnitType.TANK, 0.15f),
        ON_ANTIAIR(UnitType.ANTIAIR, 0.1f),
        ON_HELICOPTER(UnitType.HELICOPTER, 0.3f),
        ON_BOMBER(UnitType.BOMBER, 0.0f),
        ON_CONVOY(UnitType.CONVOY, 0.4f);

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

    public LightMachineGun() {

        super(LightMachineGun.DEFAULT_AMMO);

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
