package main.unit.type;

import main.game.Player;
import main.map.Case;
import main.map.Grid;
import main.unit.*;
import main.util.OptionSelector;

import java.util.List;

/**
 * Class representant un convoi
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class Convoy extends MotorizedTransport {

    private static final int CARRYING_CAPACITY = 1;

    public Convoy(Player.Type owner) {
        super(owner, Convoy.CARRYING_CAPACITY);
    }

    /**
     * Calcule des degats infliges par cette unite
     *
     * @return
     */
    @Override
    public UnitType getType() {
        return UnitType.CONVOY;
    }

    @Override
    public boolean accept(Unit unit) {
        return unit instanceof OnFoot;
    }

    @Override
    public OptionSelector<UnitAction> getAvailableActions(Case currentCase, Grid contextGrid) {

        final OptionSelector<UnitAction> actions = super.getAvailableActions(currentCase, contextGrid);

        final List<Case> adjacentCases = contextGrid.getAdjacentCases(currentCase);

        boolean troopNearby = false;

        for (Case adjacentCase : adjacentCases) {
            Unit adjacentUnit = adjacentCase.getUnit();
            if (adjacentUnit != null && !(adjacentUnit instanceof Convoy) && adjacentUnit.getOwner() == this.getOwner()) {
                troopNearby = true;
            }
        }

        actions.addOption(UnitAction.SUPPLY, troopNearby);

        return actions;

    }

}
