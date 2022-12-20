package main.menu;

public abstract class AnimatedMenu extends Menu {

    private int frame;
    private long previousFrameTime;
    private final int refreshRate; // in ms
    private final int frameCount;

    public AnimatedMenu(int x, int y, int width, int height, int frameCount, int refreshRate) {
        super(x, y, width, height);
        this.frame = 0;
        this.previousFrameTime = System.currentTimeMillis();
        this.refreshRate = refreshRate;
        this.frameCount = frameCount;
    }

    public int getFrame() {
        return this.frame;
    }

    public void nextFrame() {
        if (System.currentTimeMillis() - this.previousFrameTime >= this.refreshRate) {
            this.frame = (this.frame + 1) % this.frameCount;
            this.previousFrameTime = System.currentTimeMillis();
        }
    }

    public int getFrameCount() {
        return this.frameCount;
    }

    @Override
    public boolean needsRefresh() {
        return true;
    }

}
