package main.unit.type;

import main.game.Player;
import main.unit.Flying;
import main.unit.UnitType;
import main.weapon.type.Bombs;

public class Bomber extends Flying {

    public static final int DAILY_ENERGY_CONSUMPTION = 5;

    public Bomber(Player.Type owner){
        super(owner);
        this.addWeapon(new Bombs());
    }

    @Override
    public UnitType getType() {
        return UnitType.BOMBER;
    }

    @Override
    public int getDailyEnergyConsumption() {
        return Bomber.DAILY_ENERGY_CONSUMPTION;
    }

}
