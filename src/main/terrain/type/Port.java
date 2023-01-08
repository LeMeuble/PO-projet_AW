package main.terrain.type;

import main.MiniWars;
import main.game.Player;
import main.map.Case;
import main.map.Grid;
import main.terrain.Factory;
import main.terrain.Property;
import main.terrain.TerrainType;
import main.unit.Naval;
import main.unit.UnitType;
import main.util.OptionSelector;

public class Port extends Factory {

    public Port(Player.Type owner) {
        super(owner);
    }

    public static boolean canCreateUnit(Case currentCase, Player currentPlayer) {

        Property currentTerrain = (Property) currentCase.getTerrain();
        // On verifie que le terrain appartient bien au joueur selectionne
        if (currentTerrain.getOwner() == currentPlayer.getType()) {

            Grid grid = MiniWars.getInstance().getCurrentGame().getGrid();

            if (grid.getAdjacentCases(currentCase).stream().anyMatch(c -> c.getTerrain() instanceof Water && !c.hasUnit())) {
                return true;
            }

        }

        return false;

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
