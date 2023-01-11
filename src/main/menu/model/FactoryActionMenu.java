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

        int maxWidth = 0;
        for (UnitType value : this.getValues()) {
            int textWidth = DisplayUtil.getTextWidth(Config.FONT_20, ((Text) value).getText());
            if (textWidth > maxWidth) {
                maxWidth = textWidth;
            }
        }
        int width = maxWidth + 72;

        double x = Config.MENU_ACTION_MARGIN + Config.MENU_ACTION_TOP_HEIGHT / 1.5d;
        double y = (Config.HEIGHT - Config.MENU_ACTION_MARGIN) - Config.MENU_ACTION_TOP_HEIGHT - Config.MENU_ACTION_MIDDLE_HEIGHT / 2.0d + 4;

        StdDraw.setFont(Config.FONT_20);

        Player.Type playerType = MiniWars.getInstance().getCurrentGame().getCurrentPlayer().getType();

        for (OptionSelector<UnitType>.Option option : this.getOptions()) {

            DisplayUtil.drawPicture(x + 20, y, PathUtil.getUnitIdleFacingPath(option.getValue(), playerType, UnitFacing.RIGHT, option.isAvailable(), 0), 38, 38);

            if (option.isAvailable()) {
                StdDraw.setPenColor(Color.BLACK);
            }
            else {
                StdDraw.setPenColor(Color.GRAY);
            }

            StdDraw.textLeft(x + 38, y, option.getValue().getName());
            DisplayUtil.drawPicture(x + width - 94 - 8, y + 2, PathUtil.getIconPath("coin"), 16, 16);
            StdDraw.textLeft(x + width - 90, y, String.valueOf(option.getValue().getPrice()));

            y -= Config.MENU_ACTION_MIDDLE_HEIGHT;

        }

    }

    public MenuModel getModel() {
        return MenuModel.FACTORY_ACTION_MENU;
    }

}
