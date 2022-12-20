package main.weapon.type;

import main.unit.Unit;
import main.weapon.Weapon;

public class LightMachineGun extends Weapon {

    public static int DEFAULT_AMMO = Integer.MAX_VALUE;

    int maxAmmo = 9;

    public enum DamageMultiplier {

        ON_INFANTRY(Unit.TypeLegacy.INFANTRY, 0.6f),
        ON_BAZOOKA(Unit.TypeLegacy.BAZOOKA, 0.55f),
        ON_TANK(Unit.TypeLegacy.TANK, 0.15f),
        ON_DCA(Unit.TypeLegacy.DCA, 0.1f),
        ON_HELICOPTER(Unit.TypeLegacy.HELICOPTER, 0.3f),
        ON_BOMBARDIER(Unit.TypeLegacy.BOMBARDIER, 0.0f),
        ON_CONVOY(Unit.TypeLegacy.CONVOY, 0.4f);

        private final Unit.TypeLegacy unit;
        private final float multiplier;

        DamageMultiplier(Unit.TypeLegacy unit, float multiplier) {

            this.unit = unit;
            this.multiplier = multiplier;

        }

    }

    public LightMachineGun() {

        super(LightMachineGun.DEFAULT_AMMO);

    }

}
