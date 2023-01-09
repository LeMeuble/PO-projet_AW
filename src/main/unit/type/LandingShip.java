package main.unit.type;

import main.game.Player;
import main.unit.*;

public class LandingShip extends NavalTransport {

    public static final int DAILY_ENERGY_CONSUMPTION = 2;
    public static final int CARRYING_CAPACITY = 2;

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public LandingShip(Player.Type owner) {
        super(owner, LandingShip.CARRYING_CAPACITY);
    }

    @Override
    public UnitType getType() {
        return UnitType.LANDING_SHIP;
    }

    @Override
    public int getDailyEnergyConsumption() {
        return LandingShip.DAILY_ENERGY_CONSUMPTION;
    }

    @Override
    public boolean accept(Unit unit) {
        return unit instanceof OnFoot;
    }

}
