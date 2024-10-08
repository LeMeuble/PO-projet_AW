package fr.istic.animation;

import fr.istic.Config;
import fr.istic.DisplayUtil;
import fr.istic.MiniWars;
import fr.istic.game.Game;
import fr.istic.game.Movement;
import fr.istic.map.Coordinate;
import fr.istic.map.Grid;
import fr.istic.unit.Unit;
import fr.istic.unit.UnitAnimation;
import fr.istic.unit.UnitFacing;

import java.util.LinkedList;

/**
 * Classe representant une animation de deplacement pour
 * les unites.
 *
 * @author PandaLunatique
 * @author LeMeuble
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
     * @param unit     L'unite concernee par l'animation
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

    /**
     * Change la direction d'une unite pour qu'elle soit coherente avec le mouvement
     */
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

            if (this.direction == Movement.Direction.LEFT) {
                unit.setFacing(UnitFacing.LEFT);
            }
            else if (this.direction == Movement.Direction.RIGHT) {
                unit.setFacing(UnitFacing.RIGHT);
            }

            this.unit.setCoordinate(this.gridX, this.gridY);
            final Grid grid = MiniWars.getInstance().getCurrentGame().getGrid();
            grid.updateFogOfWar(grid.getCase(new Coordinate(this.gridX, this.gridY)), this.unit);

        }

        this.pixelX = this.calculatePixelX();
        this.pixelY = this.calculatePixelY();

    }

    /**
     * Permet de calculer la position en X du sprite a afficher sur l'ecran.
     *
     * @return Position en pixel x
     */
    private double calculatePixelX() {

        Game game = MiniWars.getInstance().getCurrentGame();

        int offsetX = game.getView().offsetX(this.gridX);
        double stepX = this.direction.getDx() * this.step * Config.PIXEL_PER_CASE / ANIMATION_STEP_PER_CASE;

        return DisplayUtil.getCenterX(offsetX, game.getWidth()) + stepX;

    }

    /**
     * Permet de calculer la position en Y du sprite a afficher sur l'ecran.
     *
     * @return Position en pixel Y
     */
    private double calculatePixelY() {

        Game game = MiniWars.getInstance().getCurrentGame();

        int offsetY = game.getView().offsetY(this.gridY);
        double stepY = this.direction.getDy() * this.step * Config.PIXEL_PER_CASE / ANIMATION_STEP_PER_CASE;

        return DisplayUtil.getCenterY(offsetY, game.getHeight()) + stepY;

    }

}
