package main.terrain.type;

import main.terrain.AnimatedTerrain;
import main.terrain.TerrainType;

/**
 * Class representant un obstacle quelconque
 *
 * @author Tristan LECONTE--DENIS
 */
public class Obstacle extends AnimatedTerrain {

    @Override
    public TerrainType getType() {
        return TerrainType.OBSTACLE;
    }

}
