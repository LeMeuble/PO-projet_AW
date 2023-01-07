package main.unit.type;

import main.game.Player;
import main.unit.Naval;
import main.unit.Transport;
import main.unit.Unit;
import main.unit.UnitType;

public class AircraftCarrier extends Naval implements Transport {

    private Unit carriedUnit;

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public AircraftCarrier(Player.Type owner) {
        super(owner);
    }

    @Override
    public UnitType getType() {
        return UnitType.DREADNOUGHT;
    }

    @Override
    public Unit getCarriedUnit() {
        return this.carriedUnit;
    }

    @Override
    public void setCarriedUnit(Unit carriedUnit) {
        this.carriedUnit = carriedUnit;
    }

    @Override
    public boolean isCarryingUnit() {
        return carriedUnit != null;
    }

}
