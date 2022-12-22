package main.weapon.type;

import main.unit.Unit;
import main.weapon.Weapon;

public class Missile extends Weapon {

    public static int DEFAULT_AMMO = 2;

    public enum DamageMultiplier {

        ON_INFANTRY(Unit.Type.INFANTRY, 0.5f),
        ON_BAZOOKA(Unit.Type.BAZOOKA, 0.5f),
        ON_TANK(Unit.Type.TANK, 0.7f),
        ON_DCA(Unit.Type.DCA, 0.4f),
        ON_HELICOPTER(Unit.Type.HELICOPTER, 0.0f),
        ON_BOMBARDIER(Unit.Type.BOMBARDIER, 0.0f),
        ON_CONVOY(Unit.Type.CONVOY, 0.7f);



        private final Unit.Type unit;
        private final float multiplier;

        DamageMultiplier(Unit.Type unit, float multiplier) {

            this.unit = unit;
            this.multiplier = multiplier;

        }

    }

    public Missile() {

        super(Missile.DEFAULT_AMMO);

    }

}
