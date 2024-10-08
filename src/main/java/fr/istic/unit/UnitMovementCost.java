package fr.istic.unit;

import fr.istic.terrain.TerrainType;
import fr.istic.weather.Weather;

import java.util.Arrays;
import java.util.List;

/**
 * Classe representant les couts de mouvement pour rentrer sur une case
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public final class UnitMovementCost {

    /**
     * Enumeration des couts de mouvement pour rentrer sur une case pour les unites a pied
     */
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

        /**
         * Renvoie le cout d'entree sur un fr.istic.terrain, en fonction de la meteo
         *
         * @param terrainType Le type de fr.istic.terrain
         * @param weather     La meteo
         *
         * @return Un objet contenant le type du fr.istic.terrain et le cout pour y entrer
         */
        public static OnFoot fromTerrainAndWeather(TerrainType terrainType, Weather weather) {

            for (OnFoot cost : OnFoot.values()) {
                if (cost.terrainType == terrainType && (cost.weather.contains(weather) || cost.weather.isEmpty())) {
                    return cost;
                }
            }
            return null;

        }

        /**
         * Verifie si une unite peut se deplacer vers le fr.istic.terrain cible
         *
         * @param terrainType Le type de fr.istic.terrain
         * @param weather     La meteo
         *
         * @return true si le couple Type de Terrain/Meteo existe, false sinon
         */
        public static boolean isAccessible(TerrainType terrainType, Weather weather) {

            return OnFoot.fromTerrainAndWeather(terrainType, weather) != null;

        }

        public int getCost() {
            return this.cost;

        }

    }

    /**
     * Enumeration des couts de mouvement pour rentrer sur une case pour les unites motorisees
     */
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

        /**
         * Renvoie le cout d'entree sur un fr.istic.terrain, en fonction de la meteo
         *
         * @param terrainType Le type de fr.istic.terrain
         * @param weather     La meteo
         *
         * @return Un objet contenant le type du fr.istic.terrain et le cout pour y entrer
         */
        public static Motorized fromTerrainAndWeather(TerrainType terrainType, Weather weather) {

            for (Motorized cost : Motorized.values()) {
                if (cost.terrainType == terrainType && (cost.weather.contains(weather) || cost.weather.isEmpty())) {
                    return cost;
                }
            }
            return null;

        }

        /**
         * Verifie si une unite peut se deplacer vers le fr.istic.terrain cible
         *
         * @param terrainType Le type de fr.istic.terrain
         * @param weather     La meteo
         *
         * @return true si le couple Type de Terrain/Meteo existe, false sinon
         */
        public static boolean isAccessible(TerrainType terrainType, Weather weather) {

            return Motorized.fromTerrainAndWeather(terrainType, weather) != null;

        }

        public int getCost() {
            return this.cost;

        }

    }

    /**
     * Enumeration des couts de mouvement pour rentrer dans une case pour les unites aeriennes
     */
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

        /**
         * Renvoie le cout d'entree sur un fr.istic.terrain, en fonction de la meteo
         *
         * @param terrainType Le type de fr.istic.terrain
         * @param weather     La meteo
         *
         * @return Un objet contenant le type du fr.istic.terrain et le cout pour y entrer
         */
        public static Flying fromTerrainAndWeather(TerrainType terrainType, Weather weather) {

            for (Flying cost : Flying.values()) {
                if (cost.terrainType == terrainType && (cost.weather.contains(weather) || cost.weather.isEmpty())) {
                    return cost;
                }
            }
            return null;

        }

        /**
         * Verifie si une unite peut se deplacer vers le fr.istic.terrain cible
         *
         * @param terrainType Le type de fr.istic.terrain
         * @param weather     La meteo
         *
         * @return true si le couple Type de Terrain/Meteo existe, false sinon
         */
        public static boolean isAccessible(TerrainType terrainType, Weather weather) {

            return Flying.fromTerrainAndWeather(terrainType, weather) != null;

        }

        public int getCost() {
            return this.cost;

        }

    }

    /**
     * Enumeration des couts de mouvement pour rentrer dans une case pour les unites navales
     */
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

        /**
         * Renvoie le cout d'entree sur un fr.istic.terrain, en fonction de la meteo
         *
         * @param terrainType Le type de fr.istic.terrain
         * @param weather     La meteo
         *
         * @return Un objet contenant le type du fr.istic.terrain et le cout pour y entrer
         */
        public static Naval fromTerrainAndWeather(TerrainType terrainType, Weather weather) {

            for (Naval cost : Naval.values()) {
                if (cost.terrainType == terrainType && (cost.weather.contains(weather) || cost.weather.isEmpty())) {
                    return cost;
                }
            }
            return null;

        }

        /**
         * Verifie si une unite peut se deplacer vers le fr.istic.terrain cible
         *
         * @param terrainType Le type de fr.istic.terrain
         * @param weather     La meteo
         *
         * @return true si le couple Type de Terrain/Meteo existe, false sinon
         */
        public static boolean isAccessible(TerrainType terrainType, Weather weather) {

            return Naval.fromTerrainAndWeather(terrainType, weather) != null;

        }

        public int getCost() {
            return this.cost;

        }

    }

}
