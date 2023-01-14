package main.unit.type;

import main.game.Player;
import main.unit.Motorized;
import main.unit.UnitType;
import main.weapon.type.Canon;
import main.weapon.type.LightMachineGun;

/**
 * Une classe representant un char
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class Tank extends Motorized {

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Tank(Player.Type owner) {
        super(owner);
        this.addWeapon(new Canon());
        this.addWeapon(new LightMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.TANK;
    }

}
