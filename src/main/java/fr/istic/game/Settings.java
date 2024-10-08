package fr.istic.game;

import fr.istic.weather.Weather;
import fr.istic.weather.WeatherManager;

/**
 * Classe representant les parametres d'une partie
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Settings {

    /**
     * Enumeration des modes de meteo possibles pour la partie
     */
    public enum WeatherMode {

        AUTO("Meteo Automatique"),
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

    /**
     * Constructeur de Settings
     * Par defaut, le meteo est en mode "automatique" (change a chaque tour)
     * Par defaut, le brouillard de guerre est active
     * Par defaut, le passage de tour automatique est desactive
     */
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

    /**
     * Passe au mode de meteo suivant (ex : passe de CLEAR vers RAINY)
     *
     * @see Settings.WeatherMode
     */
    public void nextWeatherMode() {
        this.weatherMode = WeatherMode.values()[(this.weatherMode.ordinal() + 1) % WeatherMode.values().length];
    }

    /**
     * Passe au mode de meteo precedent (ex : passe de RAINY vers CLEAR)
     *
     * @see Settings.WeatherMode
     */
    public void previousWeatherMode() {
        this.weatherMode = WeatherMode.values()[(this.weatherMode.ordinal() - 1 + WeatherMode.values().length) % WeatherMode.values().length];
    }

    public WeatherMode getWeatherMode() {
        return this.weatherMode;
    }

    /**
     * Configure le gestionnaire de meteo du jeu, en fonction du mode de meteo selectionne
     *
     * @param manager Le gestionnaire de meteo du jeu
     */
    public void configureWeatherManager(WeatherManager manager) {
        if (this.weatherMode == WeatherMode.AUTO) {
            manager.setRandomMode(true);
        }
        else {
            manager.setRandomMode(false);
            manager.setCurrentWeather(this.weatherMode.weather);
        }
    }

}
