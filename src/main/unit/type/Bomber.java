package main.unit.type;

import main.Player;
import main.unit.Flying;
import main.weapon.Weapon;
import ressources.Chemins;

public class Bomber extends Flying {

    int maxPM = 7;
    int PM;
    int unitMovementType = 3;

    int maxHealth = 10;
    int health;

    Weapon[] weapons;

    int price = 20000;
    int ammo;
    int fuel;

    boolean hasPlayed;
    // Idem que pour les dégats, on utilise un tableau ? Une liste ?
    int[] movementTable;

    public Bomber(Player.Type owner){
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

        return Chemins.getCheminUnite(this.owner.getValue(), !this.hasPlayed, Chemins.FICHIER_BOMBARDIER);

    }

}
