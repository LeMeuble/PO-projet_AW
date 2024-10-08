package fr.istic.menu;

import fr.istic.render.Renderable;


/**
 * Classe abstraite representant un fr.istic.menu.
 * Un fr.istic.menu peut etre implemente de differentes facons : il peut s'agir aussi
 * bien d'une image a afficher, un fr.istic.menu d'option ou de selection ou encore
 * le fr.istic.menu principal du jeu. Il est caracterise par plusieurs donnees :
 * - position x, y : position du fr.istic.menu sur l'ecran en pixel
 * - largeur, hauteur : largeur et hauteur du fr.istic.menu en pixel
 * - priorite : priorite d'affichage du fr.istic.menu (couche d'affichage)
 * - visible : indique si le fr.istic.menu est visible ou non
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public abstract class Menu implements Comparable<Menu>, Renderable {

    private final int priority;
    private volatile boolean isVisible;
    private volatile boolean needsRefresh;

    /**
     * Constructeur d'un fr.istic.menu
     *
     * @param priority La priorite d'affichage du fr.istic.menu
     */
    public Menu(int priority) {

        this.priority = priority;
        this.isVisible = true;
        this.needsRefresh = true;

    }

    /**
     * Obtenir la priorite d'affichage du fr.istic.menu
     *
     * @return La priorite d'affichage du fr.istic.menu
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * Indique si le fr.istic.menu est visible ou non
     *
     * @return true si le fr.istic.menu est visible, false sinon
     */
    public synchronized boolean isVisible() {
        return this.isVisible;
    }

    /**
     * Change l'etat de visibilite du fr.istic.menu
     *
     * @param isVisible true si le fr.istic.menu est visible, false sinon
     */
    public synchronized void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
        this.needsRefresh = true;
    }

    /**
     * Indique si le fr.istic.menu a besoin d'etre rafraichi a l'ecran
     *
     * @return true si le fr.istic.menu a besoin d'etre rafraichi, false sinon
     *
     * @see Renderable
     */
    public synchronized boolean needsRefresh() {
        return this.isVisible && this.needsRefresh;
    }

    /**
     * Change l'etat de rafraichissement du fr.istic.menu
     *
     * @param needsRefresh true si le fr.istic.menu a besoin d'etre rafraichi, false sinon
     *
     * @see Renderable
     */
    public synchronized void needsRefresh(boolean needsRefresh) {
        this.needsRefresh = needsRefresh;
    }

    /**
     * Methode qui permet de comparer la priorite d'affichage de deux menus
     *
     * @param other Le fr.istic.menu a comparer
     *
     * @return 0 si les deux menus ont la meme priorite, 1 si le fr.istic.menu courant a une priorite superieure, -1 si le fr.istic.menu
     * courant a une priorite inferieure
     *
     * @see Comparable<Menu>
     */
    @Override
    public int compareTo(Menu other) {
        return Integer.compare(this.priority, other.priority);
    }


    /**
     * Methode abstraite a implementer : permet de rendre le fr.istic.menu a l'ecran.
     *
     * @see Renderable
     */
    @Override
    public abstract void render();

    /**
     * Obtenir le model du fr.istic.menu dans {@link MenuModel}
     * Implemente par les classes filles.
     *
     * @return Le model du fr.istic.menu
     *
     * @see MenuModel
     */
    public abstract MenuModel getModel();

}
