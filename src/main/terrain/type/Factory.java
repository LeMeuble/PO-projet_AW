package main.terrain.type;

import main.Player;
import main.terrain.Property;
import main.weather.Weather;
import ressources.PathUtil;

public class Factory extends Property {

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
