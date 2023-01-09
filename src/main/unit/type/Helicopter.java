package main.unit.type;

import main.game.Player;
import main.map.Case;
import main.map.Grid;
import main.unit.*;
import main.util.OptionSelector;
import main.weapon.type.AirToGroundMissile;
import main.weapon.type.HeavyMachineGun;

public class Helicopter extends FlyingTransport {

    public static final int DAILY_ENERGY_CONSUMPTION = 2;
    public static final int CARRYING_CAPACITY = 1;


    public Helicopter(Player.Type owner) {
        super(owner, Helicopter.CARRYING_CAPACITY);
        this.addWeapon(new AirToGroundMissile());
        this.addWeapon(new HeavyMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.HELICOPTER;
    }

    @Override
    public boolean accept(Unit unit) {
        return unit instanceof OnFoot;
    }

    @Override
    public int getDailyEnergyConsumption() {
        return Helicopter.DAILY_ENERGY_CONSUMPTION;
    }

    @Override
    public OptionSelector<UnitAction> getAvailableActions(Case currentCase, Grid contextGrid) {

        final OptionSelector<UnitAction> actions = super.getAvailableActions(currentCase, contextGrid);

        boolean carryingUnit = this.isCarryingUnit();

        actions.addOption(UnitAction.DROP_UNIT, carryingUnit);

        return actions;
    }

}
