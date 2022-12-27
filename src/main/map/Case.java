package main.map;

import main.render.AnimationClock;
import main.terrain.AnimatedTerrain;
import main.terrain.Property;
import main.terrain.Terrain;
import main.unit.Unit;
import main.weather.Weather;
import ressources.DisplayUtil;

/**
 * Classe representant une Case
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Case {

    private final int x;
    private final int y;

    private final Terrain terrain;
    private Unit unit;

    /**
     * Constructeur d'une case
     * @param x La coordonnee x de la case
     * @param y La coordonnee y de la case
     * @param terrain Le terrain de la case
     */
    public Case(int x, int y, Terrain terrain) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;
        this.unit = null;
    }

    /**
     * @return La coordonnee x de la case dans la grille
     */
    public int getX() {
        return x;
    }

    /**
     * @return La coordonnee y de la case dans la grille
     */
    public int getY() {
        return y;
    }


    /**
     * Definit l'unite presente sur la case
     * @param unit Une unite
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * @return L'unite presente sur la case
     */
    public Unit getUnit() {
        return this.unit;
    }

    /**
     * @return true si il y a une unite sur la case, false sinon
     */
    public boolean hasUnit() {
        return this.unit != null;
    }

    /**
     * Verifie si l'unite presente sur la case est vivante, sinon la fait disparaitre
     */
    public void garbageUnit() {
        if(this.hasUnit() && !this.getUnit().isAlive()) this.unit = null;
    }

    /**
     * @return Le terrain de la case
     */
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

        // TODO: Split this method in two, one for terrain and one for unit

        this.renderUnit(x, y, game, terrainClockSync, unitClockSync);

    }

    public void renderUnit(int x, int y, Game game, AnimationClock terrainClockSync, AnimationClock unitClockSync) {
        if (this.hasUnit()) DisplayUtil.drawPictureInCase(x, y, game.getWidth(), game.getHeight(), this.getUnit().getFile(unitClockSync.getFrame()));
    }

    /**
     * @return Les coordonnee de la case, sous le format d'un tuple
     */
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

}
