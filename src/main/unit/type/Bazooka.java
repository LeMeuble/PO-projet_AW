package main.unit.type;

import main.Player;
import main.unit.Animation;
import main.unit.OnFoot;
import main.weapon.Weapon;
import ressources.PathUtil;

public class Bazooka extends OnFoot {

    public static final int MIN_REACH = 1;
    public static final int MAX_REACH = 1;

    public Bazooka(Player.Type owner){
        super(owner, 0, 0);
    }

    @Override
    public Type getType() {
        return Type.TANK;
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
    public String getFile() {
        return PathUtil.getUnitPath(this.getOwner(), Type.TANK, Animation.IDLE, !this.hasPlayed(), this.getFrame());
    }

}
