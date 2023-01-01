package main.unit;

import main.game.Player;
import main.unit.type.*;

/**
 * Enumeration de tous les types d'unites possibles
 * Contient leur nom en String, leur prix et leurs points de mouvements
 */
public enum UnitType {

    INFANTRY('i', Infantry.class, 1500, 3),
    BAZOOKA('z', Bazooka.class, 3500, 2),
    BOMBER('b', Bomber.class, 20000, 7),
    CONVOY('c', Convoy.class, 5000, 6),
    ANTIAIR('d', AntiAir.class, 6000, 6),
    HELICOPTER('h', Helicopter.class, 12000, 6),
    TANK('t', Tank.class, 7000, 6),
    ARTILLERY('a', Artillery.class, 6000, 5),
    SAMLAUNCHER('s', SamLaucher.class, 12000, 6);

    private final char character;
    private final Class<? extends Unit> unitClass;
    private final int price;
    private final int movementPoint;

    UnitType(char character, Class<? extends Unit> unitClass, int price, int movementPoint) {
        this.character = character;
        this.unitClass = unitClass;
        this.price = price;
        this.movementPoint = movementPoint;
    }

    public static UnitType fromCharacter(char character) {

        for (UnitType type : UnitType.values()) {

            if (type.character == character) {
                return type;
            }

        }
        return null;

    }

    public int getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name().toLowerCase();
    }

    public int getMovementPoint() {
        return this.movementPoint;
    }

    public Unit newInstance(Player.Type p) {

        try {
            return this.unitClass.getConstructor(Player.Type.class).newInstance(p);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
