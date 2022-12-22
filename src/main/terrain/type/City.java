package main.terrain.type;

import main.Player;
import main.terrain.Property;
import main.terrain.Terrain;
import main.weather.Weather;
import ressources.PathUtil;

public class City extends Property {

    public City(Player.Type owner) {
        super(owner);
    }

    public String getFile(Weather weather, boolean isFoggy) {

        return PathUtil.getBuildingPath(weather, this.getOwner(), Terrain.Type.CITY, isFoggy);

    }

}
