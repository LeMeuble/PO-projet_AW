package main.unit;

import java.util.ArrayList;
import java.util.List;

public class UnitCarrier {

    private final List<Unit> carriedUnits;
    private final int maxCarriedUnits;

    public UnitCarrier(int maxCarriedUnits) {
        this.carriedUnits = new ArrayList<>();
        this.maxCarriedUnits = maxCarriedUnits;
    }

    public boolean isCarryingUnit() {
        return !this.carriedUnits.isEmpty();
    }

    public boolean isFull() {
        return this.carriedUnits.size() >= maxCarriedUnits;
    }

    public void addCarriedUnit(Unit unit) {
        if (!this.isFull())
            this.carriedUnits.add(unit);
    }

    public void removeCarriedUnit(Unit unit) {
        this.carriedUnits.remove(unit);
    }

    public List<Unit> getCarriedUnits() {
        return new ArrayList<>(this.carriedUnits);
    }

}
