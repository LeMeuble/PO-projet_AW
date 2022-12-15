package main.terrain.type;

import main.terrain.Terrain;
import ressources.Chemins;

public class Water extends Terrain {

    public static final String filePath = Chemins.getCheminTerrain(Chemins.FICHIER_EAU);

    public String getFile() {

        return filePath;

    }

}
