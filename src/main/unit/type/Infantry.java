package main.unit.type;

import main.Player;
import main.unit.OnFoot;
import main.weapon.type.LightMachineGun;
import ressources.Chemins;

public class Infantry extends OnFoot {


    public Infantry(Player.Type owner){
            super(Type.INFANTRY, owner);
            this.addWeapon(new LightMachineGun());
    }


    @Override
    public String getFile() {
        return Chemins.getCheminUnite(this.getOwner().getValue(), !this.hasPlayed(), Chemins.FICHIER_INFANTERIE);
    }

}
