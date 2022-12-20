package main.terrain;

import main.Player;
import main.unit.Unit;

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


    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

}
