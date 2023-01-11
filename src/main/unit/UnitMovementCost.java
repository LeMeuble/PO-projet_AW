package main.unit;

import main.terrain.TerrainType;
import main.weather.Weather;

import java.util.Arrays;
import java.util.List;

public final class UnitMovementCost {

    public enum OnFoot {

        ON_HQ_NORMAL(TerrainType.HQ, 1),
        ON_CITY_NORMAL(TerrainType.CITY, 1),
        ON_FACTORY_NORMAL(TerrainType.FACTORY, 1),
        ON_AIRPORT_NORMAL(TerrainType.AIRPORT, 1),
        ON_PORT_NORMAL(TerrainType.PORT, 1),
        ON_PLAIN_NORMAL(TerrainType.PLAIN, 1),
        ON_BEACH_NORMAL(TerrainType.BEACH, 1),
        ON_FOREST_NORMAL(TerrainType.FOREST, 1, Weather.CLEAR, Weather.HEAVY_WIND, Weather.RAINY),
        ON_MOUNTAIN_NORMAL(TerrainType.MOUNTAIN, 2, Weather.CLEAR, Weather.HEAVY_WIND, Weather.RAINY),

        ON_FOREST_SNOWY(TerrainType.FOREST, 2, Weather.SNOWY);

        private final TerrainType terrainType;
        private final List<Weather> weather;
        private final int cost;

        OnFoot(TerrainType terrainType, int cost, Weather... weather) {

            this.terrainType = terrainType;
            this.cost = cost;
            this.weather = Arrays.asList(weather);

        }

        public static OnFoot fromTerrainAndWeather(TerrainType terrainType, Weather weather) {

            for (OnFoot cost : OnFoot.values()) {
                if (cost.terrainType == terrainType && (cost.weather.contains(weather) || cost.weather.isEmpty())) {
                    return cost;
                }
            }
            return null;

        }

        public static boolean isAccessible(TerrainType terrainType, Weather weather) {

            return OnFoot.fromTerrainAndWeather(terrainType, weather) != null;

        }

        public int getCost() {
            return this.cost;

        }

    }

    public enum Motorized {

        ON_HQ_NORMAL(TerrainType.HQ, 1),
        ON_CITY_NORMAL(TerrainType.CITY, 1),
        ON_AIRPORT_NORMAL(TerrainType.AIRPORT, 1),
        ON_PORT_NORMAL(TerrainType.PORT, 1),
        ON_FACTORY_NORMAL(TerrainType.FACTORY, 1),
        ON_PLAIN_NORMAL(TerrainType.PLAIN, 1, Weather.CLEAR, Weather.HEAVY_WIND, Weather.RAINY),
        ON_BEACH_NORMAL(TerrainType.BEACH, 2),
        ON_FOREST_NORMAL(TerrainType.FOREST, 2, Weather.CLEAR, Weather.HEAVY_WIND, Weather.RAINY),

        ON_PLAIN_SNOWY(TerrainType.PLAIN, 2, Weather.SNOWY),
        ON_FOREST_SNOWY(TerrainType.FOREST, 3, Weather.SNOWY);


        private final TerrainType terrainType;
        private final List<Weather> weather;
        private final int cost;

        Motorized(TerrainType terrainType, int cost, Weather... weather) {

            this.terrainType = terrainType;
            this.cost = cost;
            this.weather = Arrays.asList(weather);

        }

        public static Motorized fromTerrainAndWeather(TerrainType terrainType, Weather weather) {

            for (Motorized cost : Motorized.values()) {
                if (cost.terrainType == terrainType && (cost.weather.contains(weather) || cost.weather.isEmpty())) {
                    return cost;
                }
            }
            return null;

        }

        public static boolean isAccessible(TerrainType terrainType, Weather weather) {

            return Motorized.fromTerrainAndWeather(terrainType, weather) != null;

        }

        public int getCost() {
            return this.cost;

        }

    }

    public enum Flying {

        ON_HQ(TerrainType.HQ, 1),
        ON_CITY(TerrainType.CITY, 1),
        ON_FACTORY(TerrainType.FACTORY, 1),
        ON_AIRPORT(TerrainType.AIRPORT, 1),
        ON_PORT(TerrainType.PORT, 1),
        ON_PLAIN(TerrainType.PLAIN, 1),
        ON_OBSTACLE(TerrainType.OBSTACLE, 1),
        ON_BEACH(TerrainType.BEACH, 1),
        ON_FOREST(TerrainType.FOREST, 1),
        ON_MOUNTAIN(TerrainType.MOUNTAIN, 1),
        ON_WATER(TerrainType.WATER, 1);

        private final TerrainType terrainType;
        private final List<Weather> weather;
        private final int cost;

        Flying(TerrainType terrainType, int cost, Weather... weather) {

            this.terrainType = terrainType;
            this.cost = cost;
            this.weather = Arrays.asList(weather);

        }

        public static Flying fromTerrainAndWeather(TerrainType terrainType, Weather weather) {

            for (Flying cost : Flying.values()) {
                if (cost.terrainType == terrainType && (cost.weather.contains(weather) || cost.weather.isEmpty())) {
                    return cost;
                }
            }
            return null;

        }

        public static boolean isAccessible(TerrainType terrainType, Weather weather) {

            return Flying.fromTerrainAndWeather(terrainType, weather) != null;

        }

        public int getCost() {
            return this.cost;

        }

    }

    public enum Naval {

        ON_WATER(TerrainType.WATER, 1);

        private final TerrainType terrainType;
        private final List<Weather> weather;
        private final int cost;

        Naval(TerrainType terrainType, int cost, Weather... weather) {

            this.terrainType = terrainType;
            this.cost = cost;
            this.weather = Arrays.asList(weather);

        }

        public static Naval fromTerrainAndWeather(TerrainType terrainType, Weather weather) {

            for (Naval cost : Naval.values()) {
                if (cost.terrainType == terrainType && (cost.weather.contains(weather) || cost.weather.isEmpty())) {
                    return cost;
                }
            }
            return null;

        }

        public static boolean isAccessible(TerrainType terrainType, Weather weather) {

            return Naval.fromTerrainAndWeather(terrainType, weather) != null;

        }

        public int getCost() {
            return this.cost;

        }

    }

}
