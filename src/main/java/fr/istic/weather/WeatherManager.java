package fr.istic.weather;

import fr.istic.Config;

/**
 * Classe representant le gestionnaire de meteo.
 * Ce gestionnaire permet d'avoir une meteo statique ou dynamique selon
 * les parametres definies a l'initialisation.
 * La meteo en mode dynamique est mise a jour a chaque changement de tour
 * avec une probabilite de {@link #WEATHER_CHANGE_CHANCE}.
 * La meteo suivante est choisie aleatoire parmis les meteos definies (et des
 * probabilites definies) selon la meteo precedente. Cela permet d'eviter
 * les transitions "brutales".
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class WeatherManager {

    private static final double WEATHER_CHANGE_CHANCE = Config.CHANGING_WEATHER;
    private static final double WEATHER_CHANGE_EXTRA_CHANCE = Config.CHANGING_WEATHER_EXTRA_PER_PLAYER;

    private Weather currentWeather;
    private Weather nextWeather;
    private boolean willChange; // Weather will on next turn
    private boolean hasChanged; // Weather has changed on current turn
    private boolean randomMode;

    /**
     * Creer un gestionnaire de meteo.
     */
    public WeatherManager() {
        this.currentWeather = Weather.random();
        this.nextWeather = Weather.random();
        this.randomMode = true;
    }

    /**
     * Changer le mode de meteo. (statique ou dynamique)
     *
     * @param randomMode true si le mode est dynamique, false sinon
     */
    public void setRandomMode(boolean randomMode) {
        this.randomMode = randomMode;
    }

    /**
     * Obtenir la meteo courante
     *
     * @return La meteo courante
     *
     * @see Weather
     */
    public Weather getCurrentWeather() {
        return this.currentWeather;
    }

    /**
     * Change la meteo courante.
     *
     * @param weather La nouvelle meteo.
     *
     * @see Weather
     */
    public void setCurrentWeather(Weather weather) {
        this.currentWeather = weather;
    }

    /**
     * Obtenir la meteo suivante
     *
     * @return La meteo suivante
     *
     * @see Weather
     */
    public Weather getNextWeather() {
        return this.nextWeather;
    }

    /**
     * Methode appelle a chaque changement de tour.
     * Met a jour la meteo si le mode est dynamique avec une probabilite dependante du
     * nombre de joueur dans la partie.
     *
     * @param playerCount Le nombre de joueurs dans la partie
     */
    public void clock(int playerCount) {

        if (this.randomMode) {
            if (this.willChange) {
                this.currentWeather = this.nextWeather;
                this.willChange = false;
                this.hasChanged = true;
            }
            else if (this.hasChanged) {
                this.hasChanged = false;
            }
            else if (Math.random() < WEATHER_CHANGE_CHANCE + WEATHER_CHANGE_EXTRA_CHANCE * playerCount) {

                final Weather nextWeather = this.currentWeather.next();
                this.willChange = nextWeather != this.currentWeather;
                this.nextWeather = nextWeather;

            }
        }

    }

    /**
     * Determine si la meteo va changer au prochain tour.
     *
     * @return true si la meteo va changer, false sinon
     */
    public boolean willChange() {
        return this.willChange;
    }

    /**
     * Determine si la meteo a change au tour courant.
     *
     * @return true si la meteo a change, false sinon
     */
    public boolean hasChanged() {
        return this.hasChanged;
    }

}
