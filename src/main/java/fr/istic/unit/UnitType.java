package fr.istic.unit;

import fr.istic.game.Player;
import fr.istic.menu.ActionMenu;
import fr.istic.unit.type.*;
import fr.istic.util.OptionSelector;
import fr.istic.Language;

/**
 * Enumeration de tous les types d'unites possibles
 * Contient leur nom en String, leur prix et leurs points de mouvements
 */
public enum UnitType implements ActionMenu.Text {

    INFANTRY('i', Language.UNIT_INFANTRY, Infantry.class, 1000, 99),
    BAZOOKA('z', Language.UNIT_BAZOOKA, Bazooka.class, 3000, 70),
    CONVOY('c', Language.UNIT_CONVOY, Convoy.class, 5000, 99),
    ARTILLERY('a', Language.UNIT_ARTILLERY, Artillery.class, 6000, 50),
    TANK('t', Language.UNIT_TANK, Tank.class, 7000, 70),
    CORVETTE('e', Language.UNIT_CORVETTE, Corvette.class, 7500, 99),
    ANTI_AIR('d', Language.UNIT_ANTI_AIR, AntiAir.class, 8000, 60),
    HELICOPTER('h', Language.UNIT_HELICOPTER, Helicopter.class, 9000, 99),
    LANDING_SHIP('l', Language.UNIT_LANDING_SHIP, LandingShip.class, 12000, 99),
    SAM_LAUNCHER('s', Language.UNIT_SAM_LAUNCHER, SAMLauncher.class, 12000, 50),
    CRUISER('r', Language.UNIT_CRUISER, Cruiser.class, 18000, 99),
    BOMBER('b', Language.UNIT_BOMBER, Bomber.class, 20000, 99),
    SUBMARINE('u', Language.UNIT_SUBMARINE, Submarine.class, 20000, 60),
    DREADNOUGHT('n', Language.UNIT_DREADNOUGHT, Dreadnought.class, 28000, 99),
    AIRCRAFT_CARRIER('f', Language.UNIT_AIRCRAFT_CARRIER, AircraftCarrier.class, 30000, 99);

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

    /**
     * Renvoie un type d'unite, en fonction d'un caractere
     *
     * @param character Un caractere
     *
     * @return Un type d'unite
     */
    public static UnitType fromCharacter(char character) {

        for (UnitType type : UnitType.values()) {

            if (type.character == character) {
                return type;
            }

        }
        return null;

    }

    /**
     * Renvoie un selecteur d'options, contenant des unites
     * Les unites doivent etre d'une certaine classe et avoir un prix inferieur a l'argent passe en parametre
     *
     * @param money     Le prix maximum de l'unite
     * @param unitClass La classe de l'unite (ex : Naval.class)
     *
     * @return Un selecteur d'options
     */
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

    /**
     * Renvoie uen nouvelle instance d'une unite, appartenant a un joueur proprietaire
     *
     * @param p Le joueur proprietaire
     *
     * @return Une instance d'unite
     */
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
