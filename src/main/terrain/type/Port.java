package main.terrain.type;

import main.MiniWars;
import main.game.Player;
import main.map.Case;
import main.map.Grid;
import main.terrain.Factory;
import main.terrain.TerrainType;
import main.unit.Naval;
import main.unit.UnitType;
import main.util.OptionSelector;

public class Port extends Factory {

    public Port(Player.Type owner) {
        super(owner);
    }

    public static boolean canCreateUnit(Case currentCase) {

        Grid grid = MiniWars.getInstance().getCurrentGame().getGrid();

        return grid.getAdjacentCases(currentCase)
                .stream()
                .anyMatch(c -> c.getTerrain() instanceof Water && !c.hasUnit());

    }

    @Override
    public TerrainType getType() {
        return TerrainType.PORT;
    }

    @Override
    public OptionSelector<UnitType> getUnitSelector(int money) {
        return UnitType.asSelector(money, Naval.class);
    }

}
