package main.unit.type;

import main.game.Player;
import main.unit.Animation;
import main.unit.Flying;
import main.unit.UnitType;
import main.weapon.type.Bombs;
import ressources.PathUtil;

public class Bomber extends Flying {

    public static final int MIN_REACH = 1;
    public static final int MAX_REACH = 1;

    public Bomber(Player.Type owner){
        super(owner, 0, 0);
        this.addWeapon(new Bombs());
    }

    @Override
    public UnitType getType() {
        return UnitType.TANK;
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
        return PathUtil.getUnitPath(this.getOwner(), UnitType.TANK, Animation.IDLE, !this.hasPlayed(), this.getFrame());
    }

}
