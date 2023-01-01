package main.weapon.type;

import main.unit.UnitType;
import main.weapon.RangedWeapon;

/**
 * Classe representant un mortier
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class Mortar extends RangedWeapon {

    public static int DEFAULT_AMMO = 3;

    public enum DamageMultiplier {

        ON_INFANTRY(UnitType.INFANTRY, 0.4f),
        ON_BAZOOKA(UnitType.BAZOOKA, 0.5f),
        ON_TANK(UnitType.TANK, 0.7f),
        ON_ANTIAIR(UnitType.ANTIAIR, 0.7f),
        ON_HELICOPTER(UnitType.HELICOPTER, 0.0f),
        ON_BOMBER(UnitType.BOMBER, 0.0f),
        ON_ARTILLERY(UnitType.ARTILLERY, 0.7f),
        ON_CONVOY(UnitType.CONVOY, 0.7f);


        private final UnitType unit;
        private final float multiplier;

        DamageMultiplier(UnitType unit, float multiplier) {

            this.unit = unit;
            this.multiplier = multiplier;

        }

        public static Mortar.DamageMultiplier fromUnit(UnitType unit) {

            for (Mortar.DamageMultiplier d : Mortar.DamageMultiplier.values()) {

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
    public Mortar() {
        super(Mortar.DEFAULT_AMMO);
    }

    @Override
    public boolean canBeUsedOn(UnitType unitType) {

        return this.getMultiplierOn(unitType) != 0.0f;

    }

    @Override
    public float getMultiplierOn(UnitType unitType) {
        Mortar.DamageMultiplier damage = Mortar.DamageMultiplier.fromUnit(unitType);
        return damage != null ? damage.getMultiplier() : 0.0f;
    }

}
