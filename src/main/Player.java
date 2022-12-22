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
        BLUE(2),
        YELLOW(3),
        GREEN(4),
        BLACK(5);

        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
        public String getName() {
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
    private final Type type;

    public Player(Player.Type type) {

        this.money = 0;
        this.type = type;

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