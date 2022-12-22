package main.terrain.type;

import main.terrain.AnimatedTerrain;
import main.terrain.Terrain;
import main.weather.Weather;
import ressources.PathUtil;

public class Obstacle extends AnimatedTerrain {

    public String getFile(Weather weather, boolean isFoggy, int frame) {

        return PathUtil.getAnimatedTerrainPath(weather, Terrain.Type.OBSTACLE, frame, this.getTextureVariation(), isFoggy);

    }

}
