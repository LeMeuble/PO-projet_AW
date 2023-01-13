package main.unit.type;

import main.game.Player;
import main.map.Case;
import main.map.Grid;
import main.unit.*;
import main.util.OptionSelector;
import main.weapon.type.AirToGroundMissile;
import main.weapon.type.HeavyMachineGun;

/**
 * Une classe representant un helicoptere
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class Helicopter extends FlyingTransport {

    public static final int DAILY_ENERGY_CONSUMPTION = 2;
    public static final int CARRYING_CAPACITY = 1;

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Helicopter(Player.Type owner) {
        super(owner, Helicopter.CARRYING_CAPACITY);
        this.addWeapon(new AirToGroundMissile());
        this.addWeapon(new HeavyMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.HELICOPTER;
    }

    /**
     * Sert a verifier si une unite peut etre transportee par l'helicoptere
     * @param unit Une unite
     * @return true si l'unite est une unite a pied
     */
    @Override
    public boolean accept(Unit unit) {
        return unit instanceof OnFoot;
    }

    @Override
    public int getDailyEnergyConsumption() {
        return Helicopter.DAILY_ENERGY_CONSUMPTION;
    }

    /**
     * Renvoie les actions possibles pour l'helicoptere, en plus des options de ses classes meres
     * @param currentCase La case courante
     * @param contextGrid La grille dans laquelle l'unite peut evoluer
     * @return
     */
    @Override
    public OptionSelector<UnitAction> getAvailableActions(Case currentCase, Grid contextGrid) {

        final OptionSelector<UnitAction> actions = super.getAvailableActions(currentCase, contextGrid);

        boolean carryingUnit = this.isCarryingUnit();

        // S'il y a une unite dans l'helicoptere, ajoute l'option de deposer l'unite
        actions.addOption(UnitAction.DROP_UNIT, carryingUnit);

        return actions;
    }

}
