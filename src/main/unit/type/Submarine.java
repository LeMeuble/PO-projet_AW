package main.unit.type;

import main.game.Player;
import main.unit.Naval;
import main.unit.UnitType;
import main.weapon.type.Torpedo;

public class Submarine extends Naval {

    public static final int DAILY_ENERGY_CONSUMPTION = 2;
    private boolean underwater;

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Submarine(Player.Type owner) {
        super(owner);
        this.addWeapon(new Torpedo());
        this.underwater = false;
    }

    public void dive() {
        this.underwater = true;
    }

    public void surface() {
        this.underwater = false;
    }

    public boolean isUnderwater() {
        return this.underwater;
    }

    public boolean canDive() {
        return !underwater;
    }

    public boolean canSurface() {
        return underwater;
    }


    @Override
    public UnitType getType() {
        return UnitType.SUBMARINE;
    }

    @Override
    public int getDailyEnergyConsumption() {
        return Submarine.DAILY_ENERGY_CONSUMPTION;
    }

}
