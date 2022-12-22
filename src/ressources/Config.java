package ressources;

public final class Config {

    // Config lie a la fenetre
    public static final int PIXEL_PER_CASE = 48;
    public static final int MAP_ROW_COUNT = 16;
    public static final int MAP_COLUMN_COUNT = 16;
    public static final int BOTTOM_MENU_MARGIN = 3 * PIXEL_PER_CASE;
    public static final int WIDTH = MAP_COLUMN_COUNT * PIXEL_PER_CASE;
    public static final int HEIGHT = MAP_ROW_COUNT * PIXEL_PER_CASE + BOTTOM_MENU_MARGIN;
    public static final String TITLE = "MiniWars";
    public static final String ICON_PATH = PathUtil.getUiComponentPath("icon.png");

    // Config lie aux animations
    public static final int MAP_ANIMATION_FRAME_COUNT = 4; // Nombre d'images par animation
    public static final int MAP_ANIMATION_FRAME_DURATION = 200; // En millisecondes

    public static final int UNIT_ANIMATION_FRAME_COUNT = 4; // Nombre d'images par animation
    public static final int UNIT_ANIMATION_FRAME_DURATION = 175; // En millisecondes

    public static final int MAIN_MENU_ANIMATION_FRAME_COUNT = 2; // Nombre d'images par animation
    public static final int MAIN_MENU_ANIMATION_FRAME_DURATION = 500; // Nombre d'images par animation
    public static final int MAIN_MENU_BACKGROUND_VARIATION_COUNT = 29; // Nombre d'images possible

}
