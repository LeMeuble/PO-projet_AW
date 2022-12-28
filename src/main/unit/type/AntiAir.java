package main.unit.type;

import main.game.Player;
import main.unit.Animation;
import main.unit.Motorized;
import main.weapon.type.HeavyMachineGun;
import ressources.PathUtil;

public class AntiAir extends Motorized {

    public static final int MIN_REACH = 1;
    public static final int MAX_REACH = 1;

    public AntiAir(Player.Type owner){
        super(owner, 0, 0);
        this.addWeapon(new HeavyMachineGun());
    }

    @Override
    public Type getType() {
        return Type.ANTIAIR;
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
        return PathUtil.getUnitPath(this.getOwner(), this.getType(), Animation.IDLE, !this.hasPlayed(), frame);
    }
}
