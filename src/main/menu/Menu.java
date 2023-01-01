package main.menu;

import main.render.Renderable;


/**
 * Classe abstraite representant un menu.
 * Un menu peut etre implemente de differentes facons : il peut s'agir aussi
 * bien d'une image a afficher, un menu d'option ou de selection ou encore
 * le menu principal du jeu. Il est caracterise par plusieurs donnees :
 * - position x, y : position du menu sur l'ecran en pixel
 * - largeur, hauteur : largeur et hauteur du menu en pixel
 * - priorite : priorite d'affichage du menu (couche d'affichage)
 * - visible : indique si le menu est visible ou non
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 */
public abstract class Menu implements Comparable<Menu>, Renderable {


    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int priority;
    private boolean isVisible;
    private boolean needsRefresh;

    /**
     * Constructeur d'un menu
     *
     * @param x        La position x du centre du menu
     * @param y        La position y du centre du menu
     * @param width    La largeur du menu
     * @param height   La hauteur du menu
     * @param priority La priorite d'affichage du menu
     */
    public Menu(int x, int y, int width, int height, int priority) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.priority = priority;
        this.isVisible = true;
        this.needsRefresh = true;

    }

    /**
     * Obtenir la position x du menu
     *
     * @return La position x du menu
     */
    public int getX() {
        return this.x;
    }

    /**
     * Obtenir la position y du menu
     *
     * @return La position y du menu
     */
    public int getY() {
        return this.y;
    }

    /**
     * Obtenir la largeur du menu
     *
     * @return La largeur du menu
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Obtenir la hauteur du menu
     *
     * @return La hauteur du menu
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Obtenir la priorite d'affichage du menu
     *
     * @return La priorite d'affichage du menu
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * Indique si le menu est visible ou non
     *
     * @return true si le menu est visible, false sinon
     */
    public boolean isVisible() {
        return this.isVisible;
    }

    /**
     * Change l'etat de visibilite du menu
     *
     * @param isVisible true si le menu est visible, false sinon
     */
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
        this.needsRefresh = true;
    }

    /**
     * Indique si le menu a besoin d'etre rafraichi a l'ecran
     *
     * @return true si le menu a besoin d'etre rafraichi, false sinon
     *
     * @see Renderable
     */
    public boolean needsRefresh() {
        return this.isVisible && this.needsRefresh;
    }

    /**
     * Change l'etat de rafraichissement du menu
     *
     * @param needsRefresh true si le menu a besoin d'etre rafraichi, false sinon
     *
     * @see Renderable
     */
    public void needsRefresh(boolean needsRefresh) {
        this.needsRefresh = needsRefresh;
    }

    /**
     * Methode qui permet de comparer la priorite d'affichage de deux menus
     *
     * @param other Le menu a comparer
     *
     * @return 0 si les deux menus ont la meme priorite, 1 si le menu courant a une priorite superieure, -1 si le menu
     * courant a une priorite inferieure
     *
     * @see Comparable<Menu>
     */
    @Override
    public int compareTo(Menu other) {
        return Integer.compare(this.priority, other.priority);
    }


    /**
     * Methode abstraite a implementer : permet de rendre le menu a l'ecran.
     *
     * @see Renderable
     */
    @Override
    public abstract void render();

    /**
     * Obtenir le model du menu dans {@link MenuModel}
     * Implemente par les classes filles.
     *
     * @return Le model du menu
     *
     * @see MenuModel
     */
    public abstract MenuModel getModel();

}
