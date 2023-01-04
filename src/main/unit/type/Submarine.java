package main.unit.type;

import main.game.Player;
import main.unit.Naval;
import main.unit.UnitType;

public class Submarine extends Naval {

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Submarine(Player.Type owner) {
        super(owner);
    }

    @Override
    public UnitType getType() {
        return UnitType.SUBMARINE;
    }

}
