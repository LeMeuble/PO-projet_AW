package main.unit.type;

import main.game.Player;
import main.unit.Naval;
import main.unit.UnitType;

public class LandingShip extends Naval {

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public LandingShip(Player.Type owner) {
        super(owner);
    }

    @Override
    public UnitType getType() {
        return UnitType.LANDINGSHIP;
    }

}
