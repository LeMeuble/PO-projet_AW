package main.menu.model;

import main.menu.Menu;

public class FactoryActionMenu extends Menu {

    public FactoryActionMenu() {

        super(0, 0, 0, 0);

    }

    @Override
    public void render() {

    }

    public Model getModel() {
        return Model.FACTORY_ACTION_MENU;
    }

}
