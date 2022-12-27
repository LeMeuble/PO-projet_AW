package main.menu.model;

import main.menu.Menu;

public class PauseMenu extends Menu {

    public PauseMenu() {

        super(0, 0, 0, 0);

    }

    @Override
    public void render() {

    }

    public Model getModel() {
        return Model.PAUSE_MENU;
    }

}
