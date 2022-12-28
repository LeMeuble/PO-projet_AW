package main.weapon.type;

import main.unit.UnitType;
import main.weapon.Weapon;

public class Missile extends Weapon {

    public static int DEFAULT_AMMO = 2;

    public enum DamageMultiplier {

        ON_INFANTRY(UnitType.INFANTRY, 0.5f),
        ON_BAZOOKA(UnitType.BAZOOKA, 0.5f),
        ON_TANK(UnitType.TANK, 0.7f),
        ON_ANTIAIR(UnitType.ANTIAIR, 0.4f),
        ON_HELICOPTER(UnitType.HELICOPTER, 0.7f),
        ON_BOMBER(UnitType.BOMBER, 0.7f),
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

    public Missile() {

        super(Missile.DEFAULT_AMMO);

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
