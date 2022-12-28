package main.render;

import librairies.StdDraw;
import main.game.GameState;
import main.game.Movement;
import main.control.Cursor;
import main.game.Game;
import main.game.GameView;
import main.map.Case;
import main.menu.AnimatedMenu;
import main.menu.Menu;
import main.menu.MenuManager;
import main.menu.model.MainMenu;
import main.weather.Weather;
import ressources.Config;
import ressources.DisplayUtil;

import java.awt.*;

public class Renderer {

    private final AnimationClock terrainClockSync;
    private final AnimationClock unitClockSync;
    private final MenuManager menuManager;

    public Renderer(MenuManager menuManager) {

        this.menuManager = menuManager;

        this.terrainClockSync = new AnimationClock(Config.MAP_ANIMATION_FRAME_COUNT, Config.MAP_ANIMATION_FRAME_DURATION, true);
        this.unitClockSync = new AnimationClock(Config.UNIT_LONG_ANIMATION_FRAME_COUNT, Config.UNIT_ANIMATION_FRAME_DURATION, true);

        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(Config.WIDTH, Config.HEIGHT);
        StdDraw.setXscale(0, Config.WIDTH);
        StdDraw.setYscale(0, Config.HEIGHT);
        StdDraw.setTitle(Config.TITLE);
        StdDraw.setIcon(Config.ICON_PATH);

    }

    public void clearBuffer() {
        StdDraw.clear(Color.BLACK);
    }

    public void render(GameState gameState, Game game) {

        synchronized (this) {

            boolean copyBuffer = false;

            switch (gameState) {
                case PLAYING_SELECTING_UNIT_ACTION:
                case PLAYING_SELECTING:
                    copyBuffer = this.renderMap(gameState, game, game.getCursor().needsRefresh());
                    copyBuffer |= this.renderCursor(game, copyBuffer);
                    break;
                case PLAYING_MOVING_UNIT:
                    copyBuffer = this.renderMap(gameState, game, game.getCursor().needsRefresh());
                    copyBuffer |= this.renderMovement(game, copyBuffer);
                    copyBuffer |= this.renderCursor(game, copyBuffer);
                    break;
            }

            for (Menu menu : this.menuManager.getMenus()) {
                copyBuffer |= this.renderMenu(menu, copyBuffer);
            }

            if (copyBuffer) {
                StdDraw.show();
            }

        }

    }

    private boolean renderMenu(Menu menu, boolean forceRender) {

        if (!menu.isVisible()) return false;

        if (menu.needsRefresh() || forceRender) {

            if (menu instanceof AnimatedMenu) {

                if (menu instanceof MainMenu)
                    System.out.println("Rendering main menu: " + menu.isVisible() + " " + menu.needsRefresh() + "" + menu.getClass());

                AnimatedMenu animatedMenu = (AnimatedMenu) menu;

                if (animatedMenu.needsRefresh() || forceRender) {
                    animatedMenu.render();
                    animatedMenu.nextFrame();
                    animatedMenu.needsRefresh(false);
                    return true;
                }

            } else {

                if (menu.needsRefresh() || forceRender) {
                    menu.render();
                    menu.needsRefresh(false);
                    return true;
                }

            }

        }

        return false;

    }

    private boolean renderMenu(Menu.Model model, boolean forceRender) {

        Menu menu = this.menuManager.getMenu(model);
        if (menu == null) return false;

        return this.renderMenu(menu, forceRender);

    }

    /**
     * Rendre la carte sur l'ecran
     *
     * @param game        La partie en cours
     * @param forceRender Forcer le rendu
     * @return True s'il est necessaire de mettre a jour l'ecran
     */
    private boolean renderMap(GameState gameState, Game game, boolean forceRender) {

        if (game == null) return false;

        boolean terrainNeedsRefresh = this.terrainClockSync.needsRefresh();
        boolean unitNeedsRefresh = this.unitClockSync.needsRefresh();

        if (terrainNeedsRefresh) this.terrainClockSync.nextFrame();
        if (unitNeedsRefresh) this.unitClockSync.nextFrame();

        if (terrainNeedsRefresh || unitNeedsRefresh || forceRender) {

            final int mapWidth = game.getWidth();
            final int mapHeight = game.getHeight();
            final Weather weather = game.getWeather();
            final GameView gameView = game.getView();

            final int maxIterationX = Math.min(mapWidth, Config.MAP_COLUMN_COUNT);
            final int maxIterationY = Math.min(mapHeight, Config.MAP_ROW_COUNT) - 1;

            for (int i = 0; i < maxIterationX; i++) {
                for (int j = maxIterationY; j >= 0; j--) {
                    gameView.getCase(i, j).renderTerrain(i, j, mapWidth, mapHeight, weather, this.terrainClockSync);
                    gameView.getCase(i, j).renderUnit(i, j, mapWidth, mapHeight, this.unitClockSync);
                }
            }

            return true;

        }

        return false;

    }

    private boolean renderCursor(Game game, boolean forceRender) {

        Cursor cursor = game.getCursor();

        if (cursor.needsRefresh() || forceRender) {
            DisplayUtil.drawCursor(game.getView().getCursorX(), game.getView().getCursorY(), game.getWidth(), game.getHeight());
            game.getCursor().needsRefresh(false);
            return true;
        }
        return false;
    }

    private boolean renderMovement(Game game, boolean forceRender) {

        if (game == null) return false;

        if (game.getMovement().needsRefresh() || forceRender) {

            final Movement movement = game.getMovement();
            final GameView gameView = game.getView();
            final int mapWidth = game.getWidth();
            final int mapHeight = game.getHeight();

            // Rendre la fleche
            for (Movement.Arrow arrow : movement.toDirectionalArrows()) {

                Case c = arrow.getCase();

                if (gameView.isVisible(c)) {

                    int x = gameView.offsetX(c.getX()); // Coordonnees reelles de la case -> Coordonnees de l'ecran
                    int y = gameView.offsetY(c.getY());

                    DisplayUtil.drawPictureInCase(x, y, mapWidth, mapHeight, arrow.getPath(game.getCurrentPlayer().getType()));

                }

            }

            // Rendre l'unite selectionne au-dessus de la fleche
            if (game.getView().isVisible(movement.getMovementHead())) {

                int x = gameView.offsetX(movement.getMovementHead().getX());
                int y = gameView.offsetY(movement.getMovementHead().getY());

                gameView.getCase(x, y).renderUnit(x, y, mapWidth, mapHeight, this.unitClockSync);

            }

            game.getMovement().refreshed();

            return true;

        }


        return false;

    }

}
