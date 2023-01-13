package main.game;

import main.weather.Weather;
import main.weather.WeatherManager;

public class Settings {

    public enum WeatherMode {

        AUTO("Météo Automatique"),
        CLEAR(Weather.CLEAR),
        RAINY(Weather.RAINY),
        SNOWY(Weather.SNOWY),
        WIND(Weather.HEAVY_WIND);

        private final String name;
        private final Weather weather;

        WeatherMode(String name) {
            this.name = name;
            this.weather = null;
        }

        WeatherMode(Weather weather) {
            this.name = weather.getName();
            this.weather = weather;
        }

        public String getName() {
            return this.name;
        }

    }
    private boolean fogOfWar;
    private boolean autoEndTurn;
    private WeatherMode weatherMode;

    public Settings() {

        this.weatherMode = WeatherMode.AUTO;
        this.fogOfWar = true;
        this.autoEndTurn = false;

    }

    public boolean isFogOfWar() {
        return this.fogOfWar;
    }

    public void toggleFogOfWar() {
        this.fogOfWar = !this.fogOfWar;
    }

    public boolean isAutoEndTurn() {
        return this.autoEndTurn;
    }

    public void toggleAutoEndTurn() {
        this.autoEndTurn = !this.autoEndTurn;
    }

    public void nextWeatherMode() {
        this.weatherMode = WeatherMode.values()[(this.weatherMode.ordinal() + 1) % WeatherMode.values().length];
    }

    public void previousWeatherMode() {
        this.weatherMode = WeatherMode.values()[(this.weatherMode.ordinal() - 1 + WeatherMode.values().length) % WeatherMode.values().length];
    }

    public WeatherMode getWeatherMode() {
        return this.weatherMode;
    }

    public void configureWeatherManager(WeatherManager manager) {
        System.out.println("mode" + this.weatherMode);
        if (this.weatherMode == WeatherMode.AUTO) {
            manager.setRandomMode(true);
        }
        else {
            manager.setRandomMode(false);
            manager.setCurrentWeather(this.weatherMode.weather);
        }
    }

}
