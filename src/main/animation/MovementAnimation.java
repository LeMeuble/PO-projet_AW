package main.animation;

import main.game.Movement;
import main.render.Renderable;
import main.unit.Unit;

import java.util.Vector;

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
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("The following error is handled: " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

}
