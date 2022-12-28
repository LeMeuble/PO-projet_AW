package main.terrain.type;

import main.game.Player;
import main.terrain.Property;

/**
 * Classe representant un QG
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class HQ extends Property {

    /**
     * Constructeur du QG
     * @param owner Le joueur proprietaire du QG
     */
    public HQ(Player.Type owner) {
        super(owner);
    }

    @Override
    public Type getType() {
        return Type.HQ;
    }

}
