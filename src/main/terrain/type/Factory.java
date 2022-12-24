package main.terrain.type;

import main.Player;
import main.terrain.Property;
import ressources.Chemins;

/**
 * Classe representant une usine
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Factory extends Property {

    public static final String FILE_PATH_RED = Chemins.getCheminPropriete(Chemins.FICHIER_USINE, Player.Type.RED.getValue());
    public static final String FILE_PATH_NEUTRAL = Chemins.getCheminPropriete(Chemins.FICHIER_USINE, Player.Type.NEUTRAL.getValue());
    public static final String FILE_PATH_BLUE = Chemins.getCheminPropriete(Chemins.FICHIER_USINE, Player.Type.BLUE.getValue());

    /**
     * Constructeur de l'usine
     * @param owner Le joueur proprietaire de l'usine
     */
    public Factory(Player.Type owner) {
        super(owner);
    }

    public String getFile() {

        if(super.getOwner() == Player.Type.RED) return Factory.FILE_PATH_RED;
        else if(super.getOwner() == Player.Type.BLUE) return  Factory.FILE_PATH_BLUE;
        else return Factory.FILE_PATH_NEUTRAL;

    }

}
