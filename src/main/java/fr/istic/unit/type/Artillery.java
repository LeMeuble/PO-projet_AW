package fr.istic.unit.type;

import fr.istic.game.Player;
import fr.istic.unit.Motorized;
import fr.istic.unit.UnitType;
import fr.istic.weapon.type.Mortar;

/**
 * Une classe representant un camion d'artillerie
 *
 * @author PandaLunatique
 * @author LeMeuble
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
