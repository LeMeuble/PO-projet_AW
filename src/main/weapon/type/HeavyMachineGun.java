package main.weapon.type;

import main.unit.Unit;
import main.weapon.Weapon;

public class HeavyMachineGun extends Weapon {

    public static int DEFAULT_AMMO = Integer.MAX_VALUE;

    public enum DamageMultiplier {

        ON_INFANTRY(Unit.Type.INFANTRY, 1f),
        ON_BAZOOKA(Unit.Type.BAZOOKA, 0.8f),
        ON_TANK(Unit.Type.TANK, 0.3f),
        ON_ANTIAIR(Unit.Type.ANTIAIR, 0.3f),
        ON_HELICOPTER(Unit.Type.HELICOPTER, 1.1f),
        ON_BOMBER(Unit.Type.BOMBER, 0.7f),
        ON_CONVOY(Unit.Type.CONVOY, 0.5f);

        private final Unit.Type unit;
        private final float multiplier;

        DamageMultiplier(Unit.Type unit, float multiplier) {

            this.unit = unit;
            this.multiplier = multiplier;

        }

        public static DamageMultiplier fromUnit(Unit.Type unit) {

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
    public boolean canBeUsedOn(Unit.Type unitType) {

        return this.getMultiplierOn(unitType) != 0.0f;

    }

    @Override
    public float getMultiplierOn(Unit.Type unitType) {
        DamageMultiplier damage = DamageMultiplier.fromUnit(unitType);
        return damage != null ? damage.getMultiplier() : 0.0f;
    }

}
