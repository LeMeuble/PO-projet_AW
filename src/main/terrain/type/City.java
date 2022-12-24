package main.terrain.type;

import main.Player;
import main.terrain.Property;
import ressources.Chemins;

/**
 * Classe representant une ville
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class City extends Property {

    public static final String FILE_PATH_RED = Chemins.getCheminPropriete(Chemins.FICHIER_VILLE, Player.Type.RED.getValue());
    public static final String FILE_PATH_NEUTRAL = Chemins.getCheminPropriete(Chemins.FICHIER_VILLE, Player.Type.NEUTRAL.getValue());
    public static final String FILE_PATH_BLUE = Chemins.getCheminPropriete(Chemins.FICHIER_VILLE, Player.Type.BLUE.getValue());

    /**
     * Constructeur de la ville
     * @param owner Le joueur proprietaire de la ville
     */
    public City(Player.Type owner) {
        super(owner);
    }

    public String getFile() {

        if(super.getOwner() == Player.Type.RED) return City.FILE_PATH_RED;
        if(super.getOwner() == Player.Type.BLUE) return City.FILE_PATH_BLUE;
        else return City.FILE_PATH_NEUTRAL;
    }

}
