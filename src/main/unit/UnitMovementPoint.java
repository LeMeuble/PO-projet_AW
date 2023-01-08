package main.unit;

import main.unit.type.*;
import main.weather.Weather;

import java.util.Arrays;
import java.util.List;

public enum UnitMovementPoint {

    INFANTRY(UnitType.INFANTRY, 300),
    BAZOOKA(UnitType.BAZOOKA, 2),
    BOMBER(UnitType.BOMBER, 7),
    CONVOY(UnitType.CONVOY, 60),
    ANTIAIR(UnitType.ANTIAIR, 6),
    HELICOPTER(UnitType.HELICOPTER, 6),
    TANK(UnitType.TANK, 6),
    ARTILLERY(UnitType.ARTILLERY, 5),
    SAMLAUNCHER(UnitType.SAMLAUNCHER, 6);

    private final UnitType unitType;
    private final int movementPoint;
    private final List<Weather> weather;

    UnitMovementPoint(UnitType unitType, int movementPoint, Weather... weather) {
        this.unitType = unitType;
        this.movementPoint = movementPoint;
        this.weather = Arrays.asList(weather);
    }

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
