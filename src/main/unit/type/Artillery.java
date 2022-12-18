package main.unit.type;

import main.Player;
import main.unit.Motorized;
import main.unit.OnFoot;
import main.unit.Unit;
import main.weapon.Weapon;
import ressources.Chemins;

public class Artillery extends Motorized {

    int maxPM = 5;
    int PM;
    int unitMovementType = 2;

    int maxHealth = 10;
    int health;

    Weapon[] weapons;

    int price = 6000;
    int ammo;
    int fuel;

    boolean hasPlayed;
    // Idem que pour les dégats, on utilise un tableau ? Une liste ?
    int[] movementTable;

    public Artillery(Player.Type owner) {

        super(owner);

    }

    /**
     * @return
     */
    @Override
    public double calculateDamage() {
        return 0;
    }

    /**
     * @param amount
     */
    @Override
    public void receiveDamage(int amount) {

    }

    @Override
    public String getFile() {

        return Chemins.getCheminUnite(this.owner.getValue(), !this.hasPlayed, Chemins.FICHIER_ARTILLERIE);

    }
}
