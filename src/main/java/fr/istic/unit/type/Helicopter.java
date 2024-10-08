package fr.istic.unit.type;

import fr.istic.game.Player;
import fr.istic.unit.FlyingTransport;
import fr.istic.unit.OnFoot;
import fr.istic.unit.Unit;
import fr.istic.unit.UnitType;
import fr.istic.weapon.type.AirToGroundMissile;
import fr.istic.weapon.type.HeavyMachineGun;

/**
 * Une classe representant un helicoptere
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Helicopter extends FlyingTransport {

    public static final int DAILY_ENERGY_CONSUMPTION = 2;
    public static final int CARRYING_CAPACITY = 1;

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Helicopter(Player.Type owner) {
        super(owner, Helicopter.CARRYING_CAPACITY);
        this.addWeapon(new AirToGroundMissile());
        this.addWeapon(new HeavyMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.HELICOPTER;
    }

    /**
     * Sert a verifier si une unite peut etre transportee par l'helicoptere
     *
     * @param unit Une unite
     *
     * @return true si l'unite est une unite a pied
     */
    @Override
    public boolean accept(Unit unit) {
        return unit instanceof OnFoot;
    }

    @Override
    public int getDailyEnergyConsumption() {
        return Helicopter.DAILY_ENERGY_CONSUMPTION;
    }

}
