package main.game;

import main.control.Cursor;
import main.map.Case;
import main.map.Grid;
import ressources.Config;

/**
 * Cette classe offre la possibilite de rendre la partie visible a l'ecran
 * en permettant de dessiner une grille plus grande que l'ecran et de centrer
 * la grille sur le curseur. Il est donc possible de "scroller" sur la grille
 */
public class GameView {

    private final Grid grid;
    private final Cursor cursor;
    private final int width;
    private final int height;

    private int offsetX;
    private int offsetY;

    public GameView(Grid grid, Cursor cursor, int width, int height) {

        this.grid = grid;
        this.cursor = cursor;
        this.width = width;
        this.height = height;

        this.offsetX = 0;
        this.offsetY = 0;

    }

    public Case getCase(int x, int y) {
        return this.grid.getCase(x + this.offsetX, y + this.offsetY);
    }

    public int offsetX(int x) {
        return x - this.offsetX;
    }

    public int offsetY(int y) {
        return y - this.offsetY;
    }

    public int getCursorX() {
        return this.offsetX(this.cursor.getCurrentX());
    }

    public int getCursorY() {
        return this.offsetY(this.cursor.getCurrentY());
    }

    public int getOffsetX() {
        return this.offsetX;
    }

    public int getOffsetY() {
        return this.offsetY;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    /**
     * Permet de centrer la grille sur le curseur
     */
    public boolean adjustOffset() {

        if (this.getCursorX() < 0) {
            this.offsetX += this.getCursorX();
            return true;
        } else if (this.getCursorX() >= Config.MAP_COLUMN_COUNT) {
            this.offsetX += this.getCursorX() - Config.MAP_ROW_COUNT + 1;
            return true;
        }

        if (this.getCursorY() < 0) {
            this.offsetY += this.getCursorY();
            return true;
        } else if (this.getCursorY() >= Config.MAP_ROW_COUNT) {
            this.offsetY += this.getCursorY() - Config.MAP_ROW_COUNT + 1;
            return true;
        }

        return false;

    }

    /**
     * Permet de centrer la grille sur le curseur au maximum
     * @param c La case sur laquelle centrer la grille
     */
    public void focus(Case c) {

        int x = c.getX();
        int y = c.getY();

        if (x < Config.MAP_COLUMN_COUNT / 2) {
            this.offsetX = 0;
        } else if (x > this.width - Config.MAP_COLUMN_COUNT / 2) {
            this.offsetX = this.width - Config.MAP_COLUMN_COUNT;
        } else {
            this.offsetX = x - Config.MAP_COLUMN_COUNT / 2;
        }

        if (y < Config.MAP_ROW_COUNT / 2) {
            this.offsetY = 0;
        } else if (y > this.height - Config.MAP_ROW_COUNT / 2) {
            this.offsetY = this.height - Config.MAP_ROW_COUNT;
        } else {
            this.offsetY = y - Config.MAP_ROW_COUNT / 2;
        }

    }

    public boolean isVisible(Case c) {
        return c.getX() >= this.offsetX && c.getX() < this.offsetX + Config.MAP_COLUMN_COUNT
               && c.getY() >= this.offsetY && c.getY() < this.offsetY + Config.MAP_ROW_COUNT;
    }

}
