package main.unit;

import main.game.Player;
import main.terrain.Terrain;
import main.weather.Weather;

public abstract class Flying extends AnimatedUnit {

    public enum MovementCost {

        ON_HQ_CLEAR(Terrain.Type.HQ, Weather.CLEAR, 1),
        ON_CITY_CLEAR(Terrain.Type.CITY, Weather.CLEAR, 1),
        ON_FACTORY_CLEAR(Terrain.Type.FACTORY, Weather.CLEAR, 1),
        ON_PLAIN_CLEAR(Terrain.Type.PLAIN, Weather.CLEAR, 1),
        ON_FOREST_CLEAR(Terrain.Type.FOREST, Weather.CLEAR, 1),
        ON_MOUNTAIN_CLEAR(Terrain.Type.MOUNTAIN, Weather.CLEAR, 1),
        ON_WATER_CLEAR(Terrain.Type.WATER, Weather.CLEAR, 1);

        private final Terrain.Type terrainType;
        private final Weather weather;
        private final int cost;

        MovementCost(Terrain.Type terrainType, Weather weather, int cost) {

            this.terrainType = terrainType;
            this.weather = weather;
            this.cost = cost;

        }

        public static MovementCost fromTerrainAndWeather(Terrain.Type terrainType, Weather weather) {

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

    public Flying(Player.Type owner, int frameCount, int frameDuration) {

        super(owner, frameCount, frameDuration);

    }

    @Override
    public boolean canMoveTo(Terrain destination, Weather weather) {

        MovementCost cost = MovementCost.fromTerrainAndWeather(destination.getType(), weather);
        return cost != null && cost.canMoveTo();

    }

    public int getMovementCostTo(Terrain destination, Weather weather) {

        MovementCost cost = MovementCost.fromTerrainAndWeather(destination.getType(), weather);
        return cost == null ? Integer.MAX_VALUE : cost.getCost();

    }

}
