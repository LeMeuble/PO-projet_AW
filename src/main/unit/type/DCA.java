package main.unit.type;

import main.Player;
import main.unit.Motorized;
import main.weapon.type.HeavyMachineGun;
import ressources.Chemins;

public class DCA extends Motorized {

    public static final int MIN_REACH = 1;
    public static final int MAX_REACH = 3;


    public DCA(Player.Type owner){
        super(Type.DCA, owner);
        this.addWeapon(new HeavyMachineGun());
    }

    @Override
    public String getFile() {

        return Chemins.getCheminUnite(this.getOwner().getValue(), !this.hasPlayed(), Chemins.FICHIER_ANTIAIR);

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
