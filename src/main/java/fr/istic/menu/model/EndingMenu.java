package fr.istic.menu.model;

import fr.istic.StdDraw;
import fr.istic.game.Game;
import fr.istic.game.Player;
import fr.istic.menu.FadeMenu;
import fr.istic.menu.MenuModel;
import fr.istic.util.HelperMethod;
import fr.istic.Config;
import fr.istic.DisplayUtil;
import fr.istic.PathUtil;

import java.awt.*;

/**
 * Classe representant un ecran de fin de partie
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class EndingMenu extends FadeMenu {


    private final Game game;
    private int textAlpha;
    private boolean textShown;

    /**
     * Constructeur d'un fr.istic.menu
     */
    public EndingMenu(Game game) {
        super(10, 50);
        this.game = game;
        this.textAlpha = 0;
        this.textShown = false;
    }

    /**
     * Methode gerant l'affichage du fr.istic.menu de fin de partie
     */
    @Override
    public void render() {

        final int alpha = this.getAlpha();
        final Color black = new Color(0, 0, 0, alpha);
        final Color textColor = new Color(255, 255, 255, this.textAlpha);

        final Player.Type winner = game.getWinner();

        StdDraw.setPenColor(black);
        StdDraw.filledRectangle(Config.WIDTH / 2d, Config.HEIGHT / 2d, Config.WIDTH / 2d, Config.HEIGHT / 2d);

        if (this.isFinished() && isFadingIn() && !this.textShown) {

            this.textAlpha += 50;

            if (this.textAlpha >= 255) {

                this.textAlpha = 255;
                this.textShown = true;

            }

        }

        StdDraw.setPenColor(textColor);
        StdDraw.setFont(Config.FONT_64);
        StdDraw.text(Config.WIDTH / 2, Config.HEIGHT * 3 / 4d + 32, "Game over!");

        StdDraw.setFont(Config.FONT_26);
        StdDraw.textLeft(Config.WIDTH / 2 - 90, Config.HEIGHT * 3 / 4d - 32, "Gagnant : ");

        StdDraw.setPenColor(winner.getColor(textAlpha));

        StdDraw.textLeft(Config.WIDTH / 2 + 40, Config.HEIGHT * 3 / 4d - 32, winner.getName());

        if (this.textShown) {

            DisplayUtil.drawPicture(Config.WIDTH / 4, Config.HEIGHT / 3 + 48, PathUtil.getTeamWinIcon(winner), 128, 320);

            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.line(Config.WIDTH / 2, Config.HEIGHT / 3 + 16, Config.WIDTH / 2, Config.HEIGHT / 3 + 80);

            double statY = Config.HEIGHT / 3 + 100;

            StdDraw.textRight((3 * Config.WIDTH) / 4, statY, "Jours : ");
            StdDraw.textLeft((3 * Config.WIDTH) / 4, statY, String.valueOf(game.getDay()));
            statY -= 32;
            StdDraw.textRight((3 * Config.WIDTH) / 4, statY, "Dur\u00e9e : ");
            StdDraw.textLeft((3 * Config.WIDTH) / 4, statY, HelperMethod.millisToTime(game.getDuration()));
            statY -= 32;
            StdDraw.textRight((3 * Config.WIDTH) / 4, statY, "Unit\u00e9s : ");
            StdDraw.textRight((3 * Config.WIDTH) / 4, statY - 32, "Propri\u00e9t\u00e9s : ");

            double unitX = (3 * Config.WIDTH) / 4;
            double propertyX = (3 * Config.WIDTH) / 4;

            for (Player player : game.getPlayers()) {
                StdDraw.setPenColor(player.getType().getColor());

                StdDraw.textLeft(unitX, statY, String.valueOf(player.getStatUnitCount()));
                StdDraw.textLeft(propertyX, statY - 32, String.valueOf(player.getStatPropertyCount()));
                unitX += DisplayUtil.getTextWidth(Config.FONT_26, String.valueOf(player.getStatUnitCount())) + 8;
                propertyX += DisplayUtil.getTextWidth(Config.FONT_26, String.valueOf(player.getStatPropertyCount()
                )) + 8;
            }

            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(Config.WIDTH / 2, Config.HEIGHT * 1 / 5.5d, "Appuyer sur entr\u00e9e pour continuer.");

        }

    }

    @Override
    public boolean needsRefresh() {
        return super.needsRefresh() || (!this.textShown && this.getAnimationClock().needsRefresh());
    }

    @Override
    public MenuModel getModel() {
        return MenuModel.ENDING_MENU;
    }

}
