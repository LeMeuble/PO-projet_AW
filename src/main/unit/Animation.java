package main.unit;

public enum Animation {

    IDLE("idle"),
    MOVE_UP("moveup"),
    MOVE_DOWN("movedown"),
    MOVE_LEFT("moveleft"),
    MOVE_RIGHT("moveright");

    private final String name;

    Animation(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
