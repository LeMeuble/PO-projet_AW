package main.terrain.type;

import main.terrain.AnimatedTerrain;
import main.weather.Weather;
import ressources.PathUtil;

/**
 * Classe representant de l'eau
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Water extends AnimatedTerrain {

    public String getFile(Weather weather, boolean isFoggy, int frame) {
        return PathUtil.getAnimatedTerrainPath(weather, this.getType(), frame, this.getTextureVariation(), isFoggy);
    }

    @Override
    public Type getType() {
        return Type.WATER;
    }
}
