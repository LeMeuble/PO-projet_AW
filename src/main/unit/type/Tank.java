package main.unit.type;

import main.game.Player;
import main.unit.Animation;
import main.unit.Motorized;
import main.weapon.type.Canon;
import main.weapon.type.LightMachineGun;
import ressources.PathUtil;

public class Tank extends Motorized {

    public static final int MIN_REACH = 1;
    public static final int MAX_REACH = 1;

    public Tank(Player.Type owner){
        super(owner, 2, 100);
        this.addWeapon(new Canon());
        this.addWeapon(new LightMachineGun());
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
    public String getFile(int frame) {
        return PathUtil.getUnitPath(this.getOwner(), Type.TANK, Animation.IDLE, !this.hasPlayed(), frame);
    }

}
