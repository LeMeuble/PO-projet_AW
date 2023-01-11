package main.unit;

import main.game.Player;
import main.map.Case;
import main.map.Grid;
import main.util.OptionSelector;

import java.util.List;

public abstract class MotorizedTransport extends Motorized implements Transport {

    private final UnitCarrier unitCarrier;

    public MotorizedTransport(Player.Type owner, int maxCarriedUnits) {
        super(owner);
        this.unitCarrier = new UnitCarrier(maxCarriedUnits);
    }

    @Override
    public boolean isCarryingUnit() {
        return this.unitCarrier.isCarryingUnit();
    }

    @Override
    public List<Unit> getCarriedUnits() {
        return this.unitCarrier.getCarriedUnits();
    }

    @Override
    public boolean isFull() {
        return this.unitCarrier.isFull();
    }

    @Override
    public void addCarriedUnit(Unit unit) {
        this.unitCarrier.addCarriedUnit(unit);
    }

    @Override
    public void removeCarriedUnit(Unit unit) {
        this.unitCarrier.removeCarriedUnit(unit);
    }

    @Override
    public abstract boolean accept(Unit unit);

    @Override
    public OptionSelector<UnitAction> getAvailableActions(Case currentCase, Grid contextGrid) {

        final OptionSelector<UnitAction> actions = super.getAvailableActions(currentCase, contextGrid);

        final List<Case> adjacentCases = contextGrid.getAdjacentCases(currentCase);

        boolean availableSpace = false;

        for (Case adjacentCase : adjacentCases) {
            Unit adjacentUnit = adjacentCase.getUnit();
            if (adjacentUnit == null) availableSpace = true;
        }

        actions.addOption(UnitAction.DROP_UNIT, availableSpace && this.isCarryingUnit());

        return actions;

    }

}
