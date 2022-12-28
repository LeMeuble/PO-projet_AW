package main.terrain;

import main.weather.Weather;
import ressources.PathUtil;

public abstract class AnimatedTerrain extends Terrain {

    @Override
    public String getFile(Weather weather, boolean isFoggy) {
        return this.getFile(weather, isFoggy, 0);
    }

    public String getFile(Weather weather, boolean isFoggy, int frame) {
        return PathUtil.getAnimatedTerrainPath(weather, this.getType(), frame, this.getTextureVariation(), isFoggy);

    }

}