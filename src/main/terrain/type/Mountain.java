package main.terrain.type;

import main.terrain.Terrain;
import ressources.Chemins;

/**
 * Classe representant une montagne
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Mountain extends Terrain {

    public static final String FILE_PATH = Chemins.getCheminTerrain(Chemins.FICHIER_MONTAGNE);

    public String getFile() {

        return Mountain.FILE_PATH;

    }


}
