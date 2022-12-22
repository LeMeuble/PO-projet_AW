package main.unit.type;

import main.Player;
import main.unit.Motorized;
import main.weapon.type.Canon;
import main.weapon.type.LightMachineGun;
import ressources.Chemins;

public class Tank extends Motorized {

    public static final int MIN_REACH = 1;
    public static final int MAX_REACH = 1;

    public Tank(Player.Type owner){
        super(Type.TANK, owner);
        this.addWeapon(new Canon());
        this.addWeapon(new LightMachineGun());
    }


    @Override
    public String getFile() {
        return Chemins.getCheminUnite(this.getOwner().getValue(), !this.hasPlayed(), Chemins.FICHIER_TANK);
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
    public int getMinReach() {
        return MIN_REACH;
    }

    @Override
    public int getMaxReach() {
        return MAX_REACH;
    }

}
