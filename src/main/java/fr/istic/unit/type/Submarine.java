package fr.istic.unit.type;

import fr.istic.game.Player;
import fr.istic.map.Case;
import fr.istic.map.Grid;
import fr.istic.unit.Naval;
import fr.istic.unit.UnitAction;
import fr.istic.unit.UnitType;
import fr.istic.util.OptionSelector;
import fr.istic.weapon.type.Torpedo;
import fr.istic.weather.Weather;

/**
 * Une classe representant un sous-marin
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
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

    /**
     * Renvoie les actions possibles pour le sous-marin, en plus des options de ses classes meres
     *
     * @param currentCase La case courante
     * @param contextGrid La grille dans laquelle l'unite peut evoluer
     *
     * @return
     */
    @Override
    public OptionSelector<UnitAction> getAvailableActions(Case currentCase, Grid contextGrid) {

        final OptionSelector<UnitAction> actions = super.getAvailableActions(currentCase, contextGrid);
        actions.addOption(UnitAction.DIVE, this.canDive());
        actions.addOption(UnitAction.SURFACE, this.canSurface());

        return actions;

    }

    @Override
    public int getMovementCostTo(Case destination, Weather weather) {

        final int cost = super.getMovementCostTo(destination, weather);

        if (this.underwater) return cost == -1 ? -1 : cost * 2;
        else return cost;

    }


}
