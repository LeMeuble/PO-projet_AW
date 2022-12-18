package main.terrain.type;

import main.Player;
import main.terrain.Property;
import ressources.Chemins;

public class City extends Property {

    public City(Player.Type owner) {
        super(owner);
    }

    public static final String neutralCityPath = Chemins.getCheminPropriete(Chemins.FICHIER_VILLE, Player.Type.NEUTRAL.getValue());

    public static final String redCityfilePath = Chemins.getCheminPropriete(Chemins.FICHIER_VILLE, Player.Type.RED.getValue());
    public static final String blueCityfilePath = Chemins.getCheminPropriete(Chemins.FICHIER_VILLE, Player.Type.BLUE.getValue());


    public String getFile() {

        if(super.getOwner() == Player.Type.RED) return redCityfilePath;
        if(super.getOwner() == Player.Type.BLUE) return blueCityfilePath;
        else return neutralCityPath;
    }

}
