package main.terrain.type;

import main.terrain.Terrain;
import ressources.Chemins;

/**
 * Classe representant une foret
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Forest extends Terrain {

    public static final String FILE_PATH = Chemins.getCheminTerrain(Chemins.FICHIER_FORET);

    public String getFile() {

        return Forest.FILE_PATH;

    }

}
