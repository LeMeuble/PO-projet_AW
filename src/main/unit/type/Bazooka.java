package main.unit.type;

import main.Player;
import main.unit.OnFoot;
import main.unit.Unit;
import main.weapon.Weapon;
import ressources.Chemins;

public class Bazooka extends OnFoot {


    int maxPM = 2;
    int PM;

    int maxHealth = 10;
    int health;
    int unitMovementType = 1;

    Weapon[] weapons;

    int price = 3500;
    int ammo;
    int fuel;

    boolean hasPlayed;
    // Idem que pour les dégats, on utilise un tableau ? Une liste ?
    int[] movementTable;

    public Bazooka(Player.Type owner){
        super(owner);
    }

    /**
     * Calcule des degats infliges par cette unite
     * @return
     */
    @Override
    public double calculateDamage() {
        return 0;
    }

    /**
     * Retire un certain nombre de points de vie a cette unite
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

        return Chemins.getCheminUnite(this.owner.getValue(), !this.hasPlayed, Chemins.FICHIER_BAZOOKA);

    }

}
