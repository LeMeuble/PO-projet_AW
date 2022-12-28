package main.unit;

import main.game.Player;
import main.terrain.Terrain;
import main.weather.Weather;

import java.util.Arrays;
import java.util.List;

/**
 * Classe abstraite representant les unites a pied
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public abstract class OnFoot extends AnimatedUnit {

    public enum MovementCost {

        ON_HQ_CLEAR(Terrain.Type.HQ, 1, Weather.CLEAR, Weather.RAINY, Weather.HEAVY_WIND),
        ON_CITY_CLEAR(Terrain.Type.CITY, 1, Weather.CLEAR, Weather.RAINY, Weather.HEAVY_WIND),
        ON_FACTORY_CLEAR(Terrain.Type.FACTORY, 1, Weather.CLEAR, Weather.RAINY, Weather.HEAVY_WIND),
        ON_PLAIN_CLEAR(Terrain.Type.PLAIN, 1, Weather.CLEAR, Weather.RAINY, Weather.HEAVY_WIND),
        ON_FOREST_CLEAR(Terrain.Type.FOREST, 1, Weather.CLEAR, Weather.RAINY, Weather.HEAVY_WIND),
        ON_MOUNTAIN_CLEAR(Terrain.Type.MOUNTAIN, 2, Weather.CLEAR, Weather.RAINY, Weather.HEAVY_WIND),
        ON_WATER_CLEAR(Terrain.Type.WATER, Integer.MAX_VALUE, Weather.CLEAR, Weather.RAINY, Weather.HEAVY_WIND),

        ON_HQ_SNOWY(Terrain.Type.HQ, 1, Weather.SNOWY),
        ON_CITY_SNOWY(Terrain.Type.CITY, 1, Weather.SNOWY),
        ON_FACTORY_SNOWY(Terrain.Type.FACTORY, 1, Weather.SNOWY),
        ON_PLAIN_SNOWY(Terrain.Type.PLAIN, 1, Weather.SNOWY),
        ON_FOREST_SNOWY(Terrain.Type.FOREST, 2, Weather.SNOWY),
        ON_MOUNTAIN_SNOWY(Terrain.Type.MOUNTAIN, Integer.MAX_VALUE, Weather.SNOWY),
        ON_WATER_SNOWY(Terrain.Type.WATER, Integer.MAX_VALUE, Weather.SNOWY);

        private final Terrain.Type terrainType;
        private final List<Weather> weather;
        private final int cost;

        MovementCost(Terrain.Type terrainType, int cost, Weather ...weather) {

            this.terrainType = terrainType;
            this.cost = cost;
            this.weather = Arrays.asList(weather);

        }

        public static MovementCost fromTerrainAndWeather(Terrain.Type terrainType, Weather weather) {

            for (MovementCost cost : MovementCost.values()) {
                if (cost.terrainType == terrainType && cost.weather.contains(weather)) {
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

    public OnFoot(Player.Type owner, int frameCount, int frameDuration) {

        super(owner, frameCount, frameDuration);

    }

    /**
     * Verifie si l'unite peut se deplacer sur un terrain en fonction de la meteo
     * @param destination Le terrain de destination
     * @param weather La meteo courante
     * @return true si le deplacement est possible, false sinon
     */
    @Override
    public boolean canMoveTo(Terrain destination, Weather weather) {

        MovementCost cost = MovementCost.fromTerrainAndWeather(destination.getType(), weather);
        return cost != null && cost.canMoveTo();

    }

    /**
     * Renvoie le cout de deplacement de l'unite vers un terrain, en fonction de la meteo
     * @param destination Le terrain de destination
     * @param weather La meteo de destination
     * @return Le cout de deplacement
     */
    public int getMovementCostTo(Terrain destination, Weather weather) {

        MovementCost cost = MovementCost.fromTerrainAndWeather(destination.getType(), weather);
        return cost == null ? Integer.MAX_VALUE : cost.getCost();

    }

}
