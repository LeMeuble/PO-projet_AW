package main.weather;

/**
 * Enumeration de tous les types de meteo
 */
public enum Weather {

    CLEAR("Beau temps", "clear", 0, 0.3, 0.1, 0.6),
    RAINY("Pluie", "rainy", 0.5, 0.4, 0.1, 0),
    SNOWY("Neige", "snowy", 0.7, 0, 0.1, 0.2),
    HEAVY_WIND("Vent violent", "rainy", 0.1, 0.3, 0, 0.6);

    private final String name;
    private final String textureName;
    private final double[] probabilities;

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

    public Weather next() {

        if(probabilities.length != Weather.values().length) {
            throw new IllegalArgumentException("Probabilities must have " + Weather.values().length + " values");
        }

        double random = Math.random();
        double previous = 0;

        for (int i = 0; i < probabilities.length; i++) {
            if(random >= previous && random < previous + probabilities[i]) {
                System.out.println("Next weather is " + Weather.values()[i].getTextureName());
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
