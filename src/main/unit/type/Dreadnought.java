package main.unit.type;

import main.game.Player;
import main.unit.Naval;
import main.unit.UnitType;
import main.weapon.type.DreadnoughtBattery;

public class Dreadnought extends Naval {

    public static final int DAILY_ENERGY_CONSUMPTION = 6;

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Dreadnought(Player.Type owner) {
        super(owner);
        this.addWeapon(new DreadnoughtBattery());
    }

    @Override
    public UnitType getType() {
        return UnitType.DREADNOUGHT;
    }

    @Override
    public int getDailyEnergyConsumption() {
        return Dreadnought.DAILY_ENERGY_CONSUMPTION;
    }

}
