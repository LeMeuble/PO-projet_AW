package main.render;

/**
 * Enumeration des types d'overlay
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public enum OverlayType {

    MOVE,
    WEAPON,
    MISC;

    public String getTextureName() {

        return this.name().toLowerCase();

    }

}
