package main.unit.type;

import main.game.Player;
import main.unit.Motorized;
import main.unit.UnitAnimation;
import main.unit.UnitType;
import main.weapon.type.AirToGroundMissile;
import ressources.PathUtil;

/**
 * Classe representant un lance-missiles Sol-Air
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class SamLauncher extends Motorized {

    public SamLauncher(Player.Type owner) {

        super(owner);
        this.addWeapon(new AirToGroundMissile());

    }
    @Override
    public UnitType getType() {
        return UnitType.SAMLAUNCHER;
    }

    @Override
    public String getFile(int frame) {
        return PathUtil.getUnitPath(this.getType(), this.getOwner(), UnitAnimation.IDLE, !this.hasPlayed(), frame);
    }

}
