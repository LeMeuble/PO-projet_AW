package main.unit.type;

import main.Player;
import main.unit.Animation;
import main.unit.Motorized;
import main.weapon.Weapon;
import ressources.PathUtil;

public class Convoy extends Motorized {

    public static final int MIN_REACH = 1;
    public static final int MAX_REACH = 1;

    public Convoy(Player.Type owner){
        super(owner, 0, 0);
    }

    /**
     * Calcule des degats infliges par cette unite
     *
     * @return
     */
    @Override
    public Type getType() {
        return Type.CONVOY;
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
