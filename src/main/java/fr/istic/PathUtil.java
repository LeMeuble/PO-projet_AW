package fr.istic;

import fr.istic.game.Player;
import fr.istic.terrain.TerrainType;
import fr.istic.unit.UnitAnimation;
import fr.istic.unit.UnitFacing;
import fr.istic.unit.UnitType;
import fr.istic.weather.Weather;

import java.io.File;

public class PathUtil {

    // Dossier lie au jeu
    public static final String SEP = File.separator;
    public static final String ROOT_FOLDER = "."; // Peut etre remplacer par un chemin absolu en cas de probleme
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
    public static final String PICTURE_UI_DIGIT_FOLDER = PICTURE_UI_FOLDER + SEP + "digit";
    public static final String PICTURE_UI_OVERLAY_FOLDER = PICTURE_UI_FOLDER + SEP + "overlay";
    public static final String PICTURE_UI_ACTION_GUI_FOLDER = PICTURE_UI_FOLDER + SEP + "gui" + SEP + "action";
    public static final String PICTURE_UI_GLOBAL_GUI_FOLDER = PICTURE_UI_FOLDER + SEP + "gui" + SEP + "global";
    public static final String PICTURE_UI_BOTTOM_GUI_FOLDER = PICTURE_UI_FOLDER + SEP + "gui" + SEP + "bottom";
    public static final String PICTURE_UI_HP_FOLDER = PICTURE_UI_FOLDER + SEP + "hp";
    public static final String PICTURE_UI_ICON_FOLDER = PICTURE_UI_FOLDER + SEP + "icons";
    public static final String PICTURE_UI_INDICATOR_FOLDER = PICTURE_UI_FOLDER + SEP + "indicator";
    public static final String PICTURE_UI_KEYTIP_FOLDER = PICTURE_UI_FOLDER + SEP + "keytip";
    public static final String PICTURE_UI_TEAMS_FOLDER = PICTURE_UI_FOLDER + SEP + "teams";
    public static final String PICTURE_UI_WEATHER_FOLDER = PICTURE_UI_FOLDER + SEP + "fr.istic.weather";

    public static String getDigitPath(String digit) {

        return PICTURE_UI_DIGIT_FOLDER + SEP + digit + ".png";

    }

    public static String getArrowPath(Player.Type player, String from, String to) {

        String first = from.compareTo(to) < 0 ? from : to;
        String second = from.compareTo(to) < 0 ? to : from;

        return PICTURE_ARROWS_FOLDER + SEP + player.getTextureName() + SEP + first + "_" + second + ".png";

    }

    public static String getBuildingPath(Weather weather, Player.Type player, TerrainType terrain, boolean isFoggy) {

        String playerOrFog = isFoggy ? "foggy" : player.getTextureName();

        return PICTURE_BUILDINGS_FOLDER + SEP + weather.getTextureName() + SEP + playerOrFog + SEP + terrain.getFileName();
    }

    public static String getTerrainPath(Weather weather, TerrainType terrain, int textureVariation, boolean isFoggy) {

        String foggy = isFoggy ? "foggy" : "normal";
        return PICTURE_TERRAINS_FOLDER + SEP + weather.getTextureName() + SEP + foggy + SEP + terrain.getDirectoryName() + SEP + textureVariation + ".png";
    }

    public static String getAnimatedTerrainPath(Weather weather, TerrainType terrain, int frame, int textureVariation, boolean isFoggy) {

        String foggy = isFoggy ? "foggy" : "normal";
        return PICTURE_TERRAINS_FOLDER + SEP + weather.getTextureName() + SEP + foggy + SEP + terrain.getDirectoryName() + SEP + "frame" + frame + SEP + textureVariation + ".png";
    }

    public static String getUnitAnimationPath(UnitType unit, Player.Type player, UnitAnimation unitAnimation, int frame) {

        return PICTURE_TROOPS_FOLDER + SEP + player.getTextureName() + SEP + unit.getTextureName() + SEP + unitAnimation.getName() + SEP + frame + ".png";

    }

    public static String getUnitIdleFacingPath(UnitType unit, Player.Type player, UnitFacing unitFacing, boolean isAvailable, int frame) {

        String pose = isAvailable ? "idle" : "unavailable";
        String facing = unitFacing.getName();

        return PICTURE_TROOPS_FOLDER + SEP + player.getTextureName() + SEP + unit.getTextureName() + SEP + pose + facing + SEP + frame + ".png";

    }

    public static String getBackgroundPath(Weather weather, int id) {
        return PICTURE_UI_BACKGROUND_FOLDER + SEP + weather.getTextureName() + SEP + "background_" + id + ".png";
    }

    public static String getActionGuiPath(String name) {
        return PICTURE_UI_ACTION_GUI_FOLDER + SEP + name + ".png";
    }

    public static String getGlobalGuiPath(String name) {
        return PICTURE_UI_GLOBAL_GUI_FOLDER + SEP + name + ".png";
    }

    public static String getBottomGuiPath(Player.Type player) {

        return PICTURE_UI_BOTTOM_GUI_FOLDER + SEP + player.getTextureName() + ".png";

    }

    public static String getHealthPath(int health, boolean isAvailable) {

        String available = isAvailable ? "available" : "unavailable";
        return PICTURE_UI_HP_FOLDER + SEP + available + SEP + health + ".png";

    }

    public static String getTeamIcon(Player.Type player) {

        return PICTURE_UI_TEAMS_FOLDER + SEP + player.getTextureName() + ".png";

    }

    public static String getTeamWinIcon(Player.Type player) {

        return PICTURE_UI_TEAMS_FOLDER + SEP + "win" + player.getTextureName() + ".png";

    }

    public static String getWeatherOverlayPath(Weather weather, int frame) {

        return PICTURE_UI_WEATHER_FOLDER + SEP + weather.name().toLowerCase() + SEP + frame + ".png";

    }

    public static String getIconPath(String icon) {
        return PICTURE_UI_ICON_FOLDER + SEP + icon + ".png";
    }

    public static String getIndicatorPath(String indicator) {

        return PICTURE_UI_INDICATOR_FOLDER + SEP + indicator + ".png";

    }

    public static String getIndicatorPath(boolean available, String indicator) {

        return PICTURE_UI_INDICATOR_FOLDER + SEP + (available ? "available" : "unavailable") + SEP + indicator + ".png";

    }

    public static String getKeytipPath(String keytip) {

        return PICTURE_UI_KEYTIP_FOLDER + SEP + keytip + ".png";

    }

    public static String getOverlayPath(String overlay) {

        return PICTURE_UI_OVERLAY_FOLDER + SEP + overlay + ".png";

    }

    public static String getUiComponentPath(String file) {
        return PICTURE_UI_FOLDER + SEP + file;
    }

}
