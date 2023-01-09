package main.unit.type;

import main.game.Player;
import main.unit.OnFoot;
import main.unit.UnitType;
import main.weapon.type.Canon;
import main.weapon.type.LightMachineGun;

public class Bazooka extends OnFoot {

    public Bazooka(Player.Type owner){
        super(owner);
        this.addWeapon(new Canon());
        this.addWeapon(new LightMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.BAZOOKA;
    }

}
