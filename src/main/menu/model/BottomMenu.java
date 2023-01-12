package main.menu.model;

import librairies.StdDraw;
import main.MiniWars;
import main.animation.AnimationClock;
import main.control.KeyTips;
import main.game.Game;
import main.game.GameState;
import main.game.Player;
import main.map.Case;
import main.map.Coordinate;
import main.menu.AnimatedMenu;
import main.menu.MenuModel;
import main.terrain.Property;
import main.weapon.Weapon;
import ressources.Config;
import ressources.DisplayUtil;
import ressources.PathUtil;

import java.util.List;

public class BottomMenu extends AnimatedMenu {


    /**
     * Constructeur d'un menu
     */
    public BottomMenu() {
        super(8, new AnimationClock(2, 1500));
    }

    @Override
    public void render() {

        final Game game = MiniWars.getInstance().getCurrentGame();
        final Player.Type currentPlayer = game.getCurrentPlayer().getType();
        final Coordinate cursor = game.getCursor().getCoordinate();
        final Case selectedCase = game.getGrid().getCase(cursor);
        final GameState gameState = MiniWars.getInstance().getGameState();

        DisplayUtil.drawPicture(Config.WIDTH / 2d, 1.75d * Config.PIXEL_PER_CASE, PathUtil.getBottomGuiPath(game.getCurrentPlayer().getType()), Config.WIDTH, 3 * Config.PIXEL_PER_CASE + 0.5 * Config.PIXEL_PER_CASE);

        DisplayUtil.drawIntegerValue(Config.WIDTH - 12, 3 * Config.PIXEL_PER_CASE + 2, String.valueOf(game.getCurrentPlayer().getMoney()), 18, "right");

        selectedCase.renderTerrain(40, 60, game.getWeather(), 0);
        DisplayUtil.drawPicture(82, 86, PathUtil.getIndicatorPath("defense"), 20, 20);
        DisplayUtil.drawIntegerValue(82 + 32, 86, (int) (selectedCase.getTerrain().getTerrainCover() * 100) + "%", 14, "left");

        if (selectedCase.getTerrain() instanceof Property) {

            DisplayUtil.drawPicture(82, 60, PathUtil.getIndicatorPath("health"), 20, 20);
            DisplayUtil.drawIntegerValue(82 + 32, 60, String.valueOf((int) ((Property) selectedCase.getTerrain()).getDefense()), 14, "left");

        }

        if (selectedCase.hasUnit() && !selectedCase.isFoggy()) {

            selectedCase.renderUnit(250, 60, 0, 64, 64, false);
            DisplayUtil.drawPicture(290, 86, PathUtil.getIndicatorPath("health"), 20, 20);
            DisplayUtil.drawIntegerValue(290 + 32, 86, String.valueOf((int) selectedCase.getUnit().getHealth()), 14, "left");
            DisplayUtil.drawPicture(290, 60, PathUtil.getIndicatorPath("fuel"), 20, 20);
            DisplayUtil.drawIntegerValue(290 + 32, 60, String.valueOf(selectedCase.getUnit().getEnergy()), 14, "left");

            String ammo = "";

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

        for (int i = offsetI; i < keyTips.size() && i < offsetI + tipsPerPage; i++) {

            final KeyTips keyTip = keyTips.get(i);

            DisplayUtil.drawPicture(450, y, PathUtil.getKeytipPath(keyTip.getTexture()), keyTip.getTextureWidth(), keyTip.getTextureHeight());
            StdDraw.textLeft(450 + 48, y, keyTip.getText());
            y -= 28;

        }

        final int currentPage = (int) Math.ceil((double) (offsetI + 1) / (double) tipsPerPage);
        final int nbPages = (int) Math.ceil((double) keyTips.size() / (double) tipsPerPage);

        if(nbPages > 0)
            DisplayUtil.drawIntegerValue(528, 110, currentPage + "/" + nbPages, 12, "left");

    }

    @Override
    public MenuModel getModel() {
        return MenuModel.BOTTOM_MENU;
    }

}
