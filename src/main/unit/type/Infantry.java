package main.unit.type;

import main.game.Player;
import main.unit.OnFoot;
import main.unit.UnitAnimation;
import main.unit.UnitType;
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
    public UnitType getType() {
        return UnitType.INFANTRY;
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
        return PathUtil.getUnitPath(this.getType(), this.getOwner(), UnitAnimation.IDLE, !this.hasPlayed(), frame);
    }

}
