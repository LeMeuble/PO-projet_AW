package main.unit.type;

import main.game.Player;
import main.unit.Motorized;
import main.unit.UnitAnimation;
import main.unit.UnitType;
import main.weapon.type.Canon;
import main.weapon.type.LightMachineGun;
import ressources.PathUtil;

public class Tank extends Motorized {

    public Tank(Player.Type owner){
        super(owner);
        this.addWeapon(new Canon());
        this.addWeapon(new LightMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.TANK;
    }

}
