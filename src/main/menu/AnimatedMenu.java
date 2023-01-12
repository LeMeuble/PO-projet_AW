package main.menu;

import main.animation.AnimationClock;
import main.render.Renderable;

/**
 * Implementation de {@link Menu} pour les menus animes.
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 * @see Menu
 * @see AnimationClock
 */
public abstract class AnimatedMenu extends Menu {

    private final AnimationClock animationClock;

    /**
     * Constructeur d'un menu anime
     *
     * @param x              La position x du centre du menu
     * @param y              La position y du centre du menu
     * @param width          La largeur du menu
     * @param height         La hauteur du menu
     * @param priority       La priorite d'affichage du menu
     * @param animationClock Horloge d'animation
     *
     * @see AnimationClock
     */
    public AnimatedMenu(int priority, AnimationClock animationClock) {
        super(priority);
        this.animationClock = animationClock;
    }

    /**
     * Indique si le menu a besoin d'etre rafraichi a l'ecran
     *
     * @return true si le menu a besoin d'etre rafraichi, false sinon
     *
     * @see Renderable
     */
    @Override
    public synchronized boolean needsRefresh() {
        return this.isVisible() && (super.needsRefresh() || this.animationClock.needsRefresh());
    }

    public int getFrame() {
        return this.animationClock.getFrame();
    }

    public void nextFrame() {
        this.animationClock.nextFrame();
    }

}
