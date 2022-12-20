package main.render;

public abstract class StaticAnimatedObject {

    private int frame;
    private final int frameCount;

    public StaticAnimatedObject(int frameCount) {
        this.frame = 0;
        this.frameCount = frameCount;
    }

    public int getFrame() {
        return this.frame;
    }

    public void nextFrame() {
        this.frame = (this.frame + 1) % this.frameCount;
    }

    public int getFrameCount() {
        return this.frameCount;
    }


}
