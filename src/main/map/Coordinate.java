package main.map;

public class Coordinate {

    private int x;
    private int y;

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

    /**
     * Retourne une distance (distance de Manhattan) entre cette instance et une autre instance
     *
     * @param other L'autre instance a laquelle calculer la distance
     *
     * @return La distance entre les deux instances
     */
    public double distance(Coordinate other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

}
