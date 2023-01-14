package main.unit.type;

import main.game.Player;
import main.unit.Flying;
import main.unit.UnitType;
import main.weapon.type.Bombs;

/**
 * Une classe representant un bombardier
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class Bomber extends Flying {

    public static final int DAILY_ENERGY_CONSUMPTION = 5;

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Bomber(Player.Type owner) {
        super(owner);
        this.addWeapon(new Bombs());
    }

    @Override
    public UnitType getType() {
        return UnitType.BOMBER;
    }

    @Override
    public int getDailyEnergyConsumption() {
        return Bomber.DAILY_ENERGY_CONSUMPTION;
    }

}
