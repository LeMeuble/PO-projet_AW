package main.weapon.type;

import main.unit.Unit;
import main.unit.type.Infantry;
import main.weapon.Weapon;

import java.util.HashMap;

public class LightMachineGun extends Weapon {

    int maxAmmo = 9;

    static {

        damagesMultiplier = new HashMap<Unit.UnitType, Double>();
        //damagesMultiplier.put(Infantry, 0.6); // Un enum de tous les types d'unitées ?
        damagesMultiplier.put(Unit.UnitType.Infantry, 0.6);
        damagesMultiplier.put(Unit.UnitType.Bazooka, 0.55);
        damagesMultiplier.put(Unit.UnitType.Tank, 0.15);
        damagesMultiplier.put(Unit.UnitType.DCA, 0.1);
        damagesMultiplier.put(Unit.UnitType.Helicopter, 0.3);
        damagesMultiplier.put(Unit.UnitType.Bombardier, 0.0);
        damagesMultiplier.put(Unit.UnitType.Convoy, 0.4);

    }


}
