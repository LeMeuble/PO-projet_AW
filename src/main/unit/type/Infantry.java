package main.unit.type;

import main.Player;
import main.unit.OnFoot;
import main.weapon.Weapon;
import ressources.Chemins;

public class Infantry extends OnFoot {

    int maxPM = 3;
    int PM;
    int unitMovementType = 1;

    int maxHealth = 10;
    int health;

    Weapon[] weapons;

    int price = 1500;
    int ammo;
    int fuel;

    boolean hasPlayed;
    // Idem que pour les dégats, on utilise un tableau ? Une liste ?
    int[] movementTable;

    public Infantry(Player.Type owner){
            super(owner);
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
    public String getFile() {
        return Chemins.getCheminUnite(this.owner.getValue(), !this.hasPlayed, Chemins.FICHIER_INFANTERIE);
    }

}
