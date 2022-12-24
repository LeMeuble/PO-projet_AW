package main.unit.type;

import main.Player;
import main.unit.Flying;
import main.weapon.type.HeavyMachineGun;
import main.weapon.type.Missile;
import ressources.Chemins;

public class Helicopter extends Flying {

    public static final int MIN_REACH = 1;
    public static final int MAX_REACH = 1;

    public Helicopter(Player.Type owner){
        super(Type.HELICOPTER, owner);
        this.addWeapon(new Missile());
        this.addWeapon(new HeavyMachineGun());
    }

    @Override
    public String getFile() {
        return Chemins.getCheminUnite(this.getOwner().getValue(), !this.hasPlayed(), Chemins.FICHIER_HELICOPTERE);
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
