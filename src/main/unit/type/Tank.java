package main.unit.type;

import main.Player;
import main.unit.Motorized;
import main.weapon.type.Canon;
import main.weapon.type.LightMachineGun;
import ressources.Chemins;

public class Tank extends Motorized {

    int maxPM = 6;
    int PM;
    int unitMovementType = 2;

    int maxHealth = 10;
    int health;

    Weapon[] weapons;

    int price = 7000;
    int ammo;
    int fuel;

    boolean hasPlayed;
    // Idem que pour les dégats, on utilise un tableau ? Une liste ?
    int[] movementTable;

    public Tank(Player.Type owner){
        super(Type.TANK, owner);
        this.addWeapon(new Canon());
        this.addWeapon(new LightMachineGun());
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
        return Chemins.getCheminUnite(this.owner.getValue(), !this.hasPlayed, Chemins.FICHIER_TANK);
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
