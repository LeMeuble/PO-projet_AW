package main.terrain;

import main.weather.Weather;

public abstract class AnimatedTerrain extends Terrain {

    @Override
    public String getFile(Weather weather, boolean isFoggy) {
        return this.getFile(weather, isFoggy, 0);
    }
    public abstract String getFile(Weather weather, boolean isFoggy, int frame);

}