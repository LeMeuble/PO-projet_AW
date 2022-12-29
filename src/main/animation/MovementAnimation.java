package main.animation;

import main.game.Movement;
import main.unit.Unit;

public class MovementAnimation {

    private final Unit unit;
    private final Movement movement;
    private boolean isFinished;

    public MovementAnimation(Unit unit, Movement movement) {
        this.unit = unit;
        this.movement = movement;
        this.isFinished = false;
    }

    public void waitUntilFinished() {

        while (!this.isFinished) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
