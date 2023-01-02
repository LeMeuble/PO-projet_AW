package main.weapon.type;

import main.unit.Unit;
import main.unit.UnitType;
import main.weapon.MeleeWeapon;

public class HeavyMachineGun extends MeleeWeapon {

    private static final int DEFAULT_AMMO = Integer.MAX_VALUE;

    private enum DamageMultiplier {

        ON_INFANTRY(UnitType.INFANTRY, 1f),
        ON_BAZOOKA(UnitType.BAZOOKA, 0.8f),
        ON_TANK(UnitType.TANK, 0.3f),
        ON_ANTIAIR(UnitType.ANTIAIR, 0.3f),
        ON_HELICOPTER(UnitType.HELICOPTER, 1.1f),
        ON_BOMBER(UnitType.BOMBER, 0.7f),
        ON_ARTILLERY(UnitType.ARTILLERY, 0.5f),
        ON_CONVOY(UnitType.CONVOY, 0.5f),
        ON_SAMLAUNCHER(UnitType.SAMLAUNCHER, 0.5f);

        private final UnitType unitType;
        private final float multiplier;

        DamageMultiplier(UnitType unitType, float multiplier) {

            this.unitType = unitType;
            this.multiplier = multiplier;

        }

        public static DamageMultiplier fromUnit(UnitType unit) {

            for (DamageMultiplier d : DamageMultiplier.values()) {

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

    public HeavyMachineGun() {

        super();

    }

    @Override
    public int getDefaultAmmo() {
        return HeavyMachineGun.DEFAULT_AMMO;
    }

    @Override
    public float getMultiplierOn(Unit unit) {
        DamageMultiplier damage = DamageMultiplier.fromUnit(unit.getType());
        return damage != null ? damage.getMultiplier() : 0.0f;
    }

}
