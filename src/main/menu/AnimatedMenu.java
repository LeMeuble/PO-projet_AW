package main.menu;

public abstract class AnimatedMenu extends Menu {

    public AnimatedMenu(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public abstract void render(int frame);

}
