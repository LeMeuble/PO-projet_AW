package main.unit.type;

import main.game.Player;
import main.unit.Motorized;
import main.unit.UnitAnimation;
import main.unit.UnitType;
import ressources.PathUtil;

/**
 * Classe representant un lance-missiles Sol-Air
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class SamLaucher extends Motorized {

    public static final int MIN_REACH = 3;
    public static final int MAX_REACH = 6;

    public SamLaucher(Player.Type owner) {

        super(owner, 0, 0);

    }
    @Override
    public UnitType getType() {
        return UnitType.SAMLAUNCHER;
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
