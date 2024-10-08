package fr.istic.unit.type;

import fr.istic.game.Player;
import fr.istic.unit.Motorized;
import fr.istic.unit.UnitType;
import fr.istic.weapon.type.Canon;
import fr.istic.weapon.type.LightMachineGun;

/**
 * Une classe representant un char
 *
 * @author PandaLunatique
 * @author LeMeuble
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
