package main.terrain.type;

import main.Player;
import main.terrain.Property;
import ressources.Chemins;

public class HQ extends Property {

    public static final String redHQfilePath = Chemins.getCheminPropriete(Chemins.FICHIER_QG, Player.RED.getValue());
    public static final String blueHQfilePath = Chemins.getCheminPropriete(Chemins.FICHIER_QG, Player.BLUE.getValue());

    public HQ(Player owner) {
        super(owner);
    }


    public String getFile() {

        if(this.owner == Player.RED) return redHQfilePath;
        else return blueHQfilePath;

    }


}
