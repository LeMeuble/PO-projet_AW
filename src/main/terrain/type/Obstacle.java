package main.terrain.type;

import main.terrain.AnimatedTerrain;
import main.terrain.TerrainType;

/**
 * Class representant un obstacle quelconque
 *
 * @author Tristan LECONTE--DENIS
 */
public class Obstacle extends AnimatedTerrain {

    public static final double DEFENSE_MULTIPLIER = 0.1;

    public double GetTerrainCover() {

        return DEFENSE_MULTIPLIER;

    }

    @Override
    public TerrainType getType() {
        return TerrainType.OBSTACLE;
    }

}
