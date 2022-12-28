package main.unit.type;

import main.game.Player;
import main.unit.Animation;
import main.unit.OnFoot;
import main.weapon.type.LightMachineGun;
import ressources.PathUtil;

public class Infantry extends OnFoot {

    public static final int MIN_REACH = 1;
    public static final int MAX_REACH = 1;

    public Infantry(Player.Type owner){
            super(owner, 0, 0);
        this.addWeapon(new LightMachineGun());
    }

    @Override
    public Type getType() {
        return Type.INFANTRY;
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
        return PathUtil.getUnitPath(this.getOwner(), Type.INFANTRY, Animation.IDLE, !this.hasPlayed(), frame);
    }

}
