package main.unit;

import main.game.Player;
import main.map.Case;
import main.map.Grid;
import main.terrain.Terrain;
import main.terrain.type.HQ;
import main.util.OptionSelector;
import main.weather.Weather;

import java.util.List;

/**
 * Classe abstraite representant les unites a pied
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public abstract class OnFoot extends Unit {

    public OnFoot(Player.Type owner) {

        super(owner);

    }

    /**
     * Verifie si l'unite peut se deplacer sur un terrain en fonction de la meteo
     *
     * @param destination La case de destination
     * @param weather     La meteo courante
     *
     * @return true si le deplacement est possible, false sinon
     */
    @Override
    public boolean canMoveTo(Case destination, Weather weather) {

        boolean canMoveParent = super.canMoveTo(destination, weather);

        UnitMovementCost.OnFoot cost = UnitMovementCost.OnFoot.fromTerrainAndWeather(destination.getTerrain().getType(), weather);
        return canMoveParent && cost != null && cost.isAccessible();

    }

    /**
     * Renvoie le cout de deplacement de l'unite vers un terrain, en fonction de la meteo
     *
     * @param destination La case de destination
     * @param weather     La meteo courante
     *
     * @return Le cout de deplacement de l'unite vers une case, en fonction de la meteo
     */
    public int getMovementCostTo(Case destination, Weather weather) {

        UnitMovementCost.OnFoot cost = UnitMovementCost.OnFoot.fromTerrainAndWeather(destination.getTerrain().getType(), weather);
        return cost == null ? -1 : cost.getCost();

    }

    @Override
    public OptionSelector<UnitAction> getAvailableActions(Case currentCase, Grid contextGrid) {

        final OptionSelector<UnitAction> actions = super.getAvailableActions(currentCase, contextGrid);
        final List<Case> adjacentCases = contextGrid.getAdjacentCases(currentCase);
        final Terrain currentTerrain = currentCase.getTerrain();

        boolean anyCapturableHQ = currentTerrain instanceof HQ && ((HQ) currentTerrain).getOwner() != this.getOwner();
        boolean anyEmptyTransport = false;

        System.out.println("Adjacent cases : " + adjacentCases);

        for (Case adjacentCase : adjacentCases) {

            Unit adjacentUnit = adjacentCase.getUnit();
            if (adjacentUnit != null && adjacentUnit.getOwner() == this.getOwner()) {
                if (adjacentUnit instanceof Transport && !((Transport) adjacentUnit).isCarryingUnit()) {
                    anyEmptyTransport = true;
                }
            }

        }

        actions.addOption(UnitAction.CAPTURE, anyCapturableHQ);
        actions.addOption(UnitAction.GET_IN, anyEmptyTransport);

        return actions;

    }

}
