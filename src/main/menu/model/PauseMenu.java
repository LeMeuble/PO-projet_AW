package main.menu.model;

import main.menu.Menu;
import main.menu.MenuModel;

public class PauseMenu extends Menu {

    public PauseMenu() {

        super(0, 0, 0, 0);

    }

    @Override
    public void render() {

    }

    public MenuModel getModel() {
        return MenuModel.PAUSE_MENU;
    }

}
