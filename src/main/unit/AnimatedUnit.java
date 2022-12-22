package main.unit;

import main.Player;
import main.render.AnimationClock;

public abstract class AnimatedUnit extends Unit {

    private Animation animation;
    private AnimationClock animationClock;

    public AnimatedUnit(Player.Type owner, int frameCount, int frameDuration) {

        super(owner);
        this.animation = Animation.IDLE;
        this.animationClock = new AnimationClock(frameCount, frameDuration, true);

    }

    public int getFrame() {
        return this.animationClock.getFrame();
    }

    public void setFrame(int frame) {
        this.animationClock.setFrame(frame);
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public void switchAnimation(Animation animation) {
        this.setAnimation(animation);
        this.setFrame(0);
    }

    public void newAnimationClock(int frameCount, int frameDuration) {
        this.animationClock = new AnimationClock(frameCount, frameDuration, true);
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public boolean needsRefresh() {
        return this.animationClock.needsRefresh();
    }

    public void nextFrame() {
        this.animationClock.nextFrame();
    }

}
