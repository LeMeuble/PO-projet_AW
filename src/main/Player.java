package main;

public enum Player {

    NEUTRAL(0),
    RED(1),
    BLUE(2);

    private int value;

    Player(int value) {

        this.value = value;

    }

    public static Player fromValue(int value) {

        for(Player p : Player.values()) {

            if(p.value == value) {
                return p;
            }

        }

        return null;

    }

}
