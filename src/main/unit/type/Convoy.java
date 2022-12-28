package main.unit.type;

import main.game.Player;
import main.unit.Animation;
import main.unit.Motorized;
import main.unit.UnitType;
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
    public UnitType getType() {
        return UnitType.CONVOY;
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
