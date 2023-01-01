package main.unit;

/**
 * Enumeration des animations possibles pour les unites
 */
public enum UnitAnimation {

    // Animation : Ne rien faire
    IDLE("idle"),

    // Animation : Se deplacer vers le haut
    MOVE_UP("moveup"),

    // Animation : Se deplacer vers le bas
    MOVE_DOWN("movedown"),

    // Animation : Se deplacer vers la gauche
    MOVE_LEFT("moveleft"),

    // Animation : Se deplacer vers la droite
    MOVE_RIGHT("moveright");

    private final String name;

    UnitAnimation(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
