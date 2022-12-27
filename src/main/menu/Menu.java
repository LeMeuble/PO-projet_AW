package main.menu;


public abstract class Menu {

    public enum Model {
        MAIN_MENU,
        MAP_SELECTION_MENU,
        UNIT_ACTION_MENU,
        FACTORY_ACTION_MENU,
        PAUSE_MENU;
    }


    private boolean isVisible;
    private boolean needsRefresh;

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Menu(int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isVisible = true;
        this.needsRefresh = true;

    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
        this.needsRefresh = true;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void needsRefresh(boolean needsRefresh) {
        this.needsRefresh = needsRefresh;
    }
    public boolean needsRefresh() {
        return this.isVisible && this.needsRefresh;

    }

    public abstract void render();
    public abstract Model getModel();

}
