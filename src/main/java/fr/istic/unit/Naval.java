package fr.istic.unit;

import fr.istic.game.Player;
import fr.istic.map.Case;
import fr.istic.weather.Weather;

/**
 * Classe abstraite representant une unite navale
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public abstract class Naval extends Unit {

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Naval(Player.Type owner) {
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
        return canMoveParent && UnitMovementCost.Naval.isAccessible(destination.getTerrain().getType(), weather);

    }

    /**
     * Renvoie le cout de deplacement de l'unite vers un fr.istic.terrain, en fonction de la meteo
     *
     * @param destination La case de destination
     * @param weather     La meteo courante
     *
     * @return Le cout de deplacement de l'unite vers une case, en fonction de la meteo
     */
    public int getMovementCostTo(Case destination, Weather weather) {

        UnitMovementCost.Naval cost = UnitMovementCost.Naval.fromTerrainAndWeather(destination.getTerrain().getType(), weather);
        return cost == null ? -1 : cost.getCost();

    }

}
