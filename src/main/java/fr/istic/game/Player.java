package fr.istic.game;

import fr.istic.Config;

import java.awt.*;

/**
 * Classe representant un joueur
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Player {

    /**
     * Enumeration des types de joueurs possibles
     * <p>
     * Neutral sert a representer "aucun joueur", utilise pour les cases n'appartenant a personne
     */
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

        public static Type fromValue(int value) {

            for (Type p : Type.values()) {

                if (p.value == value) {
                    return p;
                }

            }

            return null;

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

    }

    private final Type type;
    private int money;
    private boolean isAlive;
    private int statUnitCount;
    private int statPropertyCount;

    /**
     * Constructeur d'un joueur
     * <p>
     * Initialise le joueur comme n'ayant pas d'argent, et etant vivant
     *
     * @param type Le type du joueur a creer
     */
    public Player(Player.Type type) {

        this.money = Config.STARTING_MONEY;
        this.isAlive = true;
        this.type = type;

        this.statUnitCount = 0;
        this.statPropertyCount = 0;

    }

    public int getStatUnitCount() {
        return this.statUnitCount;
    }

    public void setStatUnitCount(int statUnitCount) {
        this.statUnitCount = statUnitCount;
    }

    public int getStatPropertyCount() {
        return this.statPropertyCount;
    }

    public void setStatPropertyCount(int statPropertyCount) {
        this.statPropertyCount = statPropertyCount;
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