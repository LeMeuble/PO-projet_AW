package fr.istic.unit.type;

import fr.istic.game.Player;
import fr.istic.unit.Naval;
import fr.istic.unit.UnitType;
import fr.istic.weapon.type.Canon;
import fr.istic.weapon.type.HeavyMachineGun;

/**
 * Une classe representant un croiseur
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Cruiser extends Naval {

    public static final int DAILY_ENERGY_CONSUMPTION = 5;

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Cruiser(Player.Type owner) {
        super(owner);
        this.addWeapon(new Canon());
        this.addWeapon(new HeavyMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.CRUISER;
    }

    @Override
    public int getDailyEnergyConsumption() {
        return Cruiser.DAILY_ENERGY_CONSUMPTION;
    }

}
