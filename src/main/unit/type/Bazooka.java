package main.unit.type;

import main.Player;
import main.unit.OnFoot;
import main.weapon.type.Canon;
import main.weapon.type.LightMachineGun;
import ressources.Chemins;

public class Bazooka extends OnFoot {

    public Bazooka(Player.Type owner){
        super(Type.BAZOOKA, owner);
        this.addWeapon(new Canon());
        this.addWeapon(new LightMachineGun());
    }

    @Override
    public String getFile() {

        return Chemins.getCheminUnite(this.getOwner().getValue(), !this.hasPlayed(), Chemins.FICHIER_BAZOOKA);

    }

}
