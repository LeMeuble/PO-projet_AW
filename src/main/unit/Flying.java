package main.unit;

import main.Player;

public abstract class Flying extends AnimatedUnit {

    public Flying(Player.Type owner, int frameCount, int frameDuration) {

        super(owner, frameCount, frameDuration);

    }

}
