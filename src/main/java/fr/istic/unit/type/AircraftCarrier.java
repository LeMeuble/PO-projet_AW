package fr.istic.unit.type;

import fr.istic.game.Player;
import fr.istic.unit.Flying;
import fr.istic.unit.NavalTransport;
import fr.istic.unit.Unit;
import fr.istic.unit.UnitType;
import fr.istic.weapon.type.HeavyMachineGun;
import fr.istic.PathUtil;

/**
 * Classe representant un porte-avions
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class AircraftCarrier extends NavalTransport {

    private static final int DAILY_ENERGY_CONSUMPTION = 6;
    private static final int CARRYING_CAPACITY = 2;

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public AircraftCarrier(Player.Type owner) {
        super(owner, AircraftCarrier.CARRYING_CAPACITY);
        this.addWeapon(new HeavyMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.AIRCRAFT_CARRIER;
    }

    @Override
    public String getFile(int frame) {

        return PathUtil.getUnitIdleFacingPath(this.getType(), this.getOwner(), this.getFacing(), !this.hasPlayed(), frame);

    }

    @Override
    public int getDailyEnergyConsumption() {
        return AircraftCarrier.DAILY_ENERGY_CONSUMPTION;
    }

    /**
     * Sert a savoir si une unite peut etre transportee par le porte-avions
     *
     * @param unit Le type de l'unite
     *
     * @return true si l'unite est une unite volante
     */
    @Override
    public boolean accept(Unit unit) {
        return unit instanceof Flying;
    }

}
