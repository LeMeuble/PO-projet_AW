package main.render;

import librairies.StdDraw;
import main.GameState;
import main.Movement;
import main.controller.Cursor;
import main.map.Case;
import main.map.Game;
import main.menu.AnimatedMenu;
import main.menu.Menu;
import main.menu.MenuManager;
import main.menu.model.MainMenu;
import ressources.Config;
import ressources.DisplayUtil;
import sun.applet.Main;

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
                    copyBuffer = this.renderMap(game, game.getCursor().needsRefresh());
                    copyBuffer |= this.renderCursor(game, copyBuffer);
                    break;
                case PLAYING_MOVING_UNIT:
                    copyBuffer = this.renderMap(game, game.getCursor().needsRefresh());
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

                if(menu instanceof MainMenu) System.out.println("Rendering main menu: " + menu.isVisible() + " " + menu.needsRefresh() + "" + menu.getClass());

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
    private boolean renderMap(Game game, boolean forceRender) {

        if (game == null) return false;

        boolean terrainNeedsRefresh = this.terrainClockSync.needsRefresh();
        boolean unitNeedsRefresh = this.unitClockSync.needsRefresh();

        if (terrainNeedsRefresh) this.terrainClockSync.nextFrame();
        if (unitNeedsRefresh) this.unitClockSync.nextFrame();

        if (terrainNeedsRefresh || unitNeedsRefresh || forceRender) {

            for (int i = 0; i < Math.min(game.getWidth(), Config.MAP_COLUMN_COUNT); i++) {
                for (int j = Math.min(game.getWidth(), Config.MAP_ROW_COUNT) - 1; j >= 0; j--) {
                    game.getView().getCase(i, j).render(i, j, game, this.terrainClockSync, this.unitClockSync);
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
            game.getCursor().refreshed();
            return true;
        }
        return false;
    }

    private boolean renderMovement(Game game, boolean forceRender) {

        if (game == null) return false;

        if (game.getMovement().needsRefresh() || forceRender) {

            Movement movement = game.getMovement();

            // Rendre la fleche
            for (Movement.Arrow arrow : movement.toDirectionalArrows()) {

                Case c = arrow.getCase();

                if (game.getView().isVisible(c)) {

                    int x = game.getView().offsetX(c.getX()); // Coordonnees reelles de la case -> Coordonnees de l'ecran
                    int y = game.getView().offsetY(c.getY());

                    DisplayUtil.drawPictureInCase(x, y, game.getWidth(), game.getHeight(), arrow.getPath(game.getCurrentPlayer().getType()));

                }

            }

            // Rendre l'unite selectionne au-dessus de la fleche
            if (game.getView().isVisible(movement.getSource())) {

                int x = game.getView().offsetX(movement.getSource().getX());
                int y = game.getView().offsetY(movement.getSource().getY());

                game.getView().getCase(x, y).renderUnit(x, y, game, this.terrainClockSync, this.unitClockSync);

            }

            game.getMovement().refreshed();

            return true;

        }


        return false;

    }

}
