package main.terrain.type;

import main.Player;
import main.terrain.Property;
import ressources.Chemins;

public class HQ extends Property {

    public static final String redHQfilePath = Chemins.getCheminPropriete(Chemins.FICHIER_QG, Player.Type.RED.getValue());
    public static final String blueHQfilePath = Chemins.getCheminPropriete(Chemins.FICHIER_QG, Player.Type.BLUE.getValue());

    public HQ(Player.Type owner) {
        super(owner);
    }


    public String getFile() {

        if(super.getOwner() == Player.Type.RED) return redHQfilePath;
        else return blueHQfilePath;

    }


}
