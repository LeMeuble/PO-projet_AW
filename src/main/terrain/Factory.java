package main.terrain;

import main.game.Player;
import main.unit.UnitType;
import main.util.OptionSelector;

public abstract class Factory extends Property {

    public Factory(Player.Type owner) {
        super(owner);
    }

    public abstract OptionSelector<UnitType> getUnitSelector(int money);

}
