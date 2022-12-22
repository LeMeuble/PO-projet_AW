package main.weapon.type;

import main.unit.Unit;
import main.weapon.Weapon;

public class Bombs extends Weapon {

    public static int DEFAULT_AMMO = 3;

    public enum DamageMultiplier {

        ON_INFANTRY(Unit.Type.INFANTRY, 1f),
        ON_BAZOOKA(Unit.Type.BAZOOKA, 1f),
        ON_TANK(Unit.Type.TANK, 1f),
        ON_DCA(Unit.Type.DCA, 0.7f),
        ON_HELICOPTER(Unit.Type.HELICOPTER, 0.0f),
        ON_BOMBARDIER(Unit.Type.BOMBARDIER, 0.0f),
        ON_CONVOY(Unit.Type.CONVOY, 1f);

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

    public Bombs() {

        super(Bombs.DEFAULT_AMMO);

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
