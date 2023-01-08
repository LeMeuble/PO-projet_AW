package main.menu.model;

import librairies.StdDraw;
import main.MiniWars;
import main.game.Player;
import main.menu.ActionMenu;
import main.menu.MenuModel;
import main.unit.*;
import main.util.OptionSelector;
import ressources.Config;
import ressources.DisplayUtil;
import ressources.PathUtil;

import java.awt.*;

public class UnitActionMenu extends ActionMenu<UnitAction> {

    public UnitActionMenu(OptionSelector<UnitAction> optionSelector) {

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

        for (UnitAction action : this.getAvailableValues()) {

            if (action == UnitAction.MOVE) {

                DisplayUtil.drawPicture(x + 20, y, PathUtil.getArrowPath(playerType, "left", "end"), 28, 28);

            } else if (action == UnitAction.DROP_UNIT) {

                Unit selectedUnit = MiniWars.getInstance().getCurrentGame().getSelectedCase().getUnit();
                if (selectedUnit instanceof Transport) {

                    UnitType carriedUnit = ((Transport) selectedUnit).getCarriedUnit().getType();

                    DisplayUtil.drawPicture(x + 20, y, PathUtil.getUnitIdleFacingPath(carriedUnit, playerType, UnitFacing.RIGHT, true, 0), 38, 38);

                }

            } else DisplayUtil.drawPicture(x + 16, y, PathUtil.getIconPath(action.name().toLowerCase()), 32, 32);
            StdDraw.textLeft(x + 38, y, action.getText());

            y -= Config.MENU_ACTION_MIDDLE_HEIGHT;

        }

    }

    public MenuModel getModel() {
        return MenuModel.UNIT_ACTION_MENU;
    }

}
