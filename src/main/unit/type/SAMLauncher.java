package main.unit.type;

import main.game.Player;
import main.unit.Motorized;
import main.unit.UnitType;
import main.weapon.type.AirToGroundMissile;

/**
 * Classe representant un lance-missiles Sol-Air
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class SAMLauncher extends Motorized {

    public SAMLauncher(Player.Type owner) {

        super(owner);
        this.addWeapon(new AirToGroundMissile());

    }
    @Override
    public UnitType getType() {
        return UnitType.SAM_LAUNCHER;
    }

}
