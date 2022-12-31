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

    /**
     * Definit la nouvelle animation de l'unite
     * L'animation va continuer en fonction du numero de la frame courante
     * @param unitAnimation La nouvelle animation
     */
    public void setAnimation(UnitAnimation unitAnimation) {
        this.unitAnimation = unitAnimation;
    }

    /**
     * Change l'animation de l'unite et reinitialise les frames
     * @param unitAnimation La nouvelle animation
     */
    public void switchAnimation(UnitAnimation unitAnimation) {
        this.setAnimation(unitAnimation);
        this.setFrame(0);
    }

    public void newAnimationClock(int frameCount, int frameDuration) {
        this.animationClock = new AnimationClock(frameCount, frameDuration, true);
    }

    public UnitAnimation getAnimation() {
        return this.unitAnimation;
    }



}
