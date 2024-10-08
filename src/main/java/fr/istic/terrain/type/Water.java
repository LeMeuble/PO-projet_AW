package fr.istic.terrain.type;

import fr.istic.terrain.AnimatedTerrain;
import fr.istic.terrain.TerrainType;

/**
 * Classe representant de l'eau
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Water extends AnimatedTerrain {

    public static final float DEFENSE_MULTIPLIER = 0.0f;

    public float getTerrainCover() {

        return DEFENSE_MULTIPLIER;

    }

    @Override
    public TerrainType getType() {
        return TerrainType.WATER;
    }

}
