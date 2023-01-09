package main.weather;

import ressources.Config;

public class WeatherManager {

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
