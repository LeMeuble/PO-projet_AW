package main.menu.model;

import main.menu.Menu;
import main.menu.MultiOptionMenu;

public class UnitActionMenu extends MultiOptionMenu {

    public UnitActionMenu() {

        super(0, 0, 0, 0);

    }

    @Override
    public void render() {

    }

    public Model getModel() {
        return Model.UNIT_ACTION_MENU;
    }

}
