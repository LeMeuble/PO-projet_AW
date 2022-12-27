package main.terrain.type;

import main.terrain.Terrain;
import main.weather.Weather;
import ressources.PathUtil;

/**
 * Classe representant une foret
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Forest extends Terrain {

    public String getFile(Weather weather, boolean isFoggy) {
        return PathUtil.getTerrainPath(weather, this.getType(), this.getTextureVariation(), isFoggy);
    }

    @Override
    public Type getType() {
        return Type.FOREST;
    }

}
