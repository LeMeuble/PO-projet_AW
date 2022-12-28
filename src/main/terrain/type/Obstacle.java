package main.terrain.type;

import main.terrain.AnimatedTerrain;
import main.weather.Weather;
import ressources.PathUtil;

/**
 * Class representant un obstacle quelconque
 *
 * @author Tristan LECONTE--DENIS
 */
public class Obstacle extends AnimatedTerrain {

    public String getFile(Weather weather, boolean isFoggy, int frame) {
        return PathUtil.getAnimatedTerrainPath(weather, Type.OBSTACLE, frame, this.getTextureVariation(), isFoggy);
    }

    @Override
    public Type getType() {
        return Type.OBSTACLE;
    }

}
