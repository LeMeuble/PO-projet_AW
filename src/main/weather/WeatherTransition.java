package main.weather;

public enum WeatherTransition {

    CLEAR_TO_RAINY(Weather.CLEAR, Weather.RAINY, 0.2);

    private final Weather from;
    private final Weather to;
    private final double probability;

    WeatherTransition(Weather from, Weather to, double probability) {
        this.from = from;
        this.to = to;
        this.probability = probability;

    }

}
