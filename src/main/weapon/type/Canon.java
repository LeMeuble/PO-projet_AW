package main.weapon.type;

import main.unit.Unit;
import main.unit.UnitType;
import main.weapon.MeleeWeapon;

public class Canon extends MeleeWeapon {

    private static final int DEFAULT_AMMO = 5;

    private enum DamageMultiplier {

        ON_INFANTRY(UnitType.INFANTRY, 0.45f),
        ON_BAZOOKA(UnitType.BAZOOKA, 0.45f),
        ON_TANK(UnitType.TANK, 0.55f),
        ON_ANTIAIR(UnitType.ANTIAIR, 0.6f),
        ON_HELICOPTER(UnitType.HELICOPTER, 0.3f),
        ON_BOMBER(UnitType.BOMBER, 0.0f),
        ON_ARTILLERY(UnitType.ARTILLERY, 0.7f),
        ON_CONVOY(UnitType.CONVOY, 0.7f),
        ON_SAMLAUNCHER(UnitType.SAMLAUNCHER, 0.7f);

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


    public Canon() {

        super();

    }

    @Override
    public int getDefaultAmmo() {
        return Canon.DEFAULT_AMMO;
    }

    @Override
    public float getMultiplierOn(Unit unit) {
        DamageMultiplier damage = DamageMultiplier.fromUnit(unit.getType());
        return damage != null ? damage.getMultiplier() : 0.0f;
    }

}
