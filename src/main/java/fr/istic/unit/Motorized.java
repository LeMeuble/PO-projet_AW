package fr.istic.unit;

import fr.istic.game.Player;
import fr.istic.map.Case;
import fr.istic.weather.Weather;

/**
 * Classe abstraite representant une unite motorisee
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public abstract class Motorized extends Unit {

    public static final int DAILY_ENERGY_CONSUMPTION = 0;

    /**
     * Constructeur de Motorized
     *
     * @param owner Le joueur proprietaire de l'unite
     */
    public Motorized(Player.Type owner) {
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

        return canMoveParent && UnitMovementCost.Motorized.isAccessible(destination.getTerrain().getType(), weather);

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

        UnitMovementCost.Motorized cost = UnitMovementCost.Motorized.fromTerrainAndWeather(destination.getTerrain().getType(), weather);
        return cost == null ? -1 : cost.getCost();

    }

    @Override
    public int getDailyEnergyConsumption() {
        return Motorized.DAILY_ENERGY_CONSUMPTION;
    }

}
