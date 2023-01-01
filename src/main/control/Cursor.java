package main.control;

import main.render.Renderable;
import ressources.DisplayUtil;


/**
 * Classe representant le curseur de selection
 * Le curseur est represente par deux coordonnees
 * (x et y) pouvant aller respectivement de :
 * x : 0 a mapWidth - 1
 * y : 0 a mapHeight - 1
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 *
 * @see Renderable
 */
public class Cursor implements Renderable {

    private final int maxWidth;
    private final int maxHeight;
    private int currentX;
    private int currentY;
    private boolean needsRefresh;

    /**
     * Constructeur de Curseur
     * Le curseur est initialise a la position (0, 0)
     *
     * @param maxWidth  Largeur de la carte (nombre de cases en largeur)
     * @param maxHeight Hauteur de la carte (nombre de cases en hauteur)
     */
    public Cursor(int maxWidth, int maxHeight) {

        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;

        this.currentX = 0;
        this.currentY = 0;

    }

    /**
     * Obtient la position en x du curseur
     *
     * @return La coordonnee x du curseur
     */
    public int getCurrentX() {
        return this.currentX;
    }

    /**
     * Definir la coordonnee du curseur
     *
     * @param currentX La coordonnee x
     */
    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    /**
     * Obtient la position en y du curseur
     *
     * @return La coordonnee y du curseur
     */
    public int getCurrentY() {
        return this.currentY;
    }

    /**
     * Definit la coordonnee y du curseur
     *
     * @param currentY La coordonnee y
     */
    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    /**
     * Fait bouger le curseur d'une case vers le haut
     *
     * @return true si la position du curseur a ete modifiee, false sinon
     */
    public boolean up() {

        if (this.currentY < this.maxHeight - 1) {
            this.currentY++;
            this.needsRefresh = true;
            return true;
        }
        return false;

    }

    /**
     * Fait bouger le curseur d'une case vers le bas
     *
     * @return true si la position du curseur a ete modifiee, false sinon
     */
    public boolean down() {

        if (this.currentY > 0) {
            this.currentY--;
            this.needsRefresh = true;
            return true;
        }
        return false;

    }

    /**
     * Fait bouger le curseur d'une case vers la droite
     *
     * @return true si la position du curseur a ete modifiee, false sinon
     */
    public boolean right() {

        if (this.currentX < this.maxWidth - 1) {
            this.currentX++;
            this.needsRefresh = true;
            return true;
        }
        return false;

    }

    /**
     * Fait bouger le curseur d'une case vers la gauche
     *
     * @return true si la position du curseur a ete modifiee, false sinon
     */
    public boolean left() {

        if (this.currentX > 0) {
            this.currentX--;
            this.needsRefresh = true;
            return true;
        }
        return false;

    }

    @Override
    public void render() {


    }


    /**
     * Determine si le curseur a besoin d'etre rafraichi
     * a l'ecran
     *
     * @return True si il faut rafraichir l'ecran, false sinon
     *
     * @see Renderable#needsRefresh()
     */
    @Override
    public boolean needsRefresh() {
        return this.needsRefresh;
    }

    /**
     * Stipule qu'on n'a plus besoin de rafraichir l'ecran
     *
     * @param needsRefresh true si on a besoin de rafraichir le curseur
     *                     à l'ecran, false sinon
     *
     * @see Renderable#needsRefresh(boolean)
     */
    @Override
    public void needsRefresh(boolean needsRefresh) {
        this.needsRefresh = needsRefresh;
    }


}
