package main.weapon.type;

import main.unit.Unit;
import main.weapon.Weapon;

public class HeavyMachineGun extends Weapon {

    public static int DEFAULT_AMMO = Integer.MAX_VALUE;

    public enum DamageMultiplier {

        ON_INFANTRY(Unit.TypeLegacy.INFANTRY, 1f),
        ON_BAZOOKA(Unit.TypeLegacy.BAZOOKA, 0.8f),
        ON_TANK(Unit.TypeLegacy.TANK, 0.3f),
        ON_DCA(Unit.TypeLegacy.DCA, 0.3f),
        ON_HELICOPTER(Unit.TypeLegacy.HELICOPTER, 1.1f),
        ON_BOMBARDIER(Unit.TypeLegacy.BOMBARDIER, 0.7f),
        ON_CONVOY(Unit.TypeLegacy.CONVOY, 0.5f);

        private final Unit.TypeLegacy unit;
        private final float multiplier;

        DamageMultiplier(Unit.TypeLegacy unit, float multiplier) {

            this.unit = unit;
            this.multiplier = multiplier;

        }

    }

    public HeavyMachineGun() {

        super(HeavyMachineGun.DEFAULT_AMMO);

    }

}
