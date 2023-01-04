package main.unit.type;

import main.game.Player;
import main.unit.Flying;
import main.unit.UnitAnimation;
import main.unit.UnitType;
import main.weapon.type.Bombs;
import ressources.PathUtil;

public class Bomber extends Flying {

    public Bomber(Player.Type owner){
        super(owner);
        this.addWeapon(new Bombs());
    }

    @Override
    public UnitType getType() {
        return UnitType.BOMBER;
    }

}
