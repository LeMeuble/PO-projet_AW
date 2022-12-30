package ressources;

import main.game.Player;
import main.terrain.TerrainType;
import main.unit.UnitAnimation;
import main.unit.UnitType;
import main.weather.Weather;

import java.io.File;

public class PathUtil {

    // Dossier lie au jeu
    public static final String SEP = File.separator;
    public static final String ROOT_FOLDER = ".";
    public static final String MAPS_FOLDER = ROOT_FOLDER + SEP + "maps";
    public static final String MAP_EXTENSION = ".awdcmap";

    // Dossier lie aux textures
    public static final String PICTURES_FOLDER = ROOT_FOLDER + SEP + "pictures";
    public static final String PICTURE_ARROWS_FOLDER = PICTURES_FOLDER + SEP + "arrows";
    public static final String PICTURE_BUILDINGS_FOLDER = PICTURES_FOLDER + SEP + "buildings";
    public static final String PICTURE_TERRAINS_FOLDER = PICTURES_FOLDER + SEP + "terrains";
    public static final String PICTURE_TROOPS_FOLDER = PICTURES_FOLDER + SEP + "troops";
    public static final String PICTURE_UI_FOLDER = PICTURES_FOLDER + SEP + "ui";

    // Sous-dossier lie aux textures
    public static final String PICTURE_UI_BACKGROUND_FOLDER = PICTURE_UI_FOLDER + SEP + "backgrounds";
    public static final String PICTURE_UI_OVERLAY_FOLDER = PICTURE_UI_FOLDER + SEP + "overlay";
    public static final String PICTURE_UI_GUI_FOLDER = PICTURE_UI_FOLDER + SEP + "gui";
    public static final String PICTURE_UI_HP_FOLDER = PICTURE_UI_FOLDER + SEP + "hp";

    enum UIComponent {

        BACKGROUND("backgrounds");

        private final String folder;

        UIComponent(String folder) {
            this.folder = folder;
        }

        public String getFolder() {
            return this.folder;
        }

    }


    public static String getArrowPath(Player.Type player, String from, String to) {

        String first = from.compareTo(to) < 0 ? from : to;
        String second = from.compareTo(to) < 0 ? to : from;

        return PICTURE_ARROWS_FOLDER + SEP + player.getName() + SEP + first + "_" + second + ".png";

    }

    public static String getBuildingPath(Weather weather, Player.Type player, TerrainType terrain, boolean isFoggy) {

        String playerOrFog = isFoggy ? "foggy" : player.getName();

        return PICTURE_BUILDINGS_FOLDER + SEP + weather.getName() + SEP + playerOrFog + SEP + terrain.getFileName();
    }

    public static String getTerrainPath(Weather weather, TerrainType terrain, int textureVariation, boolean isFoggy) {

        String foggy = isFoggy ? "foggy" : "normal";
        return PICTURE_TERRAINS_FOLDER + SEP + weather.getName() + SEP + foggy + SEP + terrain.getDirectoryName() + SEP + textureVariation + ".png";
    }

    public static String getAnimatedTerrainPath(Weather weather, TerrainType terrain, int frame, int textureVariation, boolean isFoggy) {

        String foggy = isFoggy ? "foggy" : "normal";
        return PICTURE_TERRAINS_FOLDER + SEP + weather.getName() + SEP + foggy + SEP + terrain.getDirectoryName() + SEP + "frame" + frame + SEP + textureVariation + ".png";
    }

    public static String getUnitPath(UnitType unit, Player.Type player, UnitAnimation unitAnimation, boolean isAvailable, int frame) {

        String pose = isAvailable ? unitAnimation.getName() : "unavailable";
        return PICTURE_TROOPS_FOLDER + SEP + player.getName() + SEP + unit.getName() + SEP + pose + SEP + frame + ".png";

    }

    public static String getBackgroundPath(Weather weather, int id) {
        return PICTURE_UI_BACKGROUND_FOLDER + SEP + weather.getName() + SEP + "background_" + id + ".png";

    }

    public static String getUiComponentPath(String name) {
        return PICTURE_UI_FOLDER + SEP + name + ".png";
    }
    public static String getUiComponentPath(UIComponent component, String name) {
        return PICTURE_UI_FOLDER + SEP + component.getFolder() + SEP + name + ".png";
    }

    public static String getCursorPath() {

        return PICTURE_UI_FOLDER + SEP + "cursor.png";

    }

    public static String getOverlayPath(Player.Type player) {

        return PICTURE_UI_OVERLAY_FOLDER + SEP + player.getName() + ".png";

    }

    public static String getGuiPath(String type) {
        return PICTURE_UI_GUI_FOLDER + SEP + type + ".png";
    }

    public static String getHealthPath(int health, boolean isAvailable) {
        String available = isAvailable ? "available" : "unavailable";
        return PICTURE_UI_HP_FOLDER + SEP + available + SEP + health + ".png";
    }

}
