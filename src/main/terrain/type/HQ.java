package main.terrain.type;

import main.Player;
import main.terrain.Property;
import main.weather.Weather;
import ressources.PathUtil;

/**
 * Classe representant un QG
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class HQ extends Property {

    /**
     * Constructeur du QG
     * @param owner Le joueur proprietaire du QG
     */
    public HQ(Player.Type owner) {
        super(owner);
    }

    public String getFile(Weather weather, boolean isFoggy) {
        return PathUtil.getBuildingPath(weather, this.getOwner(), this.getType(), isFoggy);
    }

    @Override
    public Type getType() {
        return Type.HQ;
    }

}
