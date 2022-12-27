package main.terrain.type;

import main.Player;
import main.terrain.Property;
import main.weather.Weather;
import ressources.PathUtil;

/**
 * Classe representant une usine
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Factory extends Property {

    /**
     * Constructeur de l'usine
     * @param owner Le joueur proprietaire de l'usine
     */
    public Factory(Player.Type owner) {
        super(owner);
    }

    public String getFile(Weather weather, boolean isFoggy) {
        return PathUtil.getBuildingPath(weather, this.getOwner(), this.getType(), isFoggy);
    }

    @Override
    public Type getType() {
        return Type.FACTORY;
    }

}
