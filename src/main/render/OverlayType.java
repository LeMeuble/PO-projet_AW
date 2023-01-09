package main.render;

public enum OverlayType {

    MOVE,
    WEAPON,
    MISC;

    public String getTextureName() {

        return this.name().toLowerCase();

    }

}
