package ressources;

import main.Player;
import main.terrain.Terrain;
import main.unit.Animation;
import main.unit.Unit;
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
    public static final String PICTURE_UI_BACKGROUND_FOLDER = PICTURE_UI_FOLDER + SEP + "backgrounds";



    public static String getArrowPath(Player.Type player, String from, String to) {

        String first = from.compareTo(to) < 0 ? from : to;
        String second = from.compareTo(to) < 0 ? to : from;

        return PICTURE_ARROWS_FOLDER + SEP + player.getName() + SEP + first + "_" + second + ".png";

    }

    public static String getBuildingPath(Weather weather, Player.Type player, Terrain.Type terrain, boolean isFoggy) {

        String playerOrFog = isFoggy ? "foggy" : player.getName();

        return PICTURE_BUILDINGS_FOLDER + SEP + weather.getName() + SEP + playerOrFog + SEP + terrain.getFileName();
    }

    public static String getTerrainPath(Weather weather, Terrain.Type terrain, int textureVariation, boolean isFoggy) {

        String foggy = isFoggy ? "foggy" : "normal";
        return PICTURE_TERRAINS_FOLDER + SEP + weather.getName() + SEP + foggy + SEP + terrain.getFileName() + SEP + textureVariation + ".png";
    }

    public static String getAnimatedTerrainPath(Weather weather, Terrain.Type terrain, int frame, int textureVariation, boolean isFoggy) {

        String foggy = isFoggy ? "foggy" : "normal";
        return PICTURE_TERRAINS_FOLDER + SEP + weather.getName() + SEP + foggy + SEP + terrain.getFileName() + SEP + "frame" + frame + SEP + textureVariation + ".png";
    }

    public static String getBackgroundPath(Weather weather, int id) {
        return PICTURE_UI_BACKGROUND_FOLDER + SEP + weather.getName() + SEP + "background_" + id + ".png";

    }

    public static String getUiComponentPath(String name) {
        return PICTURE_UI_FOLDER + SEP + name + ".png";
    }

    public static String getUnitPath(Player.Type player, Unit.Type unit, Animation animation, boolean isAvailable, int frame) {

        String pose = isAvailable ? animation.getName() : "unavailable";
        return PICTURE_TROOPS_FOLDER + SEP + player.getName() + SEP + unit.getName() + SEP + pose + SEP + frame + ".png";

    }

    public static String getCursorPath() {

        return PICTURE_UI_FOLDER + SEP + "cursor.png";

    }
}
