package main.terrain.type;

import main.game.Player;
import main.terrain.Property;

/**
 * Classe representant une usine
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Factory extends Property {

    /**
     * Constructeur de l'usine
     * @param owner Le joueur proprietaire de l'usine
     */
    public Factory(Player.Type owner) {
        super(owner);
    }

    @Override
    public Type getType() {
        return Type.FACTORY;
    }

}
