package main.game;

import librairies.StdDraw;

import java.awt.Color;

/**
 * Classe representant un joueur
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 */
public class Player {

    public enum Type {

        NEUTRAL("Neutre", 0),
        RED("Rouge", 1),
        BLUE("Bleu", 2),
        YELLOW("Jaune", 3),
        GREEN("Vert", 4),
        BLACK("Noir", 5);

        private final String name;
        private final int value;

        Type(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public Color getColor() {
            return this.getColor(255);
        }

        public Color getColor(int alpha) {
            switch (this) {
                case RED:
                    return new Color(255, 0, 0, alpha);
                case BLUE:
                    return new Color(0, 0, 255, alpha);
                case YELLOW:
                    return new Color(255, 255, 0, alpha);
                case GREEN:
                    return new Color(0, 255, 0, alpha);
                case BLACK:
                    return new Color(255, 0, 255, alpha);
                default:
                    return Color.WHITE;
            }
        }

        public String getName() {
            return this.name;
        }

        public String getTextureName() {
            return this.name().toLowerCase();
        }

        public static Type fromValue(int value) {

            for (Type p : Type.values()) {

                if (p.value == value) {
                    return p;
                }

            }

            return null;

        }

    }

    private int money;
    private boolean isAlive;
    private final Type type;

    private int statUnitCount;
    private int statPropertyCount;

    public Player(Player.Type type) {

        this.money = 0;
        this.isAlive = true;
        this.type = type;

        this.statUnitCount = 0;
        this.statPropertyCount = 0;

    }

    public void setStatUnitCount(int statUnitCount) {
        this.statUnitCount = statUnitCount;
    }

    public void setStatPropertyCount(int statPropertyCount) {
        this.statPropertyCount = statPropertyCount;
    }

    public int getStatUnitCount() {
        return this.statUnitCount;
    }

    public int getStatPropertyCount() {
        return this.statPropertyCount;
    }

    public Type getType() {
        return this.type;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }


}