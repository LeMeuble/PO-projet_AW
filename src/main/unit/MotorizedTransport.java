package main.unit;

import main.game.Player;

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

}
