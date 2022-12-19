package main.terrain.type;

import main.Player;
import main.terrain.Property;
import ressources.Chemins;

public class HQ extends Property {

    public static final String FILE_PATH_RED = Chemins.getCheminPropriete(Chemins.FICHIER_QG, Player.Type.RED.getValue());
    public static final String FILE_PATH_BLUE = Chemins.getCheminPropriete(Chemins.FICHIER_QG, Player.Type.BLUE.getValue());

    public HQ(Player.Type owner) {
        super(owner);
    }

    public String getFile() {

        if(super.getOwner() == Player.Type.RED) return HQ.FILE_PATH_RED;
        else return HQ.FILE_PATH_BLUE;

    }


}
