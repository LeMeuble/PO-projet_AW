package main.animation;

import main.MiniWars;
import main.game.Game;
import main.game.Movement;
import main.map.Coordinate;
import main.map.Grid;
import main.unit.Unit;
import main.unit.UnitAnimation;
import main.unit.UnitFacing;
import ressources.Config;
import ressources.DisplayUtil;

import java.util.LinkedList;

/**
 * Classe representant une animation de deplacement pour
 * les unites.
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 *
 * @see Movement
 * @see UnitAnimation
 * @see Unit
 */
public class MovementAnimation {

    private static final int ANIMATION_STEP_PER_CASE = 4;

    private final Unit unit;
    private final LinkedList<Movement.Arrow> path;

    private int step;
    private int gridX;
    private int gridY;
    private double pixelX;
    private double pixelY;
    private Movement.Direction direction;
    private Movement.Arrow currentArrow;

    private boolean isFinished;

    /**
     * Creer une nouvelle animation de deplacement pour une unite
     *
     * @param unit L'unite concernee par l'animation
     * @param movement Le mouvement a effectuer
     */
    public MovementAnimation(Unit unit, Movement movement) {

        this.unit = unit;
        this.path = (LinkedList<Movement.Arrow>) movement.toDirectionalArrows();

        if (!this.path.isEmpty()) {
            this.step = 0;
            this.currentArrow = this.path.pollFirst();
            this.isFinished = false;

            this.direction = this.currentArrow.getTo();

            this.gridX = movement.getMovementHead().getCoordinate().getX();
            this.gridY = movement.getMovementHead().getCoordinate().getY();

            this.pixelX = this.calculatePixelX();
            this.pixelY = this.calculatePixelY();
        }
        else this.isFinished = true;

    }

    /**
     * Determiner si l'animation de mouvement est terminee
     *
     * @return true si l'animation est terminee, false sinon
     */
    public boolean isFinished() {
        return this.isFinished;
    }

    /**
     * Permet de mettre en pause la {@link Thread} courante, jusqu'a la fin de l'animation
     *
     * @implNote Cette methode est bloquante jusqu'a ce que l'animation soit terminee
     */
    @SuppressWarnings("BusyWait")
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

            this.currentArrow = this.path.pollFirst();

            if (this.currentArrow == null || this.currentArrow.getTo() == Movement.Direction.END) {
                this.isFinished = true;
                return;
            }


            this.gridX += this.direction.getDx();
            this.gridY += this.direction.getDy();
            this.direction = this.currentArrow.getTo();
            this.step = 0;

            if(this.direction == Movement.Direction.LEFT) {
                unit.setFacing(UnitFacing.LEFT);
            } else if(this.direction == Movement.Direction.RIGHT) {
                unit.setFacing(UnitFacing.RIGHT);
            }

            this.unit.setCoordinate(this.gridX, this.gridY);
            final Grid grid = MiniWars.getInstance().getCurrentGame().getGrid();
            grid.updateFogOfWar(grid.getCase(new Coordinate(this.gridX, this.gridY)), this.unit);

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
