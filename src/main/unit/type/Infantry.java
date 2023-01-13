package main.unit.type;

import main.game.Player;
import main.unit.OnFoot;
import main.unit.UnitType;
import main.weapon.type.LightMachineGun;

/**
 * Une classe representant de l'infanterie
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
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
