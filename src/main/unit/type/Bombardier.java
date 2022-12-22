package main.unit.type;

import main.Player;
import main.unit.Flying;
import main.weapon.type.Bombs;
import ressources.Chemins;

public class Bombardier extends Flying {

    public static final int MIN_REACH = 0;
    public static final int MAX_REACH = 0;

    public Bombardier(Player.Type owner){
        super(Type.BOMBARDIER, owner);
        this.addWeapon(new Bombs());
    }


    @Override
    public String getFile() {

        return Chemins.getCheminUnite(this.getOwner().getValue(), !this.hasPlayed(), Chemins.FICHIER_BOMBARDIER);

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
