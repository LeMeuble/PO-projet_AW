package fr.istic.map;

/**
 * Classe representant des coordonnees
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Coordinate implements Cloneable {

    private int x;
    private int y;

    /**
     * Constructeur d'une coordonnee, initialisee a 0:0
     */
    public Coordinate() {
        this(0, 0);
    }

    /**
     * Constructeur d'une coordonnee, initialisee a x:y
     *
     * @param x
     * @param y
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Retourne une distance (distance de Manhattan) entre cette instance et une autre instance
     *
     * @param other L'autre instance a laquelle calculer la distance
     *
     * @return La distance entre les deux instances
     */
    public int distance(Coordinate other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    @Override
    public Coordinate clone() {
        try {
            return (Coordinate) super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Coordinate)) return false;
        Coordinate other = (Coordinate) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public String toString() {
        return "(C: " + this.x + "," + this.y + ")";
    }

}
