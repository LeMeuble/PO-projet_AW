package main.unit;

import main.game.Player;
import main.map.Case;
import main.terrain.Terrain;
import main.terrain.TerrainType;
import main.weather.Weather;

import java.util.Arrays;
import java.util.List;

/**
 * Classe abstraite representant une unite volante
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public abstract class Flying extends Unit {

    public Flying(Player.Type owner) {

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

        UnitMovementCost.Flying cost =  UnitMovementCost.Flying.fromTerrainAndWeather(destination.getTerrain().getType(), weather);
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

        UnitMovementCost.Flying cost =  UnitMovementCost.Flying.fromTerrainAndWeather(destination.getTerrain().getType(), weather);
        return cost == null ? -1 : cost.getCost();

    }

}
