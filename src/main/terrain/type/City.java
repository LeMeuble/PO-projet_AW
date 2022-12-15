package main.terrain.type;

import main.Player;
import main.terrain.Property;
import ressources.Chemins;

public class City extends Property {
    public City(Player owner) {
        super(owner);
    }

    public static final String neutralCityPath = Chemins.getCheminPropriete(Chemins.FICHIER_VILLE, Player.NEUTRAL.getValue());

    public static final String redCityfilePath = Chemins.getCheminPropriete(Chemins.FICHIER_VILLE, Player.RED.getValue());
    public static final String blueCityfilePath = Chemins.getCheminPropriete(Chemins.FICHIER_VILLE, Player.BLUE.getValue());


    public String getFile() {

        if(this.owner == Player.RED) return redCityfilePath;
        if(this.owner == Player.BLUE) return blueCityfilePath;
        else return neutralCityPath;
    }

}
