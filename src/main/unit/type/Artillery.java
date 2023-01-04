package main.unit.type;

import main.game.Player;
import main.unit.Motorized;
import main.unit.UnitAnimation;
import main.unit.UnitType;
import main.weapon.type.Mortar;
import ressources.PathUtil;

public class Artillery extends Motorized {

    public Artillery(Player.Type owner) {

        super(owner);
        this.addWeapon(new Mortar());

    }
    @Override
    public UnitType getType() {
        return UnitType.ARTILLERY;
    }

}
