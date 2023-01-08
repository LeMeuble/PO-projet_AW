package main.animation;

import main.MiniWars;
import main.game.Game;
import main.game.Movement;
import main.unit.Unit;
import main.unit.UnitAnimation;
import main.unit.UnitFacing;
import ressources.Config;
import ressources.DisplayUtil;

import java.util.LinkedList;

public class MovementAnimation {

    private static final int ANIMATION_STEP_PER_CASE = 4;

    private final Unit unit;
    private final LinkedList<Movement.Arrow> arrowPath;

    private int step;
    private int gridX;
    private int gridY;
    private double pixelX;
    private double pixelY;
    private Movement.Direction direction;
    private Movement.Arrow currentArrow;

    private boolean isFinished;

    public MovementAnimation(Unit unit, Movement movement) {

        this.unit = unit;
        this.arrowPath = (LinkedList<Movement.Arrow>) movement.toDirectionalArrows();

        if (!this.arrowPath.isEmpty()) {
            this.step = 0;
            this.currentArrow = this.arrowPath.pollFirst();
            this.isFinished = false;

            this.direction = this.currentArrow.getTo();

            if(this.direction == Movement.Direction.LEFT) {
                unit.setFacing(UnitFacing.LEFT);
                System.out.println("LEFT");
            } else if(this.direction == Movement.Direction.RIGHT) {
                unit.setFacing(UnitFacing.RIGHT);
                System.out.println("RIGHT");
            }

            this.gridX = movement.getMovementHead().getCoordinate().getX();
            this.gridY = movement.getMovementHead().getCoordinate().getY();

            this.pixelX = this.calculatePixelX();
            this.pixelY = this.calculatePixelY();
        }
        else this.isFinished = true;

    }

    public boolean isFinished() {
        return this.isFinished;
    }

    public void waitUntilFinished() {

        while (!this.isFinished) {
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException ignored) {
            }
        }

    }

    public int getGridX() {
        return this.gridX;
    }

    public int getGridY() {
        return this.gridY;
    }

    public double getPixelX() {
        return this.pixelX;
    }

    public double getPixelY() {
        return this.pixelY;
    }

    public UnitAnimation getUnitAnimation() {
        return UnitAnimation.valueOf("MOVE_" + this.direction.name());
    }

    public Unit getUnit() {
        return this.unit;
    }

    public void nextStep() {

        if (this.isFinished) return;

        this.step++;

        if (this.step == ANIMATION_STEP_PER_CASE) {

            this.currentArrow = this.arrowPath.pollFirst();

            if (this.currentArrow == null || this.currentArrow.getTo() == Movement.Direction.END) {
                this.isFinished = true;
                return;
            }

            this.gridX += this.direction.getDx();
            this.gridY += this.direction.getDy();
            this.direction = this.currentArrow.getTo();
            this.step = 0;

        }

        this.pixelX = this.calculatePixelX();
        this.pixelY = this.calculatePixelY();

    }

    private double calculatePixelX() {

        Game game = MiniWars.getInstance().getCurrentGame();

        int offsetX = game.getView().offsetX(this.gridX);
        double stepX = this.direction.getDx() * this.step * Config.PIXEL_PER_CASE / ANIMATION_STEP_PER_CASE;

        return DisplayUtil.getCenterX(offsetX, game.getWidth()) + stepX;

    }

    private double calculatePixelY() {

        Game game = MiniWars.getInstance().getCurrentGame();

        int offsetY = game.getView().offsetY(this.gridY);
        double stepY = this.direction.getDy() * this.step * Config.PIXEL_PER_CASE / ANIMATION_STEP_PER_CASE;

        return DisplayUtil.getCenterY(offsetY, game.getHeight()) + stepY;

    }

}
