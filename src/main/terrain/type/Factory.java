package main.terrain.type;

import main.Player;
import main.terrain.Property;
import ressources.Chemins;

public class Factory extends Property {

    public static final String redFactoryFilePath = Chemins.getCheminPropriete(Chemins.FICHIER_USINE, Player.Type.RED.getValue());
    public static final String neutralFactoryFilePath = Chemins.getCheminPropriete(Chemins.FICHIER_USINE, Player.Type.NEUTRAL.getValue());
    public static final String blueFactoryFilePath = Chemins.getCheminPropriete(Chemins.FICHIER_USINE, Player.Type.BLUE.getValue());

    public Factory(Player.Type owner) {
        super(owner);
    }

    public void produceUnit() {

        // Fait spawn une unité

    }

    public String getFile() {

        if(super.getOwner() == Player.Type.RED) return redFactoryFilePath;
        else if(super.getOwner() == Player.Type.BLUE) return blueFactoryFilePath;
        else return neutralFactoryFilePath;

    }

}
