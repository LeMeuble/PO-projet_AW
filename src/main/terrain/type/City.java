package main.terrain.type;

import main.game.Player;
import main.terrain.Property;

/**
 * Classe representant une ville
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class City extends Property {

    /**
     * Constructeur de la ville
     * @param owner Le joueur proprietaire de la ville
     */
    public City(Player.Type owner) {
        super(owner);
    }

    @Override
    public Type getType() {
        return Type.CITY;
    }

}
