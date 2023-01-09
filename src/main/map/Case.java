package main.map;

import main.MiniWars;
import main.animation.AnimationClock;
import main.game.Game;
import main.game.GameView;
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

    private final Coordinate coordinate;
    private final Terrain terrain;

    private boolean isFoggy;

    private Unit unit;

    /**
     * Constructeur d'une case
     *
     * @param coordinate Coordonnees de la case
     * @param terrain    Le terrain de la case
     */
    public Case(Coordinate coordinate, Terrain terrain) {
        this.coordinate = coordinate;
        this.terrain = terrain;
        this.unit = null;
        this.isFoggy = true;
    }

    public Coordinate getCoordinate() {
        return this.coordinate.clone();
    }

    /**
     * Retourne une distance (distance de Manhattan) entre la case et une autre case
     *
     * @param other La case a laquelle calculer la distance
     *
     * @return La distance entre les deux cases
     */
    public double distance(Case other) {
        return this.coordinate.distance(other.coordinate);
    }

    public boolean isAdjacent(Case other) {
        return this.distance(other) == 1;
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

        if(unit != null) {
            unit.setCoordinate(this.coordinate.clone());
        }

    }

    /**
     * @return true si il y a une unite sur la case, false sinon
     */
    public boolean hasUnit() {
        return this.unit != null;
    }

    /**
     * @return Le terrain de la case
     */
    public Terrain getTerrain() {
        return this.terrain;
    }

    public boolean isFoggy() {
        return isFoggy;
    }

    public void setFoggy(boolean isFoggy) {this.isFoggy = isFoggy;}

    public void render() {

        final Game game = MiniWars.getInstance().getCurrentGame();

        final int offsetX = game.getView().offsetX(this.coordinate.getX());
        final int offsetY = game.getView().offsetY(this.coordinate.getY());

        final double x = DisplayUtil.getCenterX(offsetX, game.getWidth());
        final double y = DisplayUtil.getCenterX(offsetX, game.getWidth());

    }

    public void renderTerrain(double pixelX, double pixelY, Weather weather, int frame) {

        if (terrain instanceof AnimatedTerrain) {
            DisplayUtil.drawPicture(pixelX, pixelY, ((AnimatedTerrain) terrain).getFile(weather, this.isFoggy, frame), Config.PIXEL_PER_CASE, Config.PIXEL_PER_CASE);
        }
        else if (terrain instanceof Property) {
            DisplayUtil.drawPicture(pixelX, pixelY + Config.PIXEL_PER_CASE / 2, terrain.getFile(weather, this.isFoggy), Config.PIXEL_PER_CASE, Config.PIXEL_PER_CASE * 2);
        }
        else {
            DisplayUtil.drawPicture(pixelX, pixelY, terrain.getFile(weather, this.isFoggy), Config.PIXEL_PER_CASE, Config.PIXEL_PER_CASE);
        }

    }

    public void renderUnit(double pixelX, double pixelY, int frame) {

        if (this.hasUnit() && this.getUnit().isAlive()) {

            final Unit unit = this.getUnit();

            DisplayUtil.drawPicture(pixelX, pixelY, unit.getFile(frame), Config.PIXEL_PER_CASE, Config.PIXEL_PER_CASE);

            double offsetDivider = (unit instanceof OnFoot) ? 4.0d : 5.0d;
            double indicatorX = pixelX + Config.PIXEL_PER_CASE / offsetDivider;
            double indicatorY = pixelY - Config.PIXEL_PER_CASE / offsetDivider;

            if (unit.getHealth() < Unit.MAX_HEALTH) {

                DisplayUtil.drawPicture(pixelX, pixelY, PathUtil.getHealthPath((int) Math.floor(unit.getHealth()), !unit.hasPlayed()), Config.PIXEL_PER_CASE / 3, Config.PIXEL_PER_CASE / 3);

            }

            boolean isLowAmmo = unit.getWeapons().stream()
                    .anyMatch(weapon -> weapon.getAmmo() <= weapon.getDefaultAmmo() * Config.UNIT_LOW_AMMO_THRESHOLD);
            boolean noAmmo = unit.getWeapons().stream()
                    .allMatch(weapon -> weapon.getAmmo() == 0);

            boolean displayLowAmmo = ((isLowAmmo && (frame % 2 == 0)) || noAmmo) && unit.hasAnyWeapon();

            if (displayLowAmmo) {
                pixelY += Config.PIXEL_PER_CASE / 3;
                DisplayUtil.drawPicture(pixelX, pixelY, PathUtil.getIndicatorPath("low_ammo"), Config.PIXEL_PER_CASE / 3, Config.PIXEL_PER_CASE / 3);
            }

//            boolean displayLowFuel = false;
//
//            if(unit instanceof Motorized) {
//
//                Motorized motorized = (Motorized) unit;
//                displayLowFuel = motorized.getFuel() <= motorized.getf() * Config.UNIT_LOW_FUEL_THRESHOLD;
//
//            }
//
//
//            DisplayUtil.drawPicture(pixelX, pixelY, PathUtil.getIndicatorPath((int) unit.getHealth() - 1, !unit.hasPlayed()), Config.PIXEL_PER_CASE / 3, Config.PIXEL_PER_CASE / 3);

        }
    }

    /**
     * @return Les coordonnee de la case, sous le format d'un tuple
     */
    public String toString() {
        return "(" + this.coordinate.getX() + ", " + this.coordinate.getY() + ")";
    }

}
