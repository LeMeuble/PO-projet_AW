package fr.istic.unit.type;

import fr.istic.game.Player;
import fr.istic.unit.Naval;
import fr.istic.unit.UnitType;
import fr.istic.weapon.type.DreadnoughtBattery;

/**
 * Une classe representant un cuirrase
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
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
