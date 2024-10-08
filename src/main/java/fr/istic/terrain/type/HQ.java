package fr.istic.terrain.type;

import fr.istic.game.Player;
import fr.istic.terrain.Property;
import fr.istic.terrain.TerrainType;

/**
 * Classe representant un QG
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class HQ extends Property {

    public static final float DEFENSE_MULTIPLIER = 0.4f;

    public float getTerrainCover() {

        return DEFENSE_MULTIPLIER;

    }

    /**
     * Constructeur du QG
     *
     * @param owner Le joueur proprietaire du QG
     */
    public HQ(Player.Type owner) {
        super(owner);
    }

    @Override
    public TerrainType getType() {
        return TerrainType.HQ;
    }

}
