package main.unit.type;

import main.game.Player;
import main.unit.UnitAnimation;
import main.unit.OnFoot;
import main.unit.UnitType;
import main.weapon.type.Canon;
import main.weapon.type.LightMachineGun;
import ressources.PathUtil;

public class Bazooka extends OnFoot {

    public static final int MIN_REACH = 1;
    public static final int MAX_REACH = 1;

    public Bazooka(Player.Type owner){
        super(owner, 0, 0);
        this.addWeapon(new Canon());
        this.addWeapon(new LightMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.BAZOOKA;
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
        return PathUtil.getUnitPath(this.getOwner(), this.getType(), UnitAnimation.IDLE, !this.hasPlayed(), frame);
    }

}
