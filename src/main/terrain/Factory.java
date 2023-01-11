package main.terrain;

import main.game.Player;
import main.map.Case;
import main.unit.UnitType;
import main.util.OptionSelector;

import java.lang.reflect.Method;

public abstract class Factory extends Property {

    public static final float DEFENSE_MULTIPLIER = 0.2f;

    public Factory(Player.Type owner) {
        super(owner);
    }

    public static boolean canCreateUnit(Case c) {

        if (c.getTerrain() instanceof Factory) {

            try {
                Class<? extends Factory> factoryClass = ((Factory) c.getTerrain()).getClass();
                Method canCreateUnit = factoryClass.getMethod("canCreateUnit", Case.class);
                if (canCreateUnit.getDeclaringClass() != Factory.class) {
                    return (boolean) canCreateUnit.invoke(null, c);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;

    }

    public float getTerrainCover() {

        return DEFENSE_MULTIPLIER;

    }

    public boolean anythingBuyable(int money) {
        return this.getUnitSelector(money).hasAvailableOption();
    }

    public abstract OptionSelector<UnitType> getUnitSelector(int money);
}
