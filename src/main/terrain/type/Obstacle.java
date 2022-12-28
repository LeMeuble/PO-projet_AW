package main.terrain.type;

import main.terrain.AnimatedTerrain;

/**
 * Class representant un obstacle quelconque
 *
 * @author Tristan LECONTE--DENIS
 */
public class Obstacle extends AnimatedTerrain {

    @Override
    public Type getType() {
        return Type.OBSTACLE;
    }

}
