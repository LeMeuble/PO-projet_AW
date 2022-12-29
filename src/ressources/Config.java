package ressources;

public final class Config {

    /*
    Config lie au rendu graphique
    */
    public static final int PIXEL_PER_CASE = 48;
    public static final int MAP_ROW_COUNT = 16;
    public static final int MAP_COLUMN_COUNT = 16;
    public static final int BOTTOM_MENU_MARGIN = 3 * PIXEL_PER_CASE;
    public static final int HEIGHT = MAP_ROW_COUNT * PIXEL_PER_CASE + BOTTOM_MENU_MARGIN;
    public static final int WIDTH = MAP_COLUMN_COUNT * PIXEL_PER_CASE;
    public static final String TITLE = "MiniWars";
    public static final String ICON_PATH = PathUtil.getUiComponentPath("icon");

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
    public static final int UNIT_SHORT_ANIMATION_FRAME_COUNT = 2; // Nombre d'images par animation pour les unites avec une courte animation
    public static final int UNIT_LONG_ANIMATION_FRAME_COUNT = 3; // Nombre d'images par animation pour les unites avec une longue animation
    public static final int UNIT_ANIMATION_FRAME_DURATION = 175; // En millisecondes

    /*
    Config lie au menus
     */
    public static final int MENU_ACTION_MARGIN = 16;
    public static final int MENU_ACTION_WIDTH = 128;
    public static final int MENU_ACTION_TOP_HEIGHT = 23;
    public static final int MENU_ACTION_MIDDLE_HEIGHT = 40;
    public static final int MENU_ACTION_BOTTOM_HEIGHT = 26;

}
