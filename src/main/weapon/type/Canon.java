package main.weapon.type;

import main.unit.Unit;
import main.weapon.Weapon;

public class Canon extends Weapon {

    public static int DEFAULT_AMMO = 5;

    public enum DamageMultiplier {

        ON_INFANTRY(Unit.TypeLegacy.INFANTRY, 0.45f),
        ON_BAZOOKA(Unit.TypeLegacy.BAZOOKA, 0.45f),
        ON_TANK(Unit.TypeLegacy.TANK, 0.55f),
        ON_DCA(Unit.TypeLegacy.DCA, 0.6f),
        ON_HELICOPTER(Unit.TypeLegacy.HELICOPTER, 0.3f),
        ON_BOMBARDIER(Unit.TypeLegacy.BOMBARDIER, 0.0f),
        ON_CONVOY(Unit.TypeLegacy.CONVOY, 0.7f);

        private final Unit.TypeLegacy unit;
        private final float multiplier;

        DamageMultiplier(Unit.TypeLegacy unit, float multiplier) {

            this.unit = unit;
            this.multiplier = multiplier;

        }

    }

    public Canon() {

        super(Canon.DEFAULT_AMMO);

    }

}
