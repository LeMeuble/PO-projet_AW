package main.unit.type;

import main.Player;
import main.terrain.Terrain;
import main.unit.Motorized;
import main.unit.OnFoot;
import main.weather.Weather;
import ressources.Chemins;

public class Artillery extends Motorized {

    public static final int MIN_REACH = 2;
    public static final int MAX_REACH = 3;


    public Artillery(Player.Type owner) {

        super(Type.ARTILLERY, owner);

    }


    @Override
    public String getFile() {

        return Chemins.getCheminUnite(this.getOwner().getValue(), !this.hasPlayed(), Chemins.FICHIER_ARTILLERIE);

    }

    @Override
    public int getMovementCostTo(Terrain terrain, Weather weather) {

        OnFoot.MovementCost cost = OnFoot.MovementCost.fromTerrainAndWeather(Terrain.Type.fromTerrain(terrain), weather);

        return cost == null ? Integer.MAX_VALUE : cost.getCost();

    }

    @Override
    public int getMinReach() {
        return MIN_REACH;
    }
    @Override
    public int getMaxReach() {
        return MAX_REACH;
    }
}
