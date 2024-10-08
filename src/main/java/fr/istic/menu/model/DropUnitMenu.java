package fr.istic.menu.model;

import fr.istic.StdDraw;
import fr.istic.MiniWars;
import fr.istic.game.Player;
import fr.istic.menu.ActionMenu;
import fr.istic.menu.MenuModel;
import fr.istic.unit.Unit;
import fr.istic.util.OptionSelector;
import fr.istic.Config;

import java.awt.*;

/**
 * Classe representant un fr.istic.menu de depot d'unite
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class DropUnitMenu extends ActionMenu<Unit> {

    /**
     * Constructeur du fr.istic.menu de depot d'unite
     *
     * @param optionSelector Un selecteur d'options, contenant les unites qu'il est possible de deposer
     */
    public DropUnitMenu(OptionSelector<Unit> optionSelector) {
        super(optionSelector, false);
    }

    /**
     * Methode gerant l'affichage du fr.istic.menu de depot d'unite
     */
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

//            DisplayUtil.drawPicture(x + 20, y, PathUtil.getUnitIdleFacingPath(fr.istic.unit.getType(), playerType, UnitFacing.RIGHT, true, 0), 38, 38);
            StdDraw.textLeft(x + 38, y, unit.getType().getName());

            y -= Config.MENU_ACTION_MIDDLE_HEIGHT;

        }

    }

    @Override
    public MenuModel getModel() {
        return MenuModel.DROP_UNIT_MENU;
    }


}
