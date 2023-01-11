package main.menu.model;

import main.menu.AnimatedMenu;
import main.menu.Menu;
import main.menu.MenuModel;

import java.awt.*;

public class NextTurnMenu extends Menu {

    public NextTurnMenu() {
        super(10);
    }

    @Override
    public void render() {

        Color c = new Color(0, 0, 0, 0.8f);

    }

    @Override
    public MenuModel getModel() {
        return MenuModel.NEXT_TURN_MENU;
    }

}
