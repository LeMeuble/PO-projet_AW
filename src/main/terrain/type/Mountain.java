package main.terrain.type;

import main.terrain.Terrain;
import ressources.Chemins;

public class Mountain extends Terrain {

    public static final String filePath = Chemins.getCheminTerrain(Chemins.FICHIER_MONTAGNE);

    public String getFile() {

        return filePath;

    }


}
