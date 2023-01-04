package main.unit.type;

import main.game.Player;
import main.map.Case;
import main.map.Grid;
import main.unit.*;
import main.util.OptionSelector;
import main.weapon.type.AirToGroundMissile;
import main.weapon.type.HeavyMachineGun;
import ressources.PathUtil;

public class Helicopter extends Flying implements Transport {

    private Unit carriedUnit;


    public Helicopter(Player.Type owner) {
        super(owner);
        this.addWeapon(new AirToGroundMissile());
        this.addWeapon(new HeavyMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.HELICOPTER;
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

        boolean carryingUnit = this.isCarryingUnit();

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
