package fr.istic.terrain.type;

import fr.istic.terrain.AnimatedTerrain;
import fr.istic.terrain.TerrainType;

/**
 * Classe representant une plage
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Beach extends AnimatedTerrain {

    public static final float DEFENSE_MULTIPLIER = 0.0f;

    @Override
    public TerrainType getType() {
        return TerrainType.BEACH;
    }

    @Override
    public float getTerrainCover() {
        return DEFENSE_MULTIPLIER;
    }

}
