package main.unit;

import main.game.Player;
import main.map.Case;
import main.weather.Weather;

public abstract class Naval extends Unit {

    public static final int MOVEMENT_COST_PER_CASE = 1;

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Naval(Player.Type owner) {
        super(owner);
    }

    @Override
    public int getMovementCostTo(Case destination, Weather weather) {
        return Naval.MOVEMENT_COST_PER_CASE;
    }


}
