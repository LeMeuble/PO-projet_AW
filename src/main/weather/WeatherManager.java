package main.weather;

import ressources.Config;

public class WeatherManager {

    public enum ChangingWeather {

        Clear(0, 0.6, 0.3, 0.1),
        Wind(0.5, 0, 0.4, 0.1),
        Rain(0.7, 0.2, 0, 0.1),
        Snow(0.1, 0.6, 0.3, 0);

        private final double toClear;
        private final double toWind;
        private final double toRain;
        private final double toSnow;

        ChangingWeather(double toClear, double toWind, double toRain, double toSnow) {
            this.toClear = toClear;
            this.toWind = toWind;
            this.toRain = toRain;
            this.toSnow = toSnow;
        }

        public double getToClear() {
            return toClear;
        }

        public double getToWind() {
            return toWind;
        }

        public double getToRain() {
            return toRain;
        }

        public double getToSnow() {
            return toSnow;
        }

    }

    private static final double WEATHER_CHANGE_CHANCE = Config.CHANGING_WEATHER;

    private Weather currentWeather;
    private Weather nextWeather;
    private boolean willChange; // Weather will on next turn
    private boolean hasChanged; // Weather has changed on current turn
    private boolean randomMode;

    public WeatherManager() {
        this.currentWeather = Weather.random();
        this.nextWeather = Weather.random();
        this.randomMode = true;
    }

    public void toggleRandomMode() {
        this.randomMode = !this.randomMode;
    }

    public Weather getCurrentWeather() {
        return this.currentWeather;
    }

    public Weather getNextWeather() {
        return this.nextWeather;
    }


    public void clock() {
        if (this.randomMode) {
            if (this.willChange) {
                this.currentWeather = this.nextWeather;
                this.willChange = false;
                this.hasChanged = true;
            }
            else if (this.hasChanged) {
                this.hasChanged = false;
            }

            else if (Math.random() < WEATHER_CHANGE_CHANCE) {

                this.willChange = true;
                this.nextWeather = this.currentWeather.next();

            }
        }
    }

    public boolean willChange() {
        return this.willChange;
    }

    public boolean hasChanged() {
        return this.hasChanged;
    }

}
