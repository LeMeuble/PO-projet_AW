package main.unit;

import main.game.Player;
import main.map.Case;
import main.terrain.TerrainType;
import main.weather.Weather;

import java.util.Arrays;
import java.util.List;

/**
 * Classe abstraite representant une unite motorisee
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public abstract class Motorized extends Fueled {

    private int fuel;

    public Motorized(Player.Type owner) {

        super(owner);

    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
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

        UnitMovementCost.Motorized cost = UnitMovementCost.Motorized.fromTerrainAndWeather(destination.getTerrain().getType(), weather);
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

        UnitMovementCost.Motorized cost = UnitMovementCost.Motorized.fromTerrainAndWeather(destination.getTerrain().getType(), weather);
        return cost == null ? -1 : cost.getCost();

    }

}
