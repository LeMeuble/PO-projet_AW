package fr.istic.unit.type;

import fr.istic.game.Player;
import fr.istic.unit.OnFoot;
import fr.istic.unit.UnitType;
import fr.istic.weapon.type.LightMachineGun;

/**
 * Une classe representant de l'infanterie
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Infantry extends OnFoot {

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Infantry(Player.Type owner) {
        super(owner);
        this.addWeapon(new LightMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.INFANTRY;
    }

}
