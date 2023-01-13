package main.menu.model;

import librairies.StdDraw;
import main.MiniWars;
import main.game.Player;
import main.menu.ActionMenu;
import main.menu.FadeMenu;
import main.menu.MenuModel;
import main.util.OptionSelector;
import ressources.Config;
import ressources.DisplayUtil;
import ressources.PathUtil;

import java.awt.*;
import java.util.Arrays;

public class NextTurnMenu extends FadeMenu {


    public enum Action implements ActionMenu.Text {

        YES("Passer le tour"),
        NO("Continuer");

        private final String text;

        Action(String text) {
            this.text = text;
        }

        @Override
        public String getText() {
            return this.text;
        }

    }

    public static class AskMenu extends ActionMenu<Action> {

        public AskMenu() {

            super(new OptionSelector<>(Arrays.asList(Action.values())), false);

        }

        @Override
        public void render() {

            super.render();

            double x = Config.MENU_ACTION_MARGIN + Config.MENU_ACTION_TOP_HEIGHT / 1.5d;
            double y = (Config.HEIGHT - Config.MENU_ACTION_MARGIN) - Config.MENU_ACTION_TOP_HEIGHT - Config.MENU_ACTION_MIDDLE_HEIGHT / 2.0d + 4;

            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setFont(Config.FONT_20);

            StdDraw.textLeft(x + 16, y, Action.YES.text);
            y -= Config.MENU_ACTION_MIDDLE_HEIGHT;
            StdDraw.textLeft(x + 16, y, Action.NO.text);

        }

        @Override
        public MenuModel getModel() {
            return MenuModel.NEXT_TURN_ASK_MENU;
        }

    }

    private int textAlpha;
    private boolean textShown;

    public NextTurnMenu() {
        super(10, 50);
        this.textAlpha = 0;
        this.textShown = false;
    }

    @Override
    public void render() {

        final Color backgroundColor = new Color(0, 0, 0, this.getAlpha());

        final int textAlpha = this.isFadingIn() ? this.textAlpha : this.getAlpha();

        final Color textColor = new Color(255, 255, 255, textAlpha);


        StdDraw.setPenColor(backgroundColor);

        StdDraw.filledRectangle(Config.WIDTH / 2d, Config.HEIGHT / 2d, Config.WIDTH / 2d, Config.HEIGHT / 2d);

        if (this.isFinished() && isFadingIn() && !this.textShown) {

            this.textAlpha += 50;

            if (this.textAlpha >= 255) {

                this.textAlpha = 255;
                this.textShown = true;

            }

        }

        if(MiniWars.getInstance().isPlaying()) {

            final Player.Type player = MiniWars.getInstance().getCurrentGame().getCurrentPlayer().getType();

            StdDraw.setFont(Config.FONT_20);
            StdDraw.setPenColor(textColor);

            StdDraw.textLeft(Config.WIDTH / 2 - 110, Config.HEIGHT * 3 / 4d - 64, "Au tour du joueur ");

            if (player == Player.Type.RED) {
                StdDraw.setPenColor(new Color(255, 0, 0, textAlpha));
            }
            else if (player == Player.Type.BLUE) {
                StdDraw.setPenColor(new Color(0, 0, 255, textAlpha));
            }
            else if (player == Player.Type.GREEN) {
                StdDraw.setPenColor(new Color(0, 255, 0, textAlpha));
            }
            else if (player == Player.Type.YELLOW) {
                StdDraw.setPenColor(new Color(255, 255, 0, textAlpha));
            }
            else if (player == Player.Type.BLACK) {
                StdDraw.setPenColor(new Color(255, 0, 255, textAlpha));
            }

            StdDraw.textLeft(Config.WIDTH / 2 + 72, Config.HEIGHT * 3 / 4d - 64, player.getName());

            StdDraw.setPenColor(textColor);

            StdDraw.text(Config.WIDTH / 2, Config.HEIGHT * 1 / 4d, "Appuyer sur entr\u00e9e pour continuer.");

            StdDraw.setFont(Config.FONT_64);
            StdDraw.text(Config.WIDTH / 2, Config.HEIGHT * 3 / 4d, "Jour " + MiniWars.getInstance().getCurrentGame().getDay());

        }

    }

    @Override
    public boolean needsRefresh() {

        return super.needsRefresh() || (!this.textShown && this.getAnimationClock().needsRefresh());

    }


    @Override
    public MenuModel getModel() {
        return MenuModel.NEXT_TURN_MENU;
    }

}
