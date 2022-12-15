package main.terrain.type;

import main.Player;
import main.terrain.Property;
import ressources.Chemins;

public class Factory extends Property {

    public static final String redFactoryFilePath = Chemins.getCheminPropriete(Chemins.FICHIER_USINE, Player.RED.getValue());
    public static final String neutralFactoryFilePath = Chemins.getCheminPropriete(Chemins.FICHIER_USINE, Player.NEUTRAL.getValue());
    public static final String blueFactoryFilePath = Chemins.getCheminPropriete(Chemins.FICHIER_USINE, Player.BLUE.getValue());

    public Factory(Player owner) {
        super(owner);
    }

    public void income() {

        // Faire gagner 1000 coins au joueur propriétaire

    }

    public void produceUnit() {

        // Fait spawn une unité

    }

    public String getFile() {

        if(this.owner == Player.RED) return redFactoryFilePath;
        else if(this.owner == Player.BLUE) return blueFactoryFilePath;
        else return neutralFactoryFilePath;

    }

}
