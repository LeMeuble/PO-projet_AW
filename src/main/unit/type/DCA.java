package main.unit.type;

import main.Player;
import main.unit.Unit;
import main.weapon.Weapon;

public class DCA extends Unit {

    int maxPM = 6;
    int PM;
    int unitMovementType = 2;

    int maxHealth = 10;
    int health;

    Weapon[] weapons;

    int price = 6000;
    int ammo;
    int fuel;

    boolean hasPlayed;
    Player owner;
    // Idem que pour les dégats, on utilise un tableau ? Une liste ?
    int[] movementTable;

    public DCA(Player owner){
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

        return Chemins.getCheminUnite(this.owner.getValue(), !this.hasPlayed, Chemins.FICHIER_ANTIAIR);

    }

}
