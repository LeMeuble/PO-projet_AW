package main.unit;

import main.game.Player;
import main.unit.type.*;
import main.util.OptionSelector;

/**
 * Enumeration de tous les types d'unites possibles
 * Contient leur nom en String, leur prix et leurs points de mouvements
 */
public enum UnitType {

    INFANTRY('i', Infantry.class, 1500),
    BAZOOKA('z', Bazooka.class, 3500),
    BOMBER('b', Bomber.class, 20000),
    CONVOY('c', Convoy.class, 5000),
    ANTIAIR('d', AntiAir.class, 6000),
    HELICOPTER('h', Helicopter.class, 12000),
    TANK('t', Tank.class, 7000),
    ARTILLERY('a', Artillery.class, 6000),
    SAMLAUNCHER('s', SAMLauncher.class, 12000),
    CRUISER('r', Cruiser.class, 18000),
    SUBMARINE('u', Submarine.class, 16000),
    LANDINGSHIP('l', LandingShip.class, 5000),
    DREADNOUGHT('n', Dreadnought.class, 25000);

    private final char character;
    private final Class<? extends Unit> unitClass;
    private final int price;

    UnitType(char character, Class<? extends Unit> unitClass, int price) {
        this.character = character;
        this.unitClass = unitClass;
        this.price = price;
    }

    public static UnitType fromCharacter(char character) {

        for (UnitType type : UnitType.values()) {

            if (type.character == character) {
                return type;
            }

        }
        return null;

    }

    public static OptionSelector<UnitType> asSelector(int money) {

        OptionSelector<UnitType> factorySelector = new OptionSelector<>();

        for (UnitType type : UnitType.values()) {
            boolean isAvailable = type.getPrice() <= money;
            factorySelector.addOption(type, isAvailable);
        }

        return factorySelector;

    }

    public int getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name().toLowerCase();
    }

    public Unit newInstance(Player.Type p) {

        try {
            return this.unitClass.getConstructor(Player.Type.class).newInstance(p);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
