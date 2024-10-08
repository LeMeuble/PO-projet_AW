package fr.istic;

import java.awt.*;
import java.io.File;

public final class Config {

    static {

        Font font1 = new Font("Arial", Font.BOLD, 30);
        Font font2 = new Font("Arial", Font.BOLD, 25);
        Font font3 = new Font("Arial", Font.BOLD, 18);
        Font font4 = new Font("Arial", Font.BOLD, 16);
        Font title = new Font("Arial", Font.BOLD, 64);
        try {
            font1 = Font.createFont(Font.TRUETYPE_FONT, new File(PathUtil.getUiComponentPath("fixedsys.ttf")))
                    .deriveFont(32f);
            font2 = Font.createFont(Font.TRUETYPE_FONT, new File(PathUtil.getUiComponentPath("fixedsys.ttf")))
                    .deriveFont(26f);
            font3 = Font.createFont(Font.TRUETYPE_FONT, new File(PathUtil.getUiComponentPath("fixedsys.ttf")))
                    .deriveFont(20f);
            font4 = Font.createFont(Font.TRUETYPE_FONT, new File(PathUtil.getUiComponentPath("fixedsys.ttf")))
                    .deriveFont(18f);
            title = Font.createFont(Font.TRUETYPE_FONT, new File(PathUtil.getUiComponentPath("fixedsys.ttf")))
                    .deriveFont(64f);
        }
        catch (Exception ignored) {
        }

        FONT_64 = title;
        FONT_32 = font1;
        FONT_26 = font2;
        FONT_20 = font3;
        FONT_18 = font4;

    }

    /*
    Config lie au rendu graphique
    */
    public static final int MAP_ROW_COUNT = 16;
    public static final int MAP_COLUMN_COUNT = 16;
    public static final double PIXEL_PER_CASE = 48d;
    public static final double BOTTOM_MENU_MARGIN = 3 * PIXEL_PER_CASE;
    public static final double WIDTH = MAP_COLUMN_COUNT * PIXEL_PER_CASE;
    public static final double HEIGHT = MAP_ROW_COUNT * PIXEL_PER_CASE + BOTTOM_MENU_MARGIN;
    public static final String TITLE = "MiniWars";
    public static final String ICON_PATH = PathUtil.getUiComponentPath("icon.png");

    public static final Font FONT_64;
    public static final Font FONT_32;
    public static final Font FONT_26;
    public static final Font FONT_20;
    public static final Font FONT_18;

    /*
    Config lie aux animations
    */
    // Animation : Menu principal
    public static final int MAIN_MENU_ANIMATION_FRAME_COUNT = 2; // Nombre d'images par animation
    public static final int MAIN_MENU_ANIMATION_FRAME_DURATION = 500; // Nombre d'images par animation
    public static final int MAIN_MENU_BACKGROUND_VARIATION_COUNT = 25; // Nombre d'images possible

    // Animation : Rendu de la carte
    public static final int MAP_ANIMATION_FRAME_COUNT = 4; // Nombre d'images par animation
    public static final int MAP_ANIMATION_FRAME_DURATION = 200; // En millisecondes

    // Animation : Rendu des unites
    public static final int UNIT_ANIMATION_FRAME_COUNT = 3; // Nombre d'images par animation pour les unites avec une longue animation
    public static final int UNIT_ANIMATION_FRAME_DURATION = 175; // En millisecondes
    public static final int UNIT_MOVING_FRAME_DURATION = 50; // En millisecondes

    /*
    Config lie au menus
     */
    public static final int MENU_ACTION_MARGIN = 32;
    public static final int MENU_ACTION_WIDTH = 180;
    public static final int MENU_ACTION_TOP_HEIGHT = 24;
    public static final int MENU_ACTION_MIDDLE_HEIGHT = 44;
    public static final int MENU_ACTION_BOTTOM_HEIGHT = 24;

    /**
     * Config lie au jeu
     */
    public static final float UNIT_MAX_HEALTH = 10f;
    public static final int UNIT_MAX_HEALTH_RECOVERY = 2; // pv par tour
    public static final double UNIT_LOW_AMMO_THRESHOLD = 1 / 2d;
    public static final double UNIT_LOW_FUEL_THRESHOLD = 1 / 2d;
    public static final int PROPERTY_DEFAULT_DEFENSE = 20;
    public static final int PROPERTY_DEFAULT_RECOVERY = 5;

    /*
    Config lie a la meteo
     */
    public static final double CHANGING_WEATHER = 0.1d;
    public static final double CHANGING_WEATHER_EXTRA_PER_PLAYER = 0.1d;


    public static final int STARTING_MONEY = 5000;


}
