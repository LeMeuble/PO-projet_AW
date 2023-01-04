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
    public boolean needsRefresh() {
        return this.isVisible() && (super.needsRefresh() || this.animationClock.needsRefresh());
    }

    /**
     * Obtenir la frame d'animation courante
     *
     * @return La frame d'animation courante
     *
     * @see AnimationClock#getFrame()
     */
    public int getFrame() {
        return this.animationClock.getFrame();
    }

    /**
     * Effectuer le passage a la frame suivante.
     * Cette methode doit etre appelee a chaque frame pour que l'horloge
     * fonctionne correctement, ou alternativement, a chaque fois que la valeur
     * de {@link #needsRefresh()} est vraie.
     *
     * @see AnimationClock#nextFrame()
     */
    public void nextFrame() {
        this.animationClock.nextFrame();
    }

}
