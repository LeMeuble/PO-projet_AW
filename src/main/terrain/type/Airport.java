package main.terrain.type;

import main.game.Player;
import main.terrain.Factory;
import main.terrain.TerrainType;
import main.unit.Flying;
import main.unit.UnitType;
import main.util.OptionSelector;

public class Airport extends Factory {

    public Airport(Player.Type owner) {
        super(owner);
    }

    @Override
    public OptionSelector<UnitType> getUnitSelector(int money) {
        return UnitType.asSelector(money, Flying.class);
    }

    @Override
    public TerrainType getType() {
        return TerrainType.AIRPORT;
    }

}
