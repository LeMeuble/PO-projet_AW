package main.unit.type;

import main.game.Player;
import main.unit.Motorized;
import main.unit.UnitType;
import main.weapon.type.HeavyMachineGun;

/**
 * Une classe representant un vehicule anti-aerien
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class AntiAir extends Motorized {

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public AntiAir(Player.Type owner){
        super(owner);
        this.addWeapon(new HeavyMachineGun());
    }

    @Override
    public UnitType getType() {
        return UnitType.ANTI_AIR;
    }

}
