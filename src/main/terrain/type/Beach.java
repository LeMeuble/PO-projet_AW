package main.terrain.type;

import main.terrain.AnimatedTerrain;
import main.terrain.TerrainType;

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
