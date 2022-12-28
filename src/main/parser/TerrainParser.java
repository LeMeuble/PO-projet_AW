package main.parser;

import main.game.Player;
import main.terrain.Property;
import main.terrain.Terrain;
import main.terrain.type.*;

public class TerrainParser {

    private enum TerrainCharacter {

        PLAIN('P', Plain.class),
        FOREST('F', Forest.class),
        MOUNTAIN('M', Mountain.class),
        WATER('W', Water.class),
        OBSTACLE('O', Obstacle.class),
        FACTORY('f', Factory.class),
        CITY('c', City.class),
        HQ('h', HQ.class);

        private final char character;
        private final Class<? extends Terrain> terrainClass;

        TerrainCharacter(char character, Class<? extends Terrain> terrainClass) {
            this.character = character;
            this.terrainClass = terrainClass;
        }

        public static TerrainCharacter fromCharacter(char character) {

            for (TerrainCharacter type : TerrainCharacter.values()) {

                if (type.character == character) return type;

            }
            return null;

        }

        private Terrain newInstance(Player.Type p) {

            try {
                if (Property.class.isAssignableFrom(this.terrainClass)) {
                    return this.terrainClass.getConstructor(Player.Type.class).newInstance(p);
                } else {
                    return this.terrainClass.newInstance();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }

    }

    protected static Terrain parse(String t) {

        if (t == null) return null;

        String format = t.trim();
        if (format.startsWith("{") && format.endsWith("}")) {

            String[] split = format.substring(1, format.length() - 1).trim().split(";");

            if (split.length == 3) {

                final String terrainSpan = split[0].trim();
                final String variationSpan = split[1].trim();
                final String ownerSpan = split[2].trim();

                for (int i = 0; i < split.length; i++) {
                    split[i] = split[i].trim();
                }

                TerrainCharacter parsed = TerrainCharacter.fromCharacter(terrainSpan.charAt(0));

                if (parsed != null) {

                    Player.Type owner = !ownerSpan.equals(".") ? Player.Type.fromValue(Integer.parseInt(ownerSpan)) : null;
                    Terrain terrain = parsed.newInstance(owner);

                    if (terrain != null) {

                        int variation = variationSpan.contains(".") ? 0 : Integer.parseInt(variationSpan);
                        terrain.setTextureVariation(variation);

                        return terrain;

                    }

                }

            }

        }

        return null;

    }

}
