package main.unit.type;

import main.game.Player;
import main.unit.Motorized;
import main.unit.UnitType;
import main.weapon.type.HeavyMachineGun;

public class AntiAir extends Motorized {

    public AntiAir(Player.Type owner){
        super(owner);
        this.addWeapon(new HeavyMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.ANTI_AIR;
    }

}
