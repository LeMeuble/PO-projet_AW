package main.unit.type;

import main.game.Player;
import main.unit.Motorized;
import main.unit.UnitAnimation;
import main.unit.UnitType;
import main.weapon.type.HeavyMachineGun;
import ressources.PathUtil;

public class AntiAir extends Motorized {

    private static final int DEFAULT_FUEL = 50;

    public AntiAir(Player.Type owner){
        super(owner);
        this.addWeapon(new HeavyMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.ANTIAIR;
    }

}
