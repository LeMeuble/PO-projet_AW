package main.unit;

import main.Player;
import main.terrain.Case;
import main.terrain.Terrain;
import main.weather.Weather;

public abstract class Motorized extends Unit {

    public enum MovementCost {

        ON_HQ_CLEAR(Terrain.Type.HQ, Weather.CLEAR, 1),
        ON_CITY_CLEAR(Terrain.Type.CITY, Weather.CLEAR, 1),
        ON_FACTORY_CLEAR(Terrain.Type.FACTORY, Weather.CLEAR, 1),
        ON_PLAIN_CLEAR(Terrain.Type.PLAIN, Weather.CLEAR, 1),
        ON_FOREST_CLEAR(Terrain.Type.FOREST, Weather.CLEAR, 2),
        ON_MOUNTAIN_CLEAR(Terrain.Type.MOUNTAIN, Weather.CLEAR, Integer.MAX_VALUE),
        ON_WATER_CLEAR(Terrain.Type.WATER, Weather.CLEAR, Integer.MAX_VALUE);


        private final Terrain.Type terrainType;
        private final Weather weather;
        private final int cost;

        MovementCost(Terrain.Type terrainType, Weather weather, int cost) {

            this.terrainType = terrainType;
            this.weather = weather;
            this.cost = cost;

        }


        public static MovementCost fromTerrainAndWeather(Terrain.Type terrainType, Weather weather){

            for (MovementCost cost : MovementCost.values()) {
                if (cost.terrainType == terrainType && cost.weather == weather) {
                    return cost;
                }
            }
            return null;

        }

        public int getCost() {
            return this.cost;

        }

        public boolean canMoveTo() {

            return this.cost != Integer.MAX_VALUE;

        }

    }

    public Motorized(Player.Type owner) {

        super(owner);

    }

}
