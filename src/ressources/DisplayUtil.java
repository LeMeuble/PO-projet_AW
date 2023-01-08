package ressources;

import librairies.StdDraw;

import java.awt.*;
import java.awt.image.BufferedImage;

import static ressources.Config.BOTTOM_MENU_MARGIN;

public class DisplayUtil {

    /**
     * Get the x and y margin offsets in pixel depending on
     * the current window size and the given width and height
     * of the grid.
     *
     * @param gridWidth  The width of the grid in case units
     * @param gridHeight The height of the grid in case units
     *
     * @return The x and y margin offsets in pixel in an array
     * First element is the x offset, second is the y offset
     * x : offset if the grid is smaller than the window width otherwise 0
     * y : offset if the grid is smaller than the window height otherwise 0
     */
    public static double[] getCenteringOffset(int gridWidth, int gridHeight) {

        double xPixelOffset = 0;
        if (gridWidth < Config.MAP_COLUMN_COUNT)
            xPixelOffset += (Config.MAP_COLUMN_COUNT - gridWidth) * (double) Config.PIXEL_PER_CASE;

        double yPixelOffset = 0;
        if (gridHeight < Config.MAP_COLUMN_COUNT)
            yPixelOffset += (Config.MAP_COLUMN_COUNT - gridHeight) * (double) Config.PIXEL_PER_CASE;

        return new double[]{xPixelOffset / 2, yPixelOffset / 2};

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
    public static void drawPictureInCase(int gridX, int gridY, int gridWidth, int gridHeight, double factorX, double factorY, String path) {

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

        drawPictureInCase(gridX, gridY, gridWidth, gridHeight, 1d, 1d, path);

    }

    /**
     * Draw the game cursor in a case
     *
     * @param gridX      The x position in the grid
     * @param gridY      The y position in the grid
     * @param gridWidth  The width of the grid
     * @param gridHeight The height of the grid
     */
    public static void drawCursor(int gridX, int gridY, int gridWidth, int gridHeight) {

        drawPictureInCase(gridX, gridY, gridWidth, gridHeight, 1, 1, PathUtil.getCursorPath());

    }


    /**
     * Get the center x of a case in pixel using the grid
     *
     * @param gridX     The X position in the grid
     * @param gridWidth The width of the grid
     *
     * @return The center X of the case in pixel on the screen
     */
    public static double getCenterX(int gridX, int gridWidth) {

        double[] offset = getCenteringOffset(gridWidth, 0);
        return gridX * Config.PIXEL_PER_CASE + (double) Config.PIXEL_PER_CASE / 2 + offset[0];

    }

    /**
     * Get the center Y of a case in pixel using the grid
     *
     * @param gridY      The y position in the grid
     * @param gridHeight The height of the grid
     *
     * @return The center Y of the case in pixel on the screen
     */
    public static double getCenterY(int gridY, int gridHeight) {

        double[] offset = getCenteringOffset(0, gridHeight);
        return gridY * Config.PIXEL_PER_CASE + (double) Config.PIXEL_PER_CASE / 2 + BOTTOM_MENU_MARGIN + offset[1];

    }

    /**
     * Draw a picture using pixel position and default size
     *
     * @param pixelX The x position in pixel
     * @param pixelY The y position in pixel
     * @param file   The path to the picture file (relative to the project)
     */
    public static void drawPicture(double pixelX, double pixelY, String file) {

        StdDraw.picture(pixelX, pixelY, file);

    }

    /**
     * Draw a picture using pixel position and pixel size
     *
     * @param pixelX       The x position in pixel
     * @param pixelY       The y position in pixel
     * @param file         The path to the picture file (relative to the project)
     * @param scaledWidth  The width of the picture in pixel
     * @param scaledHeight The height of the picture in pixel
     */
    public static void drawPicture(double pixelX, double pixelY, String file, double scaledWidth, double scaledHeight) {

        StdDraw.picture(pixelX, pixelY, file, scaledWidth, scaledHeight);

    }

    public static int getTextWidth(Font font, String text) {

        final BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2d = img.createGraphics();
        final FontMetrics fm = g2d.getFontMetrics(font);
        return fm.stringWidth(text);

    }

}
