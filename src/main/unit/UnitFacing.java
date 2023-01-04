package main.unit;

/**
 * Enumeration des directions dans laquelle une unite peut regarder
 */
public enum UnitFacing {

    LEFT,
    RIGHT;

    public String getName() {
        return this.name().toLowerCase();
    }

    public static UnitFacing random() {
        return UnitFacing.values()[(int) (Math.random() * UnitFacing.values().length)];
    }

}
