package main.unit.type;

import main.game.Player;
import main.map.Case;
import main.map.Grid;
import main.unit.*;
import main.util.OptionSelector;
import ressources.PathUtil;

import java.util.List;

/**
 * Class representant un convoi
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class Convoy extends Motorized implements Transport {

    private Unit carriedUnit;

    public Convoy(Player.Type owner) {
        super(owner);

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
    public String getFile(int frame) {
        return PathUtil.getUnitPath(this.getType(), this.getOwner(), UnitAnimation.IDLE, !this.hasPlayed(), frame);
    }

    public Unit getCarriedUnit() {
        return carriedUnit;
    }

    public void setCarriedUnit(Unit carriedUnit) {
        this.carriedUnit = carriedUnit;
    }

    /**
     * @return true si l'unite en transporte une autre, false sinon
     */
    public boolean isCarryingUnit() {

        return this.carriedUnit != null;

    }

    @Override
    public OptionSelector<UnitAction> getAvailableActions(Case currentCase, Grid contextGrid) {
        final OptionSelector<UnitAction> actions = super.getAvailableActions(currentCase, contextGrid);

        final List<Case> adjacentCases = contextGrid.getAdjacentCases(currentCase);

        boolean troopNearby = false;
        boolean carryingUnit = this.isCarryingUnit();

        for (Case adjacentCase : adjacentCases) {
            Unit adjacentUnit = adjacentCase.getUnit();
            if (adjacentUnit != null && !(adjacentUnit instanceof Convoy) && adjacentUnit.getOwner() == this.getOwner()) {
                troopNearby = true;
            }
        }

        actions.addOption(UnitAction.SUPPLY, troopNearby);
        actions.addOption(UnitAction.DROP_UNIT, carryingUnit);

        return actions;
    }

    public String ToString() {

        if (isCarryingUnit()) {
            return "Carried unit : " + this.carriedUnit;
        }
        return "No unit carried";
    }

}
