package main.unit.type;

import main.Player;
import main.unit.Motorized;
import ressources.Chemins;

public class Convoy extends Motorized {

    public Convoy(Player.Type owner){
        super(Type.CONVOY, owner);
    }


    @Override
    public String getFile() {

        return Chemins.getCheminUnite(this.getOwner().getValue(), !this.hasPlayed(), Chemins.FICHIER_GENIE);

    }

    @Override
    public int getMinReach() {
        return 0;
    }

    @Override
    public int getMaxReach() {
        return 0;
    }


}
