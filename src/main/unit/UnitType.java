package main.unit;

import main.game.Player;
import main.menu.ActionMenu;
import main.unit.type.*;
import main.util.OptionSelector;

/**
 * Enumeration de tous les types d'unites possibles
 * Contient leur nom en String, leur prix et leurs points de mouvements
 */
public enum UnitType implements ActionMenu.Text {

    AIRCRAFT_CARRIER('f', "Porte avion", AircraftCarrier.class, 30000, 99),
    ANTI_AIR('d', "Anti Air", AntiAir.class, 8000, 60),
    ARTILLERY('a', "Artillerie", Artillery.class, 6000, 50),
    BAZOOKA('z', "Bazooka", Bazooka.class, 3000, 70),
    BOMBER('b', "Bombardier", Bomber.class, 20000, 99),
    CONVOY('c', "Convoi", Convoy.class, 5000, 99),
    CORVETTE('e', "Corvette", Corvette.class, 7500, 99),
    CRUISER('r', "Croiseur", Cruiser.class, 18000, 99),
    DREADNOUGHT('n', "Dreadnought", Dreadnought.class, 28000, 99),
    HELICOPTER('h', "Helicopt\u00e8re", Helicopter.class, 9000, 99),
    INFANTRY('i', "Infanterie", Infantry.class, 1000, 99),
    LANDING_SHIP('l', "Barge", LandingShip.class, 12000, 99),
    SAM_LAUNCHER('s', "SAM Launcher", SAMLauncher.class, 12000, 50),
    SUBMARINE('u', "Sous-marin", Submarine.class, 20000, 60),
    TANK('t', "Tank", Tank.class, 7000, 70);

    private final char character;
    private final String name;
    private final Class<? extends Unit> unitClass;
    private final int price;
    private final int energy;

    UnitType(char character, String name, Class<? extends Unit> unitClass, int price, int energy) {
        this.character = character;
        this.name = name;
        this.unitClass = unitClass;
        this.price = price;
        this.energy = energy;
    }

    public boolean instanceOf(Class<? extends Unit> unitClass) {
        return unitClass.isAssignableFrom(this.unitClass);
    }

    public static UnitType fromCharacter(char character) {

        for (UnitType type : UnitType.values()) {

            if (type.character == character) {
                return type;
            }

        }
        return null;

    }

    public static OptionSelector<UnitType> asSelector(int money, Class<? extends Unit> unitClass) {

        OptionSelector<UnitType> factorySelector = new OptionSelector<>();

        for (UnitType type : UnitType.values()) {

            if (type.instanceOf(unitClass)) {
                boolean isAvailable = type.getPrice() <= money;
                factorySelector.addOption(type, isAvailable);
            }
        }

        return factorySelector;

    }

    public int getPrice() {
        return this.price;
    }

    public int getEnergy() {
        return this.energy;
    }

    public String getTextureName() {
        return this.name().toLowerCase();
    }

    public String getName() {
        return this.name;
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

    @Override
    public String getText() {
        return this.getName() + " (" + this.getPrice() + " $)";
    }
}
