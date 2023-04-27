package fr.istic.unit;

/**
 * Enumeration des directions dans laquelle une unite peut regarder
 */
public enum UnitFacing {

    LEFT,
    RIGHT;

    public String getName() {
        return this.name().toLowerCase();
    }

    /**
     * @return Une direction aleatoire
     *
     * @see Unit constructor (used when creating a new fr.istic.map)
     */
    public static UnitFacing random() {
        return UnitFacing.values()[(int) (Math.random() * UnitFacing.values().length)];
    }

}
