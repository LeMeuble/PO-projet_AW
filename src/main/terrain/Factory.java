package main.terrain;

import main.game.Player;
import main.unit.UnitType;
import main.util.OptionSelector;

public abstract class Factory extends Property {

    public static final double DEFENSE_MULTIPLIER = 0.2;

    public Factory(Player.Type owner) {
        super(owner);
    }

    public double GetTerrainCover() {

        return DEFENSE_MULTIPLIER;

    }

    public abstract OptionSelector<UnitType> getUnitSelector(int money);

}
