package main.map;

import main.render.AnimationClock;
import main.terrain.AnimatedTerrain;
import main.terrain.Property;
import main.terrain.Terrain;
import main.unit.Unit;
import main.weather.Weather;
import ressources.DisplayUtil;

public class Case {

    private final int x;
    private final int y;

    private final Terrain terrain;
    private Unit unit;

    public Case(int x, int y, Terrain terrain) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;
        this.unit = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public boolean hasUnit() {
        return this.unit != null;
    }

    public Terrain getTerrain() {
        return this.terrain;
    }

    public void render(int x, int y, Game game, AnimationClock terrainClockSync, AnimationClock unitClockSync) {

        final int width = game.getWidth();
        final int height = game.getHeight();
        final Weather weather = game.getWeather();
        final boolean isFoggy = false;

        if (terrain instanceof AnimatedTerrain) {
            DisplayUtil.drawPictureInCase(x, y, width, height, ((AnimatedTerrain) terrain).getFile(weather, isFoggy, terrainClockSync.getFrame()));
        } else if (terrain instanceof Property) {
            DisplayUtil.drawPictureInCase(x, y, width, height, 1, 2, terrain.getFile(weather, isFoggy));
        } else {
            DisplayUtil.drawPictureInCase(x, y, width, height, terrain.getFile(weather, isFoggy));
        }

        this.renderUnit(x, y, game, terrainClockSync, unitClockSync);

    }

    public void renderUnit(int x, int y, Game game, AnimationClock terrainClockSync, AnimationClock unitClockSync) {
        if (this.hasUnit()) DisplayUtil.drawPictureInCase(x, y, game.getWidth(), game.getHeight(), this.getUnit().getFile(unitClockSync.getFrame()));
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

}
