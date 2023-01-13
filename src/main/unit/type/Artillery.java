package main.unit.type;

import main.game.Player;
import main.unit.Motorized;
import main.unit.UnitType;
import main.weapon.type.Mortar;

/**
 * Une classe representant un camion d'artillerie
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class Artillery extends Motorized {

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Artillery(Player.Type owner) {

        super(owner);
        this.addWeapon(new Mortar());

    }
    @Override
    public UnitType getType() {
        return UnitType.ARTILLERY;
    }

}
