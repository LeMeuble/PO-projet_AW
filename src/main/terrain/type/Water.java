package main.terrain.type;

import main.terrain.Terrain;
import ressources.Chemins;

/**
 * Classe representant de l'eau
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Water extends Terrain {

    public static final String FILE_PATH = Chemins.getCheminTerrain(Chemins.FICHIER_EAU);

    public String getFile() {

        return Water.FILE_PATH;

    }

}
