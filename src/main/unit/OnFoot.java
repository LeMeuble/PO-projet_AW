package main.unit;

import main.Player;
import ressources.Config;

public abstract class OnFoot extends AnimatedUnit {

    public OnFoot(Player.Type owner) {

        super(owner, Config.UNIT_LONG_ANIMATION_FRAME_COUNT, Config.UNIT_ANIMATION_FRAME_DURATION);

    }

}
