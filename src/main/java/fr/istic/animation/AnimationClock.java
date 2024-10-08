package fr.istic.animation;

/**
 * Classe representant une horloge pour la synchronisation des animations
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class AnimationClock {

    private final boolean isAlternating;
    private final int frameDuration;
    private final int frameCount;
    private int frame;
    private boolean isForward;
    private long previousFrameTime;

    /**
     * Creer une nouvelle horloge de synchronisation d'animation non alternante (une seule direction)
     *
     * @param frameCount    Nombre de frames de l'animation
     * @param frameDuration Duree d'une frame en ms
     */
    public AnimationClock(int frameCount, int frameDuration) {
        this(frameCount, frameDuration, false);
    }

    /**
     * Creer une nouvelle horloge de synchronisation d'animation
     *
     * @param frameCount    Nombre de frames de l'animation
     * @param frameDuration Duree d'une frame en ms
     * @param isAlternating Indique si l'animation doit alterner entre les deux sens
     */
    public AnimationClock(int frameCount, int frameDuration, boolean isAlternating) {
        this.frame = 0;
        this.isForward = true;
        this.previousFrameTime = 0;
        this.isAlternating = isAlternating;
        this.frameDuration = frameDuration;
        this.frameCount = frameCount;
    }

    /**
     * Obtenir la frame courante
     *
     * @return La frame courante
     */
    public int getFrame() {
        return this.frame;
    }

    /**
     * Forcer la modification de la frame courante
     *
     * @param frame Nouvelle frame courante
     *
     * @deprecated
     */
    @Deprecated
    public void setFrame(int frame) {
        this.frame = frame;
    }

    /**
     * Effectuer le passage a la frame suivante.
     * Cette methode doit etre appelee a chaque frame pour que l'horloge
     * fonctionne correctement, ou alternativement, a chaque fois que la valeur
     * de {@link #needsRefresh()} est vraie.
     *
     * @see #needsRefresh()
     */
    public void nextFrame() {

        if (this.needsRefresh()) {

            this.frame += this.isForward ? 1 : -1;

            if (this.frame >= this.frameCount) {
                this.frame = this.isAlternating ? this.frameCount - 2 : 0;
                this.isForward = !this.isAlternating;
            }
            else if (this.frame < 0) {
                this.frame = this.isAlternating ? 1 : this.frameCount - 1;
                this.isForward = true;
            }

            this.previousFrameTime = System.currentTimeMillis();

        }

    }

    /**
     * Indique si le temps de la frame courante est ecoule. Implicitement,
     * cela indique si l'horloge doit passer a la frame suivante.
     *
     * @return Vrai si le temps de la frame courante est ecoule, faux sinon
     *
     * @see #nextFrame()
     */
    public boolean needsRefresh() {

        return System.currentTimeMillis() - this.previousFrameTime >= this.frameDuration;

    }

    /**
     * Obtenir le nombre de frames de l'animation
     *
     * @return Le nombre de frames de l'animation
     */
    public int getFrameCount() {
        return this.frameCount;
    }

}
