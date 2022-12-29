package main.menu;

import main.animation.AnimationClock;

public abstract class AnimatedMenu extends Menu {

    private final AnimationClock animationClock;

    public AnimatedMenu(int x, int y, int width, int height, AnimationClock animationClock) {
        super(x, y, width, height);
        this.animationClock = animationClock;
    }

    @Override
    public boolean needsRefresh() {

        return this.isVisible() && (super.needsRefresh() || this.animationClock.needsRefresh());
    }

    public int getFrame() {
        return this.animationClock.getFrame();
    }

    public void nextFrame() {
        this.animationClock.nextFrame();
    }

}
