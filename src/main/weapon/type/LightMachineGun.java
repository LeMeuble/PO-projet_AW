package main.weapon.type;

import main.unit.Unit;
import main.unit.type.Infantry;
import main.weapon.Weapon;

import java.util.HashMap;

public class LightMachineGun extends Weapon {

    int maxAmmo = 9;
    public static HashMap<Unit, Double> damagesMultiplier; // C'est homologué ça ?

    static {

        damagesMultiplier = new HashMap<Unit, Double>();
        //damagesMultiplier.put(Infantry, 0.6); // Un enum de tous les types d'unitées ?

    }


}
