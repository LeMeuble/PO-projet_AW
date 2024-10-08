package fr.istic.menu.model;

import fr.istic.StdDraw;
import fr.istic.MiniWars;
import fr.istic.animation.AnimationClock;
import fr.istic.control.KeyTips;
import fr.istic.game.Game;
import fr.istic.game.GameState;
import fr.istic.game.Player;
import fr.istic.map.Case;
import fr.istic.map.Coordinate;
import fr.istic.menu.AnimatedMenu;
import fr.istic.menu.MenuModel;
import fr.istic.terrain.Property;
import fr.istic.weapon.Weapon;
import fr.istic.Config;
import fr.istic.DisplayUtil;
import fr.istic.PathUtil;

import java.util.List;

/**
 * Classe representant le fr.istic.menu informatif au bas de l'ecran
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class BottomMenu extends AnimatedMenu {


    /**
     * Constructeur d'un fr.istic.menu
     */
    public BottomMenu() {
        super(8, new AnimationClock(2, 1500));
    }

    /**
     * Methode gerant l'affichage du fr.istic.menu
     */
    @Override
    public void render() {

        final Game game = MiniWars.getInstance().getCurrentGame();
        final Player.Type currentPlayer = game.getCurrentPlayer().getType();
        final Coordinate cursor = game.getCursor().getCoordinate();
        final Case selectedCase = game.getGrid().getCase(cursor);
        final GameState gameState = MiniWars.getInstance().getGameState();

        StdDraw.setFont(Config.FONT_20);

        DisplayUtil.drawPicture(Config.WIDTH / 2d, 1.75d * Config.PIXEL_PER_CASE, PathUtil.getBottomGuiPath(game.getCurrentPlayer().getType()), Config.WIDTH, 3 * Config.PIXEL_PER_CASE + 0.5 * Config.PIXEL_PER_CASE);

        DisplayUtil.drawIntegerValue(Config.WIDTH - 12, 3 * Config.PIXEL_PER_CASE + 2, String.valueOf(game.getCurrentPlayer().getMoney()), 18, "right");

        // Affiche la case courante en bas a gauche
        selectedCase.renderTerrain(40, 60, game.getWeather(), 0);
        DisplayUtil.drawPicture(82, 86, PathUtil.getIndicatorPath("defense"), 20, 20);
        DisplayUtil.drawIntegerValue(82 + 32, 86, (int) (selectedCase.getTerrain().getTerrainCover() * 100) + "%", 14, "left");

        // En plus d'informations sur les proprietes
        if (selectedCase.getTerrain() instanceof Property) {

            DisplayUtil.drawPicture(82, 60, PathUtil.getIndicatorPath("health"), 20, 20);
            DisplayUtil.drawIntegerValue(82 + 32, 60, String.valueOf((int) ((Property) selectedCase.getTerrain()).getDefense()), 14, "left");

        }

        // Et sur l'unite presente dans la case
        if (selectedCase.hasUnit() && !selectedCase.isFoggy()) {

            selectedCase.renderUnit(250, 60, 0, 64, 64, false);
            DisplayUtil.drawPicture(290, 86, PathUtil.getIndicatorPath("health"), 20, 20);
            DisplayUtil.drawIntegerValue(290 + 32, 86, String.valueOf((int) selectedCase.getUnit().getHealth()), 14, "left");
            DisplayUtil.drawPicture(290, 60, PathUtil.getIndicatorPath("fuel"), 20, 20);
            DisplayUtil.drawIntegerValue(290 + 32, 60, String.valueOf(selectedCase.getUnit().getEnergy()), 14, "left");

            String ammo = "";
            // Affiche les munitions de l'unite
            for (Weapon w : selectedCase.getUnit().getWeapons()) {
                ammo += w.getAmmo() + "/";
            }

            if (ammo.length() > 0) {
                DisplayUtil.drawPicture(290, 34, PathUtil.getIndicatorPath("ammo"), 20, 20);
                DisplayUtil.drawIntegerValue(290 + 32, 34, ammo.substring(0, ammo.length() - 1), 14, "left");
            }

        }


        // keytips
        List<KeyTips> keyTips = KeyTips.getAssociatedKeyTips(gameState, selectedCase, currentPlayer);

        final int tipsPerPage = 3;
        final int offsetI = keyTips.size() > tipsPerPage ? (this.getFrame() == 1 ? tipsPerPage : 0) : 0;

        double y = 84;
        StdDraw.setPenColor(StdDraw.BLACK);

        // Affiche les raccourcis clavier
        for (int i = offsetI; i < keyTips.size() && i < offsetI + tipsPerPage; i++) {

            final KeyTips keyTip = keyTips.get(i);

            DisplayUtil.drawPicture(450, y, PathUtil.getKeytipPath(keyTip.getTexture()), keyTip.getTextureWidth(), keyTip.getTextureHeight());
            StdDraw.textLeft(450 + 48, y, keyTip.getText());
            y -= 28;

        }

        final int currentPage = (int) Math.ceil((double) (offsetI + 1) / (double) tipsPerPage);
        final int nbPages = (int) Math.ceil((double) keyTips.size() / (double) tipsPerPage);

        if (nbPages > 0)
            DisplayUtil.drawIntegerValue(528, 110, currentPage + "/" + nbPages, 12, "left");

    }

    @Override
    public MenuModel getModel() {
        return MenuModel.BOTTOM_MENU;
    }

}
