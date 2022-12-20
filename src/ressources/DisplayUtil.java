package ressources;

import librairies.StdDraw;
import main.menu.Menu;

import static ressources.Config.BOTTOM_MENU_MARGIN;

public class DisplayUtil {


    public static void drawTerrainInCase(double x, double y, String file) {

        double centerX = x * Config.PIXEL_PER_CASE + (double) Config.PIXEL_PER_CASE / 2;
        double centerY = y * Config.PIXEL_PER_CASE + (double) Config.PIXEL_PER_CASE / 2 + BOTTOM_MENU_MARGIN;

        StdDraw.picture(centerX, centerY, file, Config.PIXEL_PER_CASE, Config.PIXEL_PER_CASE);

    }

    public static void drawPicture(double x, double y, String file) {

        StdDraw.picture(x, y, file);

    }
    public static void drawPicture(double x, double y, String file, int width, int height) {

        StdDraw.picture(x, y, file, width, height);

    }

//    public static void drawProperty()

}
