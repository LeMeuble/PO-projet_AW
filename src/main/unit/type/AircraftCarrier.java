package main.unit.type;

import main.game.Player;
import main.unit.*;
import main.weapon.type.HeavyMachineGun;
import ressources.PathUtil;

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

    @Override
    public boolean accept(Unit unit) {
        return unit instanceof Flying;
    }

}
