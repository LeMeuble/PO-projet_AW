package main.unit.type;

import main.game.Player;
import main.unit.Naval;
import main.unit.UnitType;
import main.weapon.type.Canon;
import main.weapon.type.HeavyMachineGun;

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
