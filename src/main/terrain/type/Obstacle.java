package main.terrain.type;

import main.terrain.AnimatedTerrain;

public class Obstacle extends AnimatedTerrain {

    @Override
    public Type getType() {
        return Type.OBSTACLE;
    }

}
