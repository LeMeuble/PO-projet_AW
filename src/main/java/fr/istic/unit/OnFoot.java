package fr.istic.unit;

import fr.istic.game.Player;
import fr.istic.map.Case;
import fr.istic.map.Grid;
import fr.istic.terrain.Property;
import fr.istic.terrain.Terrain;
import fr.istic.util.OptionSelector;
import fr.istic.weather.Weather;

import java.util.List;

/**
 * Classe abstraite representant les unites a pied
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public abstract class OnFoot extends Unit {

    private static final int DAILY_ENERGY_CONSUMPTION = 0;

    /**
     * Constructeur de OnFoot
     *
     * @param owner Le joueur proprietaire de l'unite
     */
    public OnFoot(Player.Type owner) {

        super(owner);

    }

    /**
     * Verifie si l'unite peut se deplacer sur un fr.istic.terrain en fonction de la meteo
     *
     * @param destination La case de destination
     * @param weather     La meteo courante
     *
     * @return true si le deplacement est possible, false sinon
     */
    @Override
    public boolean canMoveTo(Case destination, Weather weather) {

        boolean canMoveParent = super.canMoveTo(destination, weather);

        return canMoveParent && UnitMovementCost.OnFoot.isAccessible(destination.getTerrain().getType(), weather);

    }

    /**
     * Renvoie le cout de deplacement de l'unite vers un fr.istic.terrain, en fonction de la meteo
     *
     * @param destination La case de destination
     * @param weather     La meteo courante
     *
     * @return Le cout de deplacement de l'unite vers une case, en fonction de la meteo
     */
    @Override
    public int getMovementCostTo(Case destination, Weather weather) {

        UnitMovementCost.OnFoot cost = UnitMovementCost.OnFoot.fromTerrainAndWeather(destination.getTerrain().getType(), weather);
        return cost == null ? -1 : cost.getCost();

    }

    @Override
    public int getDailyEnergyConsumption() {
        return OnFoot.DAILY_ENERGY_CONSUMPTION;
    }

    /**
     * Renvoie un selecteur des actions possibles par l'unite
     *
     * @param currentCase La case courante
     * @param contextGrid La grille dans laquelle l'unite peut evoluer
     *
     * @return Un selecteur contenant les actions possibles de la classe mere, plus celle d'une unite a pied
     */
    @Override
    public OptionSelector<UnitAction> getAvailableActions(Case currentCase, Grid contextGrid) {

        // Options disponibles pour la classe mere (Unit)
        final OptionSelector<UnitAction> actions = super.getAvailableActions(currentCase, contextGrid);
        final List<Case> adjacentCases = contextGrid.getAdjacentCases(currentCase);
        final Terrain currentTerrain = currentCase.getTerrain();

        boolean canCapture = currentTerrain instanceof Property && ((Property) currentTerrain).getOwner() != this.getOwner();
        // Si disponible, ajoute l'option capture
        actions.addOption(UnitAction.CAPTURE, canCapture);

        return actions;

    }

}
