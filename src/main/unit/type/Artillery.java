package main.unit.type;

import main.Player;
import main.terrain.Terrain;
import main.unit.Motorized;
import main.unit.OnFoot;
import main.weather.Weather;
import ressources.Chemins;

public class Artillery extends Motorized {

    int maxPM = 5;
    int PM;
    int unitMovementType = 2;

    int maxHealth = 10;
    int health;

    Weapon[] weapons;

    int price = 6000;
    int ammo;
    int fuel;

    boolean hasPlayed;
    // Idem que pour les dégats, on utilise un tableau ? Une liste ?
    int[] movementTable;


    public Artillery(Player.Type owner) {

        super(Type.ARTILLERY, owner);

    }

    /**
     * @return
     */
    @Override
    public double calculateDamage() {
        return 0;
    }

    /**
     * @param amount
     */
    @Override
    public void receiveDamage(int amount) {

    }

    @Override
    public void inflictDamage(Unit target) {

    }

    @Override
    public int getMovementCostTo(Terrain terrain, Weather weather) {

        OnFoot.MovementCost cost = OnFoot.MovementCost.fromTerrainAndWeather(Terrain.Type.fromTerrain(terrain), weather);

        return cost == null ? Integer.MAX_VALUE : cost.getCost();

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
