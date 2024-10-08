package fr.istic.menu;

import fr.istic.animation.AnimationClock;

/**
 * Classe abstraite representant un fr.istic.menu qui disparait
 * Classe fille de AnimatedMenu
 *
 * @author PandaLunatique
 * @author LeMeuble
 * @see AnimatedMenu
 */
public abstract class FadeMenu extends AnimatedMenu {

    private static final int FADE_ALPHA_INCREMENT = 25;

    private int alpha;
    private boolean isFadeIn;
    private boolean isFinished;

    /**
     * Constructeur du fr.istic.menu
     *
     * @param priority      La priorite du fr.istic.menu, par rapport aux autres
     * @param frameDuration La duree de chaque frame du fr.istic.menu
     */
    public FadeMenu(int priority, int frameDuration) {

        super(priority, new AnimationClock(1, frameDuration));
        this.alpha = 0;
        this.isFadeIn = true;
        this.isFinished = true;

    }

    /**
     * Permet de forcer le changement de l'opacite du fr.istic.menu.
     *
     * @param alpha Nouvelle opacite.
     */
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public void fadeIn() {

        this.isFadeIn = true;
        this.isFinished = false;

        this.waitUntilFinished();

    }

    public void fadeOut() {

        this.isFadeIn = false;
        this.isFinished = false;

        this.waitUntilFinished();

    }

    public int getAlpha() {
        return this.alpha;
    }

    public boolean isFinished() {

        return this.isFinished;

    }

    public boolean isFadingIn() {

        return this.isFadeIn;

    }

    private void waitUntilFinished() {

        try {

            while (!this.isFinished) {

                Thread.sleep(50);

            }

        }
        catch (Exception ignored) {
        }

    }

    @Override
    public boolean needsRefresh() {

        return super.needsRefresh() && !this.isFinished;

    }

    /**
     * Permet de calculer l'alpha suivant selon si le fr.istic.menu apparait ou disparait.
     */
    @Override
    public void nextFrame() {

        super.nextFrame();

        if (!this.isFinished) {

            this.alpha += this.isFadeIn ? FADE_ALPHA_INCREMENT : -FADE_ALPHA_INCREMENT;

            if (this.isFadeIn && this.alpha >= 255) {
                this.alpha = 255;
                this.isFinished = true;
            }

            if (!this.isFadeIn && this.alpha <= 0) {
                this.alpha = 0;
                this.isFinished = true;
            }

        }

    }

}
