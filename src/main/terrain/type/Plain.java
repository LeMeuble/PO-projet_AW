package main.terrain.type;

import main.terrain.Terrain;
import ressources.Chemins;

public class Plain extends Terrain {

    public static final String filePath = Chemins.getCheminTerrain(Chemins.FICHIER_PLAINE);

    public String getFile() {

        return filePath;

    }


}
