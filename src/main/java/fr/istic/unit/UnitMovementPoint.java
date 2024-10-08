package fr.istic.unit;

import fr.istic.weather.Weather;

import java.util.Arrays;
import java.util.List;

/**
 * Enumeration representant les points de mouvement maximaux des unites
 */
public enum UnitMovementPoint {

    AIRCRAFT_CARRIER_NORMAL(UnitType.AIRCRAFT_CARRIER, 5),
    ANTI_AIR_NORMAL(UnitType.ANTI_AIR, 6),
    ARTILLERY_NORMAL(UnitType.ARTILLERY, 5),
    BAZOOKA_NORMAL(UnitType.BAZOOKA, 2),
    BOMBER_NORMAL(UnitType.BOMBER, 7, Weather.CLEAR, Weather.HEAVY_WIND, Weather.RAINY),
    CONVOY_NORMAL(UnitType.CONVOY, 6),
    CORVETTE_NORMAL(UnitType.CORVETTE, 7),
    CRUISER_NORMAL(UnitType.CRUISER, 6),
    DREADNOUGHT_NORMAL(UnitType.DREADNOUGHT, 5),
    HELICOPTER_NORMAL(UnitType.HELICOPTER, 6, Weather.CLEAR, Weather.HEAVY_WIND, Weather.RAINY),
    INFANTRY_NORMAL(UnitType.INFANTRY, 3),
    LANDING_SHIP_NORMAL(UnitType.LANDING_SHIP, 6),
    SAM_LAUNCHER_NORMAL(UnitType.SAM_LAUNCHER, 4),
    SUBMARINE_NORMAL(UnitType.SUBMARINE, 5),
    TANK_NORMAL(UnitType.TANK, 6),
    BOMBER_SNOWY(UnitType.BOMBER, 5, Weather.SNOWY),
    HELICOPTER_SNOWY(UnitType.HELICOPTER, 4, Weather.SNOWY);


    private final UnitType unitType;
    private final int movementPoint;
    private final List<Weather> weather;

    UnitMovementPoint(UnitType unitType, int movementPoint, Weather... weather) {
        this.unitType = unitType;
        this.movementPoint = movementPoint;
        this.weather = Arrays.asList(weather);
    }

    /**
     * Renvoie les points de mouvement d'une unite, en fonction de la meteo
     *
     * @param unitType Le type de l'unite
     * @param weather  La meteo
     *
     * @return Un object UnitMovementPoint
     */
    public static UnitMovementPoint fromUnitAndWeather(UnitType unitType, Weather weather) {
        for (UnitMovementPoint u : UnitMovementPoint.values()) {
            if (u.unitType == unitType && (u.weather.contains(weather) || u.weather.isEmpty())) {
                return u;
            }
        }
        return null;
    }

    public int getMovementPoint() {
        return this.movementPoint;
    }

}
