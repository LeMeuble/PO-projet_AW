package ressources;

import librairies.StdDraw;

import java.awt.*;

import static ressources.Config.BOTTOM_MENU_MARGIN;

public class DisplayUtil {


    private static double[] getCenteringOffset(int gridWidth, int gridHeight) {

        double xOffset = 0;
        if (gridWidth < Config.MAP_COLUMN_COUNT)
            xOffset += (Config.MAP_COLUMN_COUNT - gridWidth) * (double) Config.PIXEL_PER_CASE;

        double yOffset = 0;
        if (gridHeight < Config.MAP_COLUMN_COUNT)
            yOffset += (Config.MAP_COLUMN_COUNT - gridHeight) * (double) Config.PIXEL_PER_CASE;

        return new double[]{xOffset / 2, yOffset / 2};

    }

    /**
     * Draw a picture in case
     *
     * @param gridX      The x position in the grid
     * @param gridY      The y position in the grid
     * @param gridWidth  The width of the grid
     * @param gridHeight The height of the grid
     * @param factorX    The x factor of the picture (imageX = factorX * pixelPerCase)
     *                   or number of case in the x direction
     * @param factorY    The y factor of the picture (imageY = factorY * pixelPerCase)
     *                   or number of case in the y direction
     * @param path       The path to the picture file (relative to the project)
     */
    public static void drawPictureInCase(int gridX, int gridY, int gridWidth, int gridHeight, int factorX, int factorY, String path) {

        double[] offset = getCenteringOffset(gridWidth, gridHeight);

        double centerX = gridX * Config.PIXEL_PER_CASE; // Position de la case en pixel
        centerX += (double) (Config.PIXEL_PER_CASE * factorX) / 2 + offset[0]; // Position du centre de l'image en pixel (case + milieu de la case + décalage)

        double centerY = gridY * Config.PIXEL_PER_CASE;
        centerY += (double) (Config.PIXEL_PER_CASE * factorY) / 2 + BOTTOM_MENU_MARGIN + offset[1]; // Position de la case en pixel

        StdDraw.picture(centerX, centerY, path, Config.PIXEL_PER_CASE * factorX, Config.PIXEL_PER_CASE * factorY); // Position du centre de l'image en pixel (case + milieu de la case + décalage + marge du bas)

    }

    /**
     * Draw a picture in case
     *
     * @param gridX      The x position in the grid
     * @param gridY      The y position in the grid
     * @param gridWidth  The width of the grid
     * @param gridHeight The height of the grid
     * @param path       The path to the picture file (relative to the project)
     */
    public static void drawPictureInCase(int gridX, int gridY, int gridWidth, int gridHeight, String path) {

        drawPictureInCase(gridX, gridY, gridWidth, gridHeight, 1, 1, path);

    }

    public static void drawCursor(int gridX, int gridY, int gridWidth, int gridHeight) {

        drawPictureInCase(gridX, gridY, gridWidth, gridHeight, 1, 1, PathUtil.getCursorPath());

    }

    public static void drawDebugInCase(double x, double y, Color color) {

        double centerX = x * Config.PIXEL_PER_CASE + (double) Config.PIXEL_PER_CASE / 2;
        double centerY = y * Config.PIXEL_PER_CASE + (double) Config.PIXEL_PER_CASE / 2 + BOTTOM_MENU_MARGIN;

        StdDraw.setPenColor(color);
        StdDraw.rectangle(centerX, centerY, 4, 4);

    }

    public static void drawPicture(double x, double y, String file) {

        StdDraw.picture(x, y, file);

    }

    public static void drawPicture(double x, double y, String file, int scaledWidth, int scaledHeight) {

        StdDraw.picture(x, y, file, scaledWidth, scaledHeight);

    }

}
