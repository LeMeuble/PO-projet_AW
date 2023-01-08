package main.terrain.type;

import main.game.Player;
import main.terrain.Property;
import main.terrain.TerrainType;

/**
 * Classe representant une ville
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class City extends Property {

    public static final double DEFENSE_MULTIPLIER = 0.3;

    public double GetTerrainCover() {

        return DEFENSE_MULTIPLIER;

    }

    /**
     * Constructeur de la ville
     * @param owner Le joueur proprietaire de la ville
     */
    public City(Player.Type owner) {
        super(owner);
    }

    @Override
    public TerrainType getType() {
        return TerrainType.CITY;
    }

}
