package main.menu.model;

import librairies.StdDraw;
import main.menu.FadeMenu;
import main.menu.MenuModel;
import ressources.Config;

import java.awt.*;

public class SimpleFadeInOutMenu extends FadeMenu {


    public SimpleFadeInOutMenu() {

        super(20, 20);

    }

    @Override
    public void render() {

        final Color backgroundColor = new Color(0, 0, 0, this.getAlpha());

        StdDraw.setPenColor(backgroundColor);
        StdDraw.filledRectangle(Config.WIDTH / 2d, Config.HEIGHT / 2d, Config.WIDTH / 2d, Config.HEIGHT / 2d);

    }

    @Override
    public MenuModel getModel() {
        return MenuModel.SIMPLE_FADE_IN_OUT_MENU;
    }

}
