package main.unit;

public enum UnitAnimation {

    IDLE("idle"),
    MOVE_UP("moveup"),
    MOVE_DOWN("movedown"),
    MOVE_LEFT("moveleft"),
    MOVE_RIGHT("moveright");

    private final String name;

    UnitAnimation(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
