package fr.istic.game;

import fr.istic.control.Cursor;
import fr.istic.map.Case;
import fr.istic.map.Coordinate;
import fr.istic.map.Grid;
import fr.istic.Config;

/**
 * Cette classe offre la possibilite de rendre la partie visible a l'ecran
 * en permettant de dessiner une grille plus grande que l'ecran et de centrer
 * la grille sur le curseur. Il est donc possible de "scroller" sur la grille.
 * Elle permet ainsi l'adaptation de la grille "reelle" avec la grille de la
 * fenetre de jeu.
 * <p>
 * Les methodes d'adaptations de cette classe se basent sur les configurations
 * de la classe {@link Config} qui determinent la taille de la grille affichee
 * sur l'ecran.
 * - Config.MAP_ROW_COUNT
 * - Config.MAP_COLUMN_COUNT
 *
 * @author PandaLunatique
 * @author LeMeuble
 * @see Config
 */
public class GameView {

    private final Grid grid;
    private final Cursor cursor;
    private final int width;
    private final int height;

    private int offsetX;
    private int offsetY;

    /**
     * Constructeur de GameView
     *
     * @param grid   La grille reelle de jeu
     * @param cursor Le curseur de selection reel
     * @param width  La largeur de la grille reelle
     * @param height La hauteur de la grille reelle
     */
    public GameView(Grid grid, Cursor cursor, int width, int height) {

        this.grid = grid;
        this.cursor = cursor;
        this.width = width;
        this.height = height;

        this.offsetX = 0;
        this.offsetY = 0;

    }

    /**
     * Permet de recuperer la case reelle a partir des coordonnees sur case
     * de la grille affichee.
     *
     * @param x La coordonnee x de la case sur la grille affichee
     * @param y La coordonnee y de la case sur la grille affichee
     *
     * @return La case reelle correspondante
     *
     * @see Case
     */
    public Case getCase(int x, int y) {
        return this.grid.getCase(new Coordinate(x + this.offsetX, y + this.offsetY));
    }

    /**
     * Obtenir la valeur x sur l'ecran a partir de la coordonnee x reelle.
     *
     * @param x La coordonnee x reelle (0 <= x < width)
     *
     * @return La coordonnee x sur l'ecran (0 <= x <= Config.MAP_COLUMN_COUNT)
     *
     * @see Config
     */
    public int offsetX(int x) {
        return x - this.offsetX;
    }

    /**
     * Obtenir la valeur y sur l'ecran a partir de la coordonnee y reelle.
     *
     * @param y La coordonnee y reelle (0 <= y < height)
     *
     * @return La coordonnee y sur l'ecran (0 <= y <= Config.MAP_ROW_COUNT)
     *
     * @see Config
     */
    public int offsetY(int y) {
        return y - this.offsetY;
    }

    /**
     * Obtenir la coordonnee x du curseur sur l'ecran.
     *
     * @return La coordonnee x du curseur sur l'ecran.
     */
    public int getCursorX() {
        return this.offsetX(this.cursor.getCoordinate().getX());
    }

    /**
     * Obtenir la coordonnee y du curseur sur l'ecran.
     *
     * @return La coordonnee y du curseur sur l'ecran.
     */
    public int getCursorY() {
        return this.offsetY(this.cursor.getCoordinate().getY());
    }

    /**
     * Connaitre le decalage en x de la grille reelle par rapport a la grille affichee.
     *
     * @return Le decalage en x (0 <= x <= width - Config.MAP_COLUMN_COUNT)
     * si width > Config.MAP_COLUMN_COUNT et 0 sinon.
     *
     * @see Config
     */
    public int getOffsetX() {
        return this.offsetX;
    }

    /**
     * Definir un nouveau decalage x arbitraire.
     *
     * @param offsetX Le nouveau decalage x (0 <= x <= width - Config.MAP_COLUMN_COUNT)
     */
    public void setOffsetX(int offsetX) {
        this.offsetX = Math.max(0, Math.min(offsetX, this.width - Config.MAP_COLUMN_COUNT));
    }

    public boolean canMoveLeft() {
        return this.offsetX > 0;
    }

    public boolean canMoveRight() {
        return this.offsetX < this.width - Config.MAP_COLUMN_COUNT;
    }

    public boolean canMoveUp() {
        return this.offsetY < this.height - Config.MAP_ROW_COUNT;
    }

    public boolean canMoveDown() {
        return this.offsetY > 0;
    }

    /**
     * Connaitre le decalage en y de la grille reelle par rapport a la grille affichee.
     *
     * @return Le decalage en y (0 <= y <= height - Config.MAP_ROW_COUNT)
     * si height > Config.MAP_ROW_COUNT et 0 sinon.
     *
     * @see Config
     */
    public int getOffsetY() {
        return this.offsetY;
    }

    /**
     * Definir un nouveau decalage y arbitraire.
     *
     * @param offsetY Le nouveau decalage x (0 <= x <= width - Config.MAP_COLUMN_COUNT)
     */
    public void setOffsetY(int offsetY) {
        this.offsetY = Math.max(0, Math.min(offsetY, this.height - Config.MAP_ROW_COUNT));
    }

    /**
     * Determiner si une case est visible a l'ecran
     *
     * @param c La case a tester
     *
     * @return true si la case est visible a l'ecran, false sinon
     */
    public boolean isVisible(Case c) {
        final Coordinate coordinate = c.getCoordinate();
        return coordinate.getX() >= this.offsetX && coordinate.getX() < this.offsetX + Config.MAP_COLUMN_COUNT && coordinate.getY() >= this.offsetY && coordinate.getY() < this.offsetY + Config.MAP_ROW_COUNT;
    }

    public boolean adjustOffset() {

        if (this.getCursorX() < 0) {
            this.offsetX += this.getCursorX();
            return true;
        }
        else if (this.getCursorX() >= Config.MAP_COLUMN_COUNT) {
            this.offsetX += this.getCursorX() - Config.MAP_COLUMN_COUNT + 1;
            return true;
        }

        if (this.getCursorY() < 0) {
            this.offsetY += this.getCursorY();
            return true;
        }
        else if (this.getCursorY() >= Config.MAP_ROW_COUNT) {
            this.offsetY += this.getCursorY() - Config.MAP_ROW_COUNT + 1;
            return true;
        }

        return false;

    }

    /**
     * Permet de centrer la grille sur le curseur au maximum
     * Cette methode permet de centre l'affichage (en ajustant le decalage)
     * sur une case en particulier.
     * Pour un decalage minimal voir {@link #adjustOffset()}
     *
     * @param c La case sur laquelle centrer la grille
     */
    public void focus(Case c) {

        int x = c.getCoordinate().getX();
        int y = c.getCoordinate().getY();

        int maxOffsetX = Math.max(0, this.width - Config.MAP_COLUMN_COUNT);
        int maxOffsetY = Math.max(0, this.height - Config.MAP_ROW_COUNT);

        this.offsetX = Math.min(maxOffsetX, Math.max(0, x - Config.MAP_COLUMN_COUNT / 2));
        this.offsetY = Math.min(maxOffsetY, Math.max(0, y - Config.MAP_ROW_COUNT / 2));

    }

}
