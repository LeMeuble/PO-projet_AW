package main.terrain.type;

import main.terrain.Terrain;
import main.weather.Weather;
import ressources.Chemins;
import ressources.PathUtil;

public class Forest extends Terrain {

    public static final String FILE_PATH = Chemins.getCheminTerrain(Chemins.FICHIER_FORET);

    public String getFile(Weather weather, boolean isFoggy) {

        return PathUtil.getTerrainPath(weather, Terrain.Type.FOREST, this.getTextureVariation(), isFoggy);

    }

}
