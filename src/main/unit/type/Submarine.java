package main.unit.type;

import main.game.Player;
import main.map.Case;
import main.map.Grid;
import main.unit.Naval;
import main.unit.UnitAction;
import main.unit.UnitType;
import main.util.OptionSelector;
import main.weapon.type.Torpedo;
import main.weather.Weather;

public class Submarine extends Naval {

    public static final int DAILY_ENERGY_CONSUMPTION = 2;

    private boolean underwater;

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Submarine(Player.Type owner) {
        super(owner);
        this.addWeapon(new Torpedo());
        this.underwater = false;
    }

    public void dive() {
        this.underwater = true;
    }

    public void surface() {
        this.underwater = false;
    }


    public boolean canDive() {
        return !this.underwater;
    }

    public boolean canSurface() {
        return this.underwater;
    }


    @Override
    public UnitType getType() {
        return UnitType.SUBMARINE;
    }

    @Override
    public int getDailyEnergyConsumption() {
        return Submarine.DAILY_ENERGY_CONSUMPTION * (this.underwater ? 2 : 1);
    }

    @Override
    public OptionSelector<UnitAction> getAvailableActions(Case currentCase, Grid contextGrid) {

        final OptionSelector<UnitAction> actions = super.getAvailableActions(currentCase, contextGrid);
        actions.addOption(UnitAction.DIVE, this.canDive());
        actions.addOption(UnitAction.SURFACE, this.canSurface());

        return actions;

    }

    @Override
    public int getMovementCostTo(Case destination, Weather weather) {

        if(this.underwater) return super.getMovementCostTo(destination, weather) * 2;
        else return super.getMovementCostTo(destination, weather);

    }


}
