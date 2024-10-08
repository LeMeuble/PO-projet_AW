package fr.istic.menu.model;

import fr.istic.StdDraw;
import fr.istic.MiniWars;
import fr.istic.game.Player;
import fr.istic.menu.ActionMenu;
import fr.istic.menu.MenuModel;
import fr.istic.unit.Transport;
import fr.istic.unit.Unit;
import fr.istic.unit.UnitAction;
import fr.istic.util.OptionSelector;
import fr.istic.Config;
import fr.istic.DisplayUtil;
import fr.istic.PathUtil;

import java.awt.*;

/**
 * Classe representant un fr.istic.menu d'action d'unite
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class UnitActionMenu extends ActionMenu<UnitAction> {

    /**
     * Constructeur du fr.istic.menu d'action de l'unite
     *
     * @param optionSelector Les options disponibles pour cette unite
     */
    public UnitActionMenu(OptionSelector<UnitAction> optionSelector) {

        super(optionSelector, false);

    }

    /**
     * Methode gerant l'affichage du fr.istic.menu
     *
     * @see ActionMenu#render()
     */
    @Override
    public void render() {

        super.render();

        double x = Config.MENU_ACTION_MARGIN + Config.MENU_ACTION_TOP_HEIGHT / 1.5d;
        double y = (Config.HEIGHT - Config.MENU_ACTION_MARGIN) - Config.MENU_ACTION_TOP_HEIGHT - Config.MENU_ACTION_MIDDLE_HEIGHT / 2.0d + 4;

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(Config.FONT_20);
        Player.Type playerType = MiniWars.getInstance().getCurrentGame().getCurrentPlayer().getType();

        // Pour chaque action possible
        for (UnitAction action : this.getAvailableValues()) {

            // On affiche la bonne case, au bon endroit sur l'ecran
            if (action == UnitAction.MOVE) {

                DisplayUtil.drawPicture(x + 20, y, PathUtil.getArrowPath(playerType, "left", "end"), 28, 28);

            }
            else if (action == UnitAction.DROP_UNIT) {

                Unit selectedUnit = MiniWars.getInstance().getCurrentGame().getSelectedCase().getUnit();
                if (selectedUnit instanceof Transport) {

                    int carriedUnits = ((Transport) selectedUnit).getCarriedUnits().size();

                    if (carriedUnits > 1) {

                        DisplayUtil.drawPicture(x + 20, y, PathUtil.getIconPath("drop_unit"), 28, 28);

                    }
                    else {

                        Unit carriedUnit = ((Transport) selectedUnit).getCarriedUnits().get(0);
                        carriedUnit.render(x + 16, y, 0, 38, 38, true);

                    }

                }

            }
            else DisplayUtil.drawPicture(x + 16, y, PathUtil.getIconPath(action.name().toLowerCase()), 32, 32);
            StdDraw.textLeft(x + 38, y, action.getText());

            y -= Config.MENU_ACTION_MIDDLE_HEIGHT;

        }

    }

    public MenuModel getModel() {
        return MenuModel.UNIT_ACTION_MENU;
    }

}
