package main.terrain.type;

import main.Player;
import main.terrain.Property;
import ressources.Chemins;

public class Factory extends Property {
    public Factory(Player owner) {
        super(owner);
    }

    public void income() {

        // Faire gagner 1000 coins au joueur propriétaire

    }

    public void produceUnit() {

        // Fait spawn une unité

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
