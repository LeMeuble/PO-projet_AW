package main.render;

public class AnimationClockSync {

    private int frame;
    private boolean isForward;
    private boolean isAlternating;
    private long previousFrameTime;
    private final int refreshRate; // in ms
    private final int frameCount;

    public AnimationClockSync(int frameCount, int refreshRate) {
        this(frameCount, refreshRate, false);
    }

    /**
     * Creer une nouvelle horloge de synchronisation d'animation
     * @param frameCount Nombre de frames de l'animation
     * @param frameDuration Duree d'une frame en ms
     * @param isAlternating Indique si l'animation doit alterner entre les deux sens
     */
    public AnimationClockSync(int frameCount, int frameDuration, boolean isAlternating) {
        this.frame = 0;
        this.isForward = true;
        this.isAlternating = isAlternating;
        this.previousFrameTime = System.currentTimeMillis();
        this.refreshRate = frameDuration;
        this.frameCount = frameCount;
    }

    public int getFrame() {
        return this.frame;
    }

    public void nextFrame() {

        this.frame += this.isForward ? 1 : -1;

        if (this.frame >= this.frameCount) {
            this.frame = this.isAlternating ? this.frameCount - 2 : 0;
            this.isForward = !this.isAlternating;
        } else if (this.frame < 0) {
            this.frame = this.isAlternating ? 1 : this.frameCount - 1;
            this.isForward = true;
        }

        this.previousFrameTime = System.currentTimeMillis();

    }

    public boolean needsRefresh() {

        return System.currentTimeMillis() - this.previousFrameTime >= this.refreshRate;

    }

    public int getFrameCount() {
        return this.frameCount;
    }

}
