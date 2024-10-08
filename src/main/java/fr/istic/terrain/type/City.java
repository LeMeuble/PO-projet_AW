package fr.istic.terrain.type;

import fr.istic.game.Player;
import fr.istic.terrain.Property;
import fr.istic.terrain.TerrainType;

/**
 * Classe representant une ville
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class City extends Property {

    public static final float DEFENSE_MULTIPLIER = 0.3f;

    public float getTerrainCover() {

        return DEFENSE_MULTIPLIER;

    }

    /**
     * Constructeur de la ville
     *
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
