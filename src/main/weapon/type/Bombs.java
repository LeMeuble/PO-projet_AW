package main.weapon.type;

import main.unit.Unit;
import main.weapon.Weapon;

public class Bombs extends Weapon {

    public static int DEFAULT_AMMO = 3;

    public enum DamageMultiplier {

        ON_INFANTRY(Unit.TypeLegacy.INFANTRY, 1f),
        ON_BAZOOKA(Unit.TypeLegacy.BAZOOKA, 1f),
        ON_TANK(Unit.TypeLegacy.TANK, 1f),
        ON_DCA(Unit.TypeLegacy.DCA, 0.7f),
        ON_HELICOPTER(Unit.TypeLegacy.HELICOPTER, 0.0f),
        ON_BOMBARDIER(Unit.TypeLegacy.BOMBARDIER, 0.0f),
        ON_CONVOY(Unit.TypeLegacy.CONVOY, 1f);

        private final Unit.TypeLegacy unit;
        private final float multiplier;

        DamageMultiplier(Unit.TypeLegacy unit, float multiplier) {

            this.unit = unit;
            this.multiplier = multiplier;

        }

    }

    public Bombs() {

        super(Bombs.DEFAULT_AMMO);

    }

}
