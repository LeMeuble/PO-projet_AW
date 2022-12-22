package main.terrain.type;

import main.terrain.Terrain;
import main.weather.Weather;
import ressources.PathUtil;

public class Mountain extends Terrain {


    public String getFile(Weather weather, boolean isFoggy) {

        return PathUtil.getTerrainPath(weather, Terrain.Type.MOUNTAIN, this.getTextureVariation(), isFoggy);

    }


}
