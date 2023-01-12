package main.menu.model;

import librairies.StdDraw;
import main.menu.MenuModel;
import main.menu.SelectionMenu;
import main.util.OptionSelector;
import ressources.Config;
import ressources.DisplayUtil;
import ressources.PathUtil;

import java.awt.*;
import java.util.Arrays;

public class PauseMenu extends SelectionMenu<PauseMenu.Action> {

    public enum Action {
        RESUME("Continuer"),
        QUIT("Quitter la partie");

        private final String text;

        Action(String text) {
            this.text = text;
        }

    }

    public PauseMenu() {

        super(10, new OptionSelector<>(Arrays.asList(Action.values())));

    }

    @Override
    public void render() {

        StdDraw.setPenColor(new Color(0, 0, 0, 0.65f));
        StdDraw.filledRectangle(Config.WIDTH / 2, Config.HEIGHT / 2, Config.WIDTH / 2, Config.HEIGHT / 2);

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(Config.FONT_32);

        double y = Config.HEIGHT / 2 + Config.HEIGHT / 6;
        StdDraw.text(Config.WIDTH / 2, y, "Menu Pause");

        StdDraw.setFont(Config.FONT_20);
        StdDraw.setPenColor(new Color(150, 150, 150));

        System.out.println(this.getSelectedValue());

        y -= Config.HEIGHT / 8;

        for (Action action : this.getAvailableValues()) {

            if(action == this.getSelectedValue()) {

                DisplayUtil.drawPicture(Config.WIDTH / 2 - 150, y, PathUtil.getGlobalGuiPath("finger"), 48, 48);

            }

            StdDraw.text(Config.WIDTH / 2, y, action.text);
            y -= Config.HEIGHT / 14;

        }

        StdDraw.setPenColor(Color.WHITE);

        DisplayUtil.drawPicture(Config.WIDTH - 48, 105, PathUtil.getKeytipPath("up_down"), 24, 48);
        StdDraw.textRight(Config.WIDTH - 78, 105, "S\u00e9l\u00e9ct.");
        DisplayUtil.drawPicture(Config.WIDTH - 48, 155, PathUtil.getKeytipPath("enter"), 24, 24);
        StdDraw.textRight(Config.WIDTH - 78, 155, "Valider");

    }

    public MenuModel getModel() {
        return MenuModel.PAUSE_MENU;
    }

}
