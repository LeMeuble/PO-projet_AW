package main.map;

import main.animation.AnimationClock;
import main.terrain.AnimatedTerrain;
import main.terrain.Property;
import main.terrain.Terrain;
import main.unit.OnFoot;
import main.unit.Unit;
import main.weather.Weather;
import ressources.Config;
import ressources.DisplayUtil;
import ressources.PathUtil;

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
     *
     * @param x       La coordonnee x de la case
     * @param y       La coordonnee y de la case
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

    public double distance(Case other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    /**
     * @return L'unite presente sur la case
     */
    public Unit getUnit() {
        return this.unit;
    }

    /**
     * Definit l'unite presente sur la case
     *
     * @param unit Une unite
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
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
        if (this.hasUnit() && !this.getUnit().isAlive()) this.unit = null;
    }

    /**
     * @return Le terrain de la case
     */
    public Terrain getTerrain() {
        return this.terrain;
    }


    public void renderTerrain(int x, int y, int width, int height, Weather weather, AnimationClock terrainClockSync) {

        final boolean isFoggy = false;

        if (terrain instanceof AnimatedTerrain) {
            DisplayUtil.drawPictureInCase(x, y, width, height, ((AnimatedTerrain) terrain).getFile(weather, isFoggy, terrainClockSync.getFrame()));
        }
        else if (terrain instanceof Property) {
            DisplayUtil.drawPictureInCase(x, y, width, height, 1, 2, terrain.getFile(weather, isFoggy));
        }
        else {
            DisplayUtil.drawPictureInCase(x, y, width, height, terrain.getFile(weather, isFoggy));
        }

    }

    public void renderUnit(int x, int y, int width, int height, AnimationClock unitClockSync) {

        if (this.hasUnit() && this.getUnit().isAlive()) {

            final Unit unit = this.getUnit();

            DisplayUtil.drawPictureInCase(x, y, width, height, unit.getFile(unitClockSync.getFrame()));

            if (unit.getHealth() < Unit.MAX_HEALTH) {

                double offsetDivider = (unit instanceof OnFoot) ? 4.0d : 5.0d;

                double pixelX = DisplayUtil.getCenterX(x, width) + Config.PIXEL_PER_CASE / offsetDivider;
                double pixelY = DisplayUtil.getCenterY(y, height) - Config.PIXEL_PER_CASE / offsetDivider;

                DisplayUtil.drawPicture(pixelX, pixelY, PathUtil.getHealthPath((int) unit.getHealth() - 1, !unit.hasPlayed()), Config.PIXEL_PER_CASE / 3, Config.PIXEL_PER_CASE / 3);

            }
        }
    }

    /**
     * @return Les coordonnee de la case, sous le format d'un tuple
     */
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

}
