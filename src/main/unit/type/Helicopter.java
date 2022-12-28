package main.unit.type;

import main.game.Player;
import main.unit.Animation;
import main.unit.Flying;
import ressources.Config;
import ressources.PathUtil;

public class Helicopter extends Flying {

    public static final int MIN_REACH = 1;
    public static final int MAX_REACH = 1;

    public Helicopter(Player.Type owner){
        super(owner, 0, 0);
    }

    @Override
    public Type getType() {
        return Type.HELICOPTER;
    }

    @Override
    public int getMinReach() {
        return MIN_REACH;
    }

    @Override
    public int getMaxReach() {
        return MAX_REACH;
    }

    @Override
    public String getFile(int frame) {
        return PathUtil.getUnitPath(this.getOwner(), Type.TANK, Animation.IDLE, !this.hasPlayed(), this.getFrame());
    }

    @Override
    public void switchAnimation(Animation animation) {

        int frameCount = animation == Animation.IDLE ? Config.UNIT_LONG_ANIMATION_FRAME_COUNT : Config.UNIT_SHORT_ANIMATION_FRAME_COUNT;

        this.setAnimation(animation);
        this.newAnimationClock(frameCount, Config.UNIT_ANIMATION_FRAME_DURATION);

    }
}
