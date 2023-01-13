package main.unit.type;

import main.game.Player;
import main.unit.NavalTransport;
import main.unit.OnFoot;
import main.unit.Unit;
import main.unit.UnitType;
import main.weapon.type.AntiShipMissile;

/**
 * Une classe representant une corvette
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class Corvette extends NavalTransport {

    private static final int DAILY_ENERGY_CONSUMPTION = 3;
    private static final int CARRYING_CAPACITY = 1;

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Corvette(Player.Type owner) {
        super(owner, Corvette.CARRYING_CAPACITY);
        this.addWeapon(new AntiShipMissile());
    }

    /**
     * Sert a verifier si une unite peut etre transportee par la corvette
     * @param unit Une unite
     * @return true si l'unite est une unite a pied
     */
    @Override
    public boolean accept(Unit unit) {
        return unit instanceof OnFoot;
    }

    @Override
    public UnitType getType() {
        return UnitType.CORVETTE;
    }

    @Override
    public int getDailyEnergyConsumption() {
        return Corvette.DAILY_ENERGY_CONSUMPTION;
    }

}
