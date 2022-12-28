package main.control;


/**
 * Classe representant le curseur de selection
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class Cursor {

    private final int maxWidth;
    private final int maxHeight;
    private int currentX;
    private int currentY;
    private boolean needsRefresh;

    /**
     * Constructeur de Curseur
     * @param maxWidth
     * @param maxHeight
     */
    public Cursor(int maxWidth, int maxHeight) {

        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;

        this.currentX = 0;
        this.currentY = 0;

    }

    /**
     * @return La coordonnee x du curseur
     */
    public int getCurrentX() {
        return this.currentX;
    }

    /**
     * Definit la coordonnee du curseur
     * @param currentX La coordonnee x
     */
    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    /**
     * @return La coordonnee y du curseur
     */
    public int getCurrentY() {
        return this.currentY;
    }

    /**
     * Definit la coordonnee y du curseur
     * @param currentY La coordonnee y
     */
    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    /**
     * Fait bouger le curseur d'une case vers le haut
     * @return true si le curseur peut se déplacer, false sinon
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
     * @return true si le curseur peut se déplacer, false sinon
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
     * @return true si le curseur peut se déplacer, false sinon
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
     * @return true si le curseur peut se déplacer, false sinon
     */
    public boolean left() {

        if (this.currentX > 0) {
            this.currentX--;
            this.needsRefresh = true;
            return true;
        }
        return false;

    }

    /**
     * @return True si il faut rafraichir l'ecran, false sinon
     */
    public boolean needsRefresh() {
        return this.needsRefresh;
    }

    /**
     * Stipule qu'on n'a plus besoin de rafraichir l'ecran
     */
    public void needsRefresh(boolean needsRefresh) {
        this.needsRefresh = needsRefresh;
    }
}
