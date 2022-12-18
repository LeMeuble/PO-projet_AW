package main;

/**
 * Classe representant un joueur
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 */
public class Player {

    public enum Type {

        NEUTRAL(0),
        RED(1),
        BLUE(2);

        private final int value;

        Type(int value) {

            this.value = value;

        }

        public int getValue() {
            return this.value;
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

    private final Type type;
    private int money;

    public Player(Player.Type type) {

        this.type = type;
        this.money = 0;

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

}