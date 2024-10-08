package fr.istic.terrain.type;

import fr.istic.terrain.AnimatedTerrain;
import fr.istic.terrain.TerrainType;

/**
 * Class representant un obstacle quelconque
 *
 * @author PandaLunatique
 */
public class Obstacle extends AnimatedTerrain {

    public static final float DEFENSE_MULTIPLIER = 0.1f;

    public float getTerrainCover() {

        return DEFENSE_MULTIPLIER;

    }

    @Override
    public TerrainType getType() {
        return TerrainType.OBSTACLE;
    }

}
