package main.weather;

public enum Weather {

    CLEAR,
    RAINY,
    SNOWY;

    public String getName() {
        return this.name().toLowerCase();
    }

    public static Weather random() {
        return Weather.values()[(int) (Math.random() * Weather.values().length)];
    }

}
