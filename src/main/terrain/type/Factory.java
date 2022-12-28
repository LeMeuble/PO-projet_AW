package main.terrain.type;

import main.game.Player;
import main.terrain.Property;
import main.terrain.TerrainType;

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
    public TerrainType getType() {
        return TerrainType.FACTORY;
    }

}
