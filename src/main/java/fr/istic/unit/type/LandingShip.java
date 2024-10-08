package fr.istic.unit.type;

import fr.istic.game.Player;
import fr.istic.map.Case;
import fr.istic.terrain.type.Beach;
import fr.istic.unit.*;
import fr.istic.weather.Weather;

/**
 * Une classe representant une barge de debarquement
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class LandingShip extends NavalTransport {

    public static final int DAILY_ENERGY_CONSUMPTION = 2;
    public static final int CARRYING_CAPACITY = 2;

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public LandingShip(Player.Type owner) {
        super(owner, LandingShip.CARRYING_CAPACITY);
    }

    @Override
    public UnitType getType() {
        return UnitType.LANDING_SHIP;
    }

    @Override
    public int getDailyEnergyConsumption() {
        return LandingShip.DAILY_ENERGY_CONSUMPTION;
    }

    /**
     * Sert a verifier si une unite peut etre transportee par la barge
     *
     * @param unit Une unite
     *
     * @return true si l'unite est une unite a pied ou motorisee
     */
    @Override
    public boolean accept(Unit unit) {
        return unit instanceof OnFoot || unit instanceof Motorized;
    }

    /**
     * Verifie si la barge peut aller sur une case de destination, en fonction de la meteo
     *
     * @param destination La case de destination
     * @param weather     La meteo courante
     *
     * @return true si l'unite peut se deplacer sur la case
     */
    @Override
    public boolean canMoveTo(Case destination, Weather weather) {

        boolean canMoveParent = super.canMoveTo(destination, weather);
        return canMoveParent || destination.getTerrain() instanceof Beach;

    }

    /**
     * Retourne le cout de mouvement pour se deplacer sur une case
     * Prend en compte les plages, normalement non valides pour les unites navales
     *
     * @param destination La case de destination
     * @param weather     La meteo courante
     *
     * @return Le cout de mouvement
     */
    public int getMovementCostTo(Case destination, Weather weather) {

        int parentCost = super.getMovementCostTo(destination, weather);
        return destination.getTerrain() instanceof Beach ? 2 : parentCost;

    }

}
