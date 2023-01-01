package main.unit;

import main.game.Player;
import main.terrain.Terrain;
import main.terrain.TerrainType;
import main.weather.Weather;

/**
 * Classe abstraite representant une unite volante
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public abstract class Flying extends AnimatedUnit {

    /**
     * Enumeration des couts d'un mouvement, en fonction du type du terrain de destination et de la meteo
     */
    public enum MovementCost {

        ON_HQ_CLEAR(TerrainType.HQ, Weather.CLEAR, 1),
        ON_CITY_CLEAR(TerrainType.CITY, Weather.CLEAR, 1),
        ON_FACTORY_CLEAR(TerrainType.FACTORY, Weather.CLEAR, 1),
        ON_PLAIN_CLEAR(TerrainType.PLAIN, Weather.CLEAR, 1),
        ON_FOREST_CLEAR(TerrainType.FOREST, Weather.CLEAR, 1),
        ON_MOUNTAIN_CLEAR(TerrainType.MOUNTAIN, Weather.CLEAR, 1),
        ON_WATER_CLEAR(TerrainType.WATER, Weather.CLEAR, 1);

        private final TerrainType terrainType;
        private final Weather weather;
        private final int cost;

        MovementCost(TerrainType terrainType, Weather weather, int cost) {

            this.terrainType = terrainType;
            this.weather = weather;
            this.cost = cost;

        }

        public static MovementCost fromTerrainAndWeather(TerrainType terrainType, Weather weather) {

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
