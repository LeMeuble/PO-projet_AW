package fr.istic.map;

import fr.istic.game.Player;
import fr.istic.terrain.Property;
import fr.istic.terrain.Terrain;
import fr.istic.unit.Unit;
import fr.istic.weather.Weather;
import fr.istic.Config;

/**
 * Classe representant une Case
 *
 * @author PandaLunatique
 * @author LeMeuble
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
     * @param terrain    Le fr.istic.terrain de la case
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
    public int distance(Case other) {
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

        if (unit != null) {
            unit.setCoordinate(this.coordinate.clone());
        }

    }

    /**
     * Verifie si cette case a des actions disponible
     *
     * @param type Le type du joueur courant
     *
     * @return true si la case a des actions disponibles, false sinon
     */
    public boolean hasAvailableAction(Player.Type type) {
        return this.unit != null && this.unit.getOwner() == type && !this.unit.hasPlayed() || this.terrain instanceof Property && ((Property) this.terrain).getOwner() == type;
    }

    /**
     * @return true si il y a une unite sur la case, false sinon
     */
    public boolean hasUnit() {
        return this.unit != null;
    }

    /**
     * @return Le fr.istic.terrain de la case
     */
    public Terrain getTerrain() {
        return this.terrain;
    }

    public boolean isFoggy() {
        return isFoggy;
    }

    public void setFoggy(boolean isFoggy) {this.isFoggy = isFoggy;}

    public void renderTerrain(double pixelX, double pixelY, Weather weather, int frame) {
        this.terrain.render(pixelX, pixelY, weather, this.isFoggy, frame);
    }

    public void renderUnit(double pixelX, double pixelY, int frame) {
        this.renderUnit(pixelX, pixelY, frame, Config.PIXEL_PER_CASE, Config.PIXEL_PER_CASE, true);
    }

    public void renderUnit(double pixelX, double pixelY, int frame, double width, double height, boolean displayIndicators) {

        if (this.hasUnit()) this.getUnit().render(pixelX, pixelY, frame, width, height, displayIndicators);
    }


    /**
     * @return Les coordonnee de la case, sous le format d'un tuple
     */
    public String toString() {
        return "(" + this.coordinate.getX() + ", " + this.coordinate.getY() + ")";
    }

}
