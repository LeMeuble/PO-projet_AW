package fr.istic.terrain;

import fr.istic.PathUtil;
import fr.istic.weather.Weather;

/**
 * Classe abstraite representant du fr.istic.terrain anime
 */
public abstract class AnimatedTerrain extends Terrain {

    @Override
    public String getFile(Weather weather, boolean isFoggy) {
        return this.getFile(weather, isFoggy, 0);
    }

    public String getFile(Weather weather, boolean isFoggy, int frame) {
        return PathUtil.getAnimatedTerrainPath(weather, this.getType(), frame, this.getTextureVariation(), isFoggy);
    }

}