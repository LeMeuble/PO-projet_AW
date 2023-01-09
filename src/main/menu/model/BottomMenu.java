package main.menu.model;

import main.MiniWars;
import main.game.Game;
import main.map.Case;
import main.map.Coordinate;
import main.menu.Menu;
import main.menu.MenuModel;
import ressources.Config;
import ressources.DisplayUtil;
import ressources.PathUtil;

public class BottomMenu extends Menu {


    /**
     * Constructeur d'un menu
     *
     */
    public BottomMenu() {
        super(10);
    }

    @Override
    public void render() {

        final Game game = MiniWars.getInstance().getCurrentGame();
        final Coordinate cursor = game.getCursor().getCoordinate();
        final Case selectedCase = game.getGrid().getCase(cursor.getX(), cursor.getY());

        DisplayUtil.drawPicture(Config.WIDTH / 2d, 1.75d * Config.PIXEL_PER_CASE, PathUtil.getBottomGuiPath(game.getCurrentPlayer().getType()), Config.WIDTH, 3 * Config.PIXEL_PER_CASE + 0.5 * Config.PIXEL_PER_CASE);

        DisplayUtil.drawPicture(Config.WIDTH / 2d + 250, 3 * Config.PIXEL_PER_CASE + 2, PathUtil.getIconPath("coin"), 30, 30);

        DisplayUtil.drawIntegerValue(Config.WIDTH - 12, 3 * Config.PIXEL_PER_CASE + 2, game.getCurrentPlayer().getMoney(), 18);

        selectedCase.renderTerrain(40, 64, game.getWeather(), 0);

        if (selectedCase.hasUnit() && !selectedCase.isFoggy()) {
            selectedCase.renderUnit(250, 64, 0);
        }

    }

    @Override
    public MenuModel getModel() {
        return MenuModel.BOTTOM_MENU;
    }

}
