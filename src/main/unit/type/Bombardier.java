package main.unit.type;

import main.Player;
import main.unit.Flying;
import main.unit.Unit;
import main.weapon.Weapon;
import ressources.Chemins;

public class Bombardier extends Flying {

    public static final int MIN_REACH = 0;
    public static final int MAX_REACH = 0;

    public Bombardier(Player.Type owner){
        super(Type.BOMBARDIER, owner);
        this.addWeapon(new Bombs());
    }

    /**
     * Calcule des degats infliges par cette unite
     *
     * @return
     */
    @Override
    public double calculateDamage() {
        return 0;
    }

    /**
     * Retire un certain nombre de points de vie a cette unite
     *
     * @param amount Le nombre de points de vies a enlever
     */
    @Override
    public void receiveDamage(int amount) {

    }

    @Override
    public void inflictDamage(Unit target) {

    }

    @Override
    public String getFile() {

        return Chemins.getCheminUnite(this.owner.getValue(), !this.hasPlayed, Chemins.FICHIER_BOMBARDIER);

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
