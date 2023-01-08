package main.weather;

/**
 * Enumeration de tous les types de meteo
 */
public enum Weather {

    CLEAR("clear", 0, 0.3, 0.1, 0.6),
    RAINY("rainy", 0.5, 0.4, 0.1, 0),
    SNOWY("snowy", 0.7, 0, 0.1, 0.2),
    HEAVY_WIND("rainy", 0.1, 0.3, 0, 0.6);

    private final String textureName;
    private final double[] probabilities;

    Weather(String textureName, double... probabilities) {
        this.textureName = textureName;
        this.probabilities = probabilities;

    }

    public String getName() {
        return this.textureName;
    }

    public Weather next() {

        if(probabilities.length != Weather.values().length) {
            throw new IllegalArgumentException("Probabilities must have " + Weather.values().length + " values");
        }

        double random = Math.random();
        double previous = 0;

        for (int i = 0; i < probabilities.length; i++) {
            if(random >= previous && random < previous + probabilities[i]) {
                System.out.println("Next weather is " + Weather.values()[i].getName());
                return Weather.values()[i];
            }
            previous += probabilities[i];
        }
        return this;
    }

    public static Weather random() {
        return Weather.values()[(int) (Math.random() * Weather.values().length)];
    }

}
