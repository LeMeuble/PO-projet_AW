package fr.istic.menu;

import fr.istic.animation.AnimationClock;
import fr.istic.render.Renderable;

/**
 * Implementation de {@link Menu} pour les menus animes.
 *
 * @author PandaLunatique
 * @author LeMeuble
 * @see Menu
 * @see AnimationClock
 */
public abstract class AnimatedMenu extends Menu {

    private final AnimationClock animationClock;

    /**
     * Constructeur d'un fr.istic.menu anime
     *
     * @param x              La position x du centre du fr.istic.menu
     * @param y              La position y du centre du fr.istic.menu
     * @param width          La largeur du fr.istic.menu
     * @param height         La hauteur du fr.istic.menu
     * @param priority       La priorite d'affichage du fr.istic.menu
     * @param animationClock Horloge d'animation
     *
     * @see AnimationClock
     */
    public AnimatedMenu(int priority, AnimationClock animationClock) {
        super(priority);
        this.animationClock = animationClock;
    }

    /**
     * Indique si le fr.istic.menu a besoin d'etre rafraichi a l'ecran
     *
     * @return true si le fr.istic.menu a besoin d'etre rafraichi, false sinon
     *
     * @see Renderable
     */
    @Override
    public synchronized boolean needsRefresh() {
        return this.isVisible() && (super.needsRefresh() || this.animationClock.needsRefresh());
    }

    /**
     * @return La frame courante de l'animation
     */
    public int getFrame() {
        return this.animationClock.getFrame();
    }

    /**
     * La frame suivante de l'animation
     */
    public void nextFrame() {
        this.animationClock.nextFrame();
    }

    public AnimationClock getAnimationClock() {
        return this.animationClock;
    }

}
