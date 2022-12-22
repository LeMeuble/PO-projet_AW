package main.terrain.type;

import main.terrain.Terrain;
import main.weather.Weather;
import ressources.PathUtil;

public class Plain extends Terrain {

    public String getFile(Weather weather, boolean isFoggy) {

        return PathUtil.getTerrainPath(weather, Terrain.Type.PLAIN, this.getTextureVariation(), isFoggy);

    }

}
