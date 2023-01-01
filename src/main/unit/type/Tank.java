package main.unit.type;

import main.game.Player;
import main.unit.Motorized;
import main.unit.UnitAction;
import main.unit.UnitAnimation;
import main.unit.UnitType;
import main.util.OptionSelector;
import main.weapon.type.Canon;
import main.weapon.type.LightMachineGun;
import ressources.PathUtil;

import java.util.ArrayList;
import java.util.List;

public class Tank extends Motorized {

    public static final int MIN_REACH = 1;
    public static final int MAX_REACH = 1;

    public Tank(Player.Type owner){
        super(owner, 2, 100);
        this.addWeapon(new Canon());
        this.addWeapon(new LightMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.TANK;
    }

    @Override
    public int getMinReach() {
        return MIN_REACH;
    }

    @Override
    public int getMaxReach() {
        return MAX_REACH;
    }

    @Override
    public OptionSelector<UnitAction> getAvailableActions() {

        OptionSelector<UnitAction> actions = super.getAvailableActions();

        actions.addOption(UnitAction.ATTACK);

        return actions;
    }

    @Override
    public String getFile(int frame) {
        return PathUtil.getUnitPath(this.getType(), this.getOwner(), UnitAnimation.IDLE, !this.hasPlayed(), frame);
    }

}
