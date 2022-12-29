package main.unit;

import main.game.Player;
import main.animation.AnimationClock;

public abstract class AnimatedUnit extends Unit {

    private UnitAnimation unitAnimation;
    private AnimationClock animationClock;

    public AnimatedUnit(Player.Type owner, int frameCount, int frameDuration) {

        super(owner);
        this.unitAnimation = UnitAnimation.IDLE;
        this.animationClock = new AnimationClock(frameCount, frameDuration, true);

    }

    public int getFrame() {
        return this.animationClock.getFrame();
    }

    public void setFrame(int frame) {
        this.animationClock.setFrame(frame);
    }

    public void setAnimation(UnitAnimation unitAnimation) {
        this.unitAnimation = unitAnimation;
    }

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
