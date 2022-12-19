package main.terrain.type;

import main.terrain.Terrain;
import ressources.Chemins;

public class Forest extends Terrain {

    public static final String FILE_PATH = Chemins.getCheminTerrain(Chemins.FICHIER_FORET);

    public String getFile() {

        return Forest.FILE_PATH;

    }

}
