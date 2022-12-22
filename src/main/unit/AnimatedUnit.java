package main.unit;

import main.Player;
import main.render.AnimationClockSync;

public abstract class AnimatedUnit extends Unit {

    private AnimationClockSync animationClockSync;

    public AnimatedUnit(Player.Type owner, int frameCount, int frameDuration) {

        super(owner);
        this.animationClockSync = new AnimationClockSync(frameCount, frameDuration, true);

    }



}
