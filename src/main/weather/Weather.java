package main.weather;

/**
 * Enumeration de tous les types de meteo
 */
public enum Weather {

    CLEAR("clear", 0, 0.6, 0.3, 0.1),
    RAINY("rainy", 0.5, 0, 0.4, 0.1),
    SNOWY("snowy", 0.7, 0.2, 0, 0.1),
    HEAVY_WIND("rainy", 0.1, 0.6, 0.3, 0);

    private final String textureName;
    private final double toClear;
    private final double toWind;
    private final double toRain;
    private final double toSnow;

    Weather(String textureName, double toClear, double toWind, double toRain, double toSnow) {
        this.textureName = textureName;
        this.toClear = toClear;
        this.toWind = toWind;
        this.toRain = toRain;
        this.toSnow = toSnow;
    }

    public String getName() {
        return this.textureName;
    }

    public static Weather random() {
        return Weather.values()[(int) (Math.random() * Weather.values().length)];
    }

}
