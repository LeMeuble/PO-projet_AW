package main.unit.type;

import main.game.Player;
import main.unit.OnFoot;
import main.unit.UnitAnimation;
import main.unit.UnitType;
import main.weapon.type.LightMachineGun;
import ressources.PathUtil;

public class Infantry extends OnFoot {

    public Infantry(Player.Type owner) {
        super(owner);
        this.addWeapon(new LightMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.INFANTRY;
    }

}
