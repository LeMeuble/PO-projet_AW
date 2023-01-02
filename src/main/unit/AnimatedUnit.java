package main.unit;

import main.animation.AnimationClock;
import main.game.Player;

/**
 * Class abstraite representant une unitee animee
 *
 * @author Tristan LECONTE--DENIS
 */
public abstract class AnimatedUnit extends Unit {

    private UnitAnimation unitAnimation;
    private AnimationClock animationClock;

    /**
     * Constructeur de l'unite animee
     * @param owner Le type du joueur proprietaire de l'unite
     * @param frameCount Le nombre de frames de l'animation
     * @param frameDuration La duree d'une frame
     */
    public AnimatedUnit(Player.Type owner, int frameCount, int frameDuration) {

        super(owner);
        this.unitAnimation = UnitAnimation.IDLE;
        this.animationClock = new AnimationClock(frameCount, frameDuration, true);

    }

    /**
     * @return La frame courante de l'animation
     */
    public int getFrame() {
        return this.animationClock.getFrame();
    }

    /**
     * Definit la frame courante de l'animation
     * @param frame La nouvelle frame
     */
    public void setFrame(int frame) {
        this.animationClock.setFrame(frame);
    }







}
