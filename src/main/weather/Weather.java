package main.weather;

/**
 * Enumeration de tous les types de meteo
 */
public enum Weather {

    CLEAR("clear"),
    RAINY("rainy"),
    SNOWY("snowy"),
    HEAVY_WIND("rainy");

    private final String textureName;

    Weather(String textureName) {
        this.textureName = textureName;
    }
    public String getName() {
        return this.textureName;
    }

    public static Weather random() {
        return Weather.values()[(int) (Math.random() * Weather.values().length)];
    }

}
