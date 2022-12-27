package main.terrain.type;

import main.Player;
import main.terrain.Property;
import main.weather.Weather;
import ressources.PathUtil;

/**
 * Classe representant une ville
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class City extends Property {

    /**
     * Constructeur de la ville
     * @param owner Le joueur proprietaire de la ville
     */
    public City(Player.Type owner) {
        super(owner);
    }

    public String getFile(Weather weather, boolean isFoggy) {
        return PathUtil.getBuildingPath(weather, this.getOwner(), this.getType(), isFoggy);
    }

    @Override
    public Type getType() {
        return Type.CITY;
    }

}
