package main.weapon.type;

import main.unit.Unit;
import main.weapon.Weapon;

public class Canon extends Weapon {

    public static int DEFAULT_AMMO = 5;

    public enum DamageMultiplier {

        ON_INFANTRY(Unit.Type.INFANTRY, 0.45f),
        ON_BAZOOKA(Unit.Type.BAZOOKA, 0.45f),
        ON_TANK(Unit.Type.TANK, 0.55f),
        ON_ANTIAIR(Unit.Type.ANTIAIR, 0.6f),
        ON_HELICOPTER(Unit.Type.HELICOPTER, 0.3f),
        ON_BOMBER(Unit.Type.BOMBER, 0.0f),
        ON_CONVOY(Unit.Type.CONVOY, 0.7f);

        private final Unit.Type unit;
        private final float multiplier;

        DamageMultiplier(Unit.Type unit, float multiplier) {

            this.unit = unit;
            this.multiplier = multiplier;

        }

    }

    public Canon() {

        super(Canon.DEFAULT_AMMO);

    }

}
