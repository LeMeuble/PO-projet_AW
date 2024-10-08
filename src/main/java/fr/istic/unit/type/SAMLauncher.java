package fr.istic.unit.type;

import fr.istic.game.Player;
import fr.istic.unit.Motorized;
import fr.istic.unit.UnitType;
import fr.istic.weapon.type.GroundToAirMissile;

/**
 * Classe representant un lance-missiles Sol-Air
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class SAMLauncher extends Motorized {

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public SAMLauncher(Player.Type owner) {

        super(owner);
        this.addWeapon(new GroundToAirMissile());

    }

    @Override
    public UnitType getType() {
        return UnitType.SAM_LAUNCHER;
    }

}
