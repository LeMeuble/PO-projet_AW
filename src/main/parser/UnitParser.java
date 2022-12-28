package main.parser;

import main.game.Player;
import main.unit.Unit;
import main.unit.type.*;

public class UnitParser {

    public enum UnitCharacter {

        INFANTRY('i', Infantry.class),
        BAZOOKA('z', Bazooka.class),
        BOMBER('b', Bomber.class),
        CONVOY('c', Convoy.class),
        ANTIAIR('d', AntiAir.class),
        HELICOPTER('h', Helicopter.class),
        TANK('t', Tank.class),
        ARTILLERY('a', Artillery.class);

        private final char character;
        private final Class<? extends Unit> unitClass;

        UnitCharacter(char character, Class<? extends Unit> unitClass) {
            this.character = character;
            this.unitClass = unitClass;
        }

        public static UnitCharacter fromCharacter(char character) {

            for(UnitCharacter type : UnitCharacter.values()) {

                if(type.character == character) return type;

            }
            return null;

        }

        public Unit newInstance(Player.Type p) {

            try {
                System.out.println(this.unitClass);
                return this.unitClass.getConstructor(Player.Type.class).newInstance(p);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }

    }

    protected static Unit parse(String u) {

        if (u == null) return null;

        String format = u.trim();
        if (format.startsWith("[") && format.endsWith("]")) {

            String[] split = format.substring(1, format.length() - 1).split(";");

            if (split.length == 2 && !split[0].equals(".")) {

                final String unitSpan = split[0].trim();
                final String ownerSpan = split[1].trim();

                UnitCharacter type = UnitCharacter.fromCharacter(unitSpan.charAt(0));
                System.out.println(ownerSpan);
                Player.Type owner = !ownerSpan.equals(".") ? Player.Type.fromValue(Integer.parseInt(ownerSpan)) : null;

                if (type != null && owner != null) return type.newInstance(owner);

            }

        }
        return null;

    }

}
