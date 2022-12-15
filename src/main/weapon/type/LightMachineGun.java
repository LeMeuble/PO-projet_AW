package main.weapon.type;

import main.unit.Unit;
import main.weapon.Weapon;

import java.util.HashMap;

public class LightMachineGun extends Weapon {

    int maxAmmo = 9;

    static {

        HashMap<Unit.Type, Double> damagesMultiplier = new HashMap<>();
        damagesMultiplier.put(Unit.Type.Infantry, 0.6);
        damagesMultiplier.put(Unit.Type.Bazooka, 0.55);
        damagesMultiplier.put(Unit.Type.Tank, 0.15);
        damagesMultiplier.put(Unit.Type.DCA, 0.1);
        damagesMultiplier.put(Unit.Type.Helicopter, 0.3);
        damagesMultiplier.put(Unit.Type.Bombardier, 0.0);
        damagesMultiplier.put(Unit.Type.Convoy, 0.4);

    }


}
