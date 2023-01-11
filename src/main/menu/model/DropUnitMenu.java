package main.menu.model;

import librairies.StdDraw;
import main.MiniWars;
import main.game.Player;
import main.menu.ActionMenu;
import main.menu.MenuModel;
import main.unit.Unit;
import main.util.OptionSelector;
import ressources.Config;

import java.awt.*;

public class DropUnitMenu extends ActionMenu<Unit> {

    public DropUnitMenu(OptionSelector<Unit> optionSelector) {
        super(optionSelector, false);
    }

    @Override
    public void render() {

        super.render();

        double x = Config.MENU_ACTION_MARGIN + Config.MENU_ACTION_TOP_HEIGHT / 1.5d;
        double y = (Config.HEIGHT - Config.MENU_ACTION_MARGIN) - Config.MENU_ACTION_TOP_HEIGHT - Config.MENU_ACTION_MIDDLE_HEIGHT / 2.0d + 4;

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(Config.FONT_20);
        Player.Type playerType = MiniWars.getInstance().getCurrentGame().getCurrentPlayer().getType();

        for (Unit unit : this.getAvailableValues()) {

            unit.render(x + 20, y, 0, 38, 38, true);

//            DisplayUtil.drawPicture(x + 20, y, PathUtil.getUnitIdleFacingPath(unit.getType(), playerType, UnitFacing.RIGHT, true, 0), 38, 38);
            StdDraw.textLeft(x + 38, y, unit.getType().getName());

            y -= Config.MENU_ACTION_MIDDLE_HEIGHT;

        }

    }

    @Override
    public MenuModel getModel() {
        return MenuModel.DROP_UNIT_MENU;
    }


}
