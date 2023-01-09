package main.unit.type;

import main.game.Player;
import main.unit.Motorized;
import main.unit.UnitType;
import main.weapon.type.LightMachineGun;

public class Flare extends Motorized {

    private static final int DEFAULT_AMMO = 3;
    private int ammo = DEFAULT_AMMO;

    private static final int MIN_RANGE = 0;
    private static final int MAX_RANGE = 5;

    public Flare(Player.Type owner) {
        super(owner);
        this.addWeapon(new LightMachineGun());
    }

    @Override
    public UnitType getType() {
//        return UnitType.FLARE;
        //todo: fix
        return null;
    }

}
