package fr.istic.weather;

import fr.istic.Language;

/**
 * Enumeration de tous les types de meteo
 */
public enum Weather {

    CLEAR(Language.WEATHER_CLEAR, "clear", 0, 0.3, 0.1, 0.6),
    RAINY(Language.WEATHER_RAINY, "rainy", 0.5, 0.4, 0.1, 0),
    SNOWY(Language.WEATHER_SNOWY, "snowy", 0.7, 0, 0.1, 0.2),
    HEAVY_WIND(Language.WEATHER_HEAVY_WIND, "rainy", 0.1, 0.3, 0, 0.6);

    private final String name;
    private final String textureName;
    private final double[] probabilities;

    /**
     * Constructeur de Weather
     *
     * @param name          Le nom de la meteo
     * @param textureName   Le nom de la texture
     * @param probabilities Les probabilites des changements de meteo
     */
    Weather(String name, String textureName, double... probabilities) {
        this.name = name;
        this.textureName = textureName;
        this.probabilities = probabilities;

    }

    public String getName() {
        return this.name;
    }

    public String getTextureName() {
        return this.textureName;
    }

    /**
     * Calcule la prochaine meteo en fonction de la meteo precedente
     *
     * @return La prochaine meteo (peut etre la meteo courante)
     */
    public Weather next() {

        if (probabilities.length != Weather.values().length) {
            throw new IllegalArgumentException("Probabilities must have " + Weather.values().length + " values");
        }

        double random = Math.random();
        double previous = 0;

        // Verifie entre quel intervalle se situe le nombre aleatoire
        for (int i = 0; i < probabilities.length; i++) {
            if (random >= previous && random < previous + probabilities[i]) {
                return Weather.values()[i];
            }
            previous += probabilities[i];
        }
        return this;
    }

    /**
     * @return Une valeur aleatoire parmi les meteos possibles
     */
    public static Weather random() {
        return Weather.values()[(int) (Math.random() * Weather.values().length)];
    }

}
