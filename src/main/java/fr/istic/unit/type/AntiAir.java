package fr.istic.unit.type;

import fr.istic.game.Player;
import fr.istic.unit.Motorized;
import fr.istic.unit.UnitType;
import fr.istic.weapon.type.HeavyMachineGun;

/**
 * Une classe representant un vehicule anti-aerien
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class AntiAir extends Motorized {

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public AntiAir(Player.Type owner) {
        super(owner);
        this.addWeapon(new HeavyMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.ANTI_AIR;
    }

}
