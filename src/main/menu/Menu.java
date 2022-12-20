package main.menu;

public abstract class Menu {

    private int x;
    private int y;
    private int width;
    private int height;

    public Menu(int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract String getBackground();

}
