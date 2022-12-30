package main.menu.model;

import librairies.StdDraw;
import main.game.Player;
import main.menu.ActionMenu;
import main.menu.Menu;
import main.menu.MenuModel;
import main.unit.UnitAction;
import main.unit.UnitType;
import main.util.OptionSelector;
import ressources.Config;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class FactoryActionMenu extends ActionMenu<UnitType> {


    public FactoryActionMenu(OptionSelector<UnitType> optionSelector) {

        super(optionSelector);

    }

    @Override
    public void render() {

        super.render();
        Font font = new Font("Arial", Font.BOLD, 16);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("pictures/Minecraftia-Regular.ttf")).deriveFont(16f);
        }
        catch (Exception ignored) {}

        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        FontMetrics f = g.getFontMetrics(font);

        double x = Config.MENU_ACTION_MARGIN + Config.MENU_ACTION_TOP_HEIGHT / 1.5d;
        double y = (Config.HEIGHT - Config.MENU_ACTION_MARGIN) - Config.MENU_ACTION_TOP_HEIGHT - Config.MENU_ACTION_MIDDLE_HEIGHT / 2.0d - f.getHeight() / 2.0d;

        StdDraw.setFont(font);

        for (UnitType unit : this.getOptions()) {

            if(unit == this.getSelectedOption()) {
                StdDraw.setPenColor(Color.BLUE);
            } else {
                StdDraw.setPenColor(Color.BLACK);
            }

            StdDraw.textLeft(x, y, unit.name().toLowerCase() + " (" + unit.getPrice() + ")");

            y -= Config.MENU_ACTION_MIDDLE_HEIGHT;

        }

    }

    public MenuModel getModel() {
        return MenuModel.FACTORY_ACTION_MENU;
    }

}
