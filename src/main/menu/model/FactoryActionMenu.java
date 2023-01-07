package main.menu.model;

import librairies.StdDraw;
import main.MiniWars;
import main.game.Player;
import main.menu.ActionMenu;
import main.menu.MenuModel;
import main.unit.UnitFacing;
import main.unit.UnitType;
import main.util.OptionSelector;
import ressources.Config;
import ressources.DisplayUtil;
import ressources.PathUtil;

import java.awt.*;

public class FactoryActionMenu extends ActionMenu<UnitType> {


    public FactoryActionMenu(OptionSelector<UnitType> optionSelector) {

        super(optionSelector, true);

    }

    @Override
    public void render() {

        super.render();
        double x = Config.MENU_ACTION_MARGIN + Config.MENU_ACTION_TOP_HEIGHT / 1.5d;
        double y = (Config.HEIGHT - Config.MENU_ACTION_MARGIN) - Config.MENU_ACTION_TOP_HEIGHT - Config.MENU_ACTION_MIDDLE_HEIGHT / 2.0d + 4;

        StdDraw.setFont(Config.FONT_20);

        Player.Type playerType = MiniWars.getInstance().getCurrentGame().getCurrentPlayer().getType();

        for (UnitType unit : this.getOptions()) {

            DisplayUtil.drawPicture(x + 20, y, PathUtil.getUnitIdleFacingPath(unit, playerType, UnitFacing.RIGHT, true, 0), 38, 38);

            if (unit == this.getSelectedOption()) {
                StdDraw.setPenColor(Color.BLUE);
            }
            else {
                StdDraw.setPenColor(Color.BLACK);
            }

            StdDraw.textLeft(x + 38, y, unit.name().toLowerCase() + " (" + unit.getPrice() + ")");

            y -= Config.MENU_ACTION_MIDDLE_HEIGHT;

        }

    }

    public MenuModel getModel() {
        return MenuModel.FACTORY_ACTION_MENU;
    }

}
