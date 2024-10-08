package fr.istic.render;

/**
 * Enumeration des types d'overlay
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public enum OverlayType {

    MOVE,
    WEAPON,
    MISC;

    public String getTextureName() {

        return this.name().toLowerCase();

    }

}
