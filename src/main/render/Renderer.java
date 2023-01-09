package main.render;

import librairies.StdDraw;
import main.Logger;
import main.animation.AnimationClock;
import main.animation.MovementAnimation;
import main.control.Cursor;
import main.game.Game;
import main.game.GameState;
import main.game.GameView;
import main.game.Movement;
import main.map.Case;
import main.map.Coordinate;
import main.menu.AnimatedMenu;
import main.menu.Menu;
import main.menu.MenuManager;
import main.unit.UnitAnimation;
import main.unit.UnitType;
import main.weather.Weather;
import ressources.Config;
import ressources.DisplayUtil;
import ressources.PathUtil;

import java.awt.*;
import java.util.Set;

public class Renderer {

    private static Renderer instance;

    private final AnimationClock terrainClockSync;
    private final AnimationClock unitClockSync;
    private final AnimationClock unitMovingClockSync;

    private MovementAnimation movementAnimation;

    private int frames;
    private long previousFrameUpdate;

    public Renderer() {

        this.terrainClockSync = new AnimationClock(Config.MAP_ANIMATION_FRAME_COUNT, Config.MAP_ANIMATION_FRAME_DURATION, true);
        this.unitClockSync = new AnimationClock(Config.UNIT_ANIMATION_FRAME_COUNT, Config.UNIT_ANIMATION_FRAME_DURATION, true);
        this.unitMovingClockSync = new AnimationClock(Config.UNIT_ANIMATION_FRAME_COUNT, Config.UNIT_MOVING_FRAME_DURATION, true);

        this.movementAnimation = null;

        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize((int) Config.WIDTH, (int) Config.HEIGHT);
        StdDraw.setXscale(0, Config.WIDTH);
        StdDraw.setYscale(0, Config.HEIGHT);
        StdDraw.setTitle(Config.TITLE);
        StdDraw.setIcon(Config.ICON_PATH);

        this.frames = 0;
        this.previousFrameUpdate = System.currentTimeMillis();

    }

    public static Renderer getInstance() {

        if (instance == null) {

            synchronized (Renderer.class) {

                if (instance == null) {
                    instance = new Renderer();
                }

            }

        }

        return instance;

    }


    public void clearBuffer() {
        StdDraw.clear(Color.BLACK);
    }

    public void render(GameState gameState, Game game) {

        synchronized (this) {

            try {

                boolean copyBuffer = false;
                this.clearBuffer();

                switch (gameState) {

                    case MENU_MAP_SELECTION:
                    case MENU_TITLE_SCREEN:
                        break;
                    case PLAYING_MOVING_UNIT:
                        copyBuffer = this.renderMap(gameState, game, game.getCursor().needsRefresh());
                        copyBuffer |= this.renderOverlay(game, copyBuffer);
                        copyBuffer |= this.renderMovement(game, copyBuffer);
                        copyBuffer |= this.renderCursor(game, gameState, copyBuffer);
                        break;
                    case PLAYING_RENDERING_MOVING_UNIT:

                        copyBuffer = this.renderMap(gameState, game, game.getCursor().needsRefresh() || this.unitMovingClockSync.needsRefresh());
                        copyBuffer |= this.renderMovementAnimation(game, copyBuffer);

                        break;
                    case PLAYING_ENDIND_SCREEN:
                        clearBuffer();
                        StdDraw.setPenColor(StdDraw.WHITE);
                        StdDraw.text(150, 150, game.getWinner().getName());
                        copyBuffer = true;
                        break;

                    default:
                        copyBuffer = this.renderMap(gameState, game, game.getCursor().needsRefresh() || MenuManager.getInstance().anyMenuNeedsRefresh() || PopupRegistry.getInstance().needsRefresh());
                        copyBuffer |= this.renderOverlay(game, copyBuffer);
                        copyBuffer |= this.renderCursor(game, gameState, copyBuffer);
                        break;
                }

                for (Menu menu : MenuManager.getInstance().getMenus()) {
                    copyBuffer |= this.renderMenu(menu, copyBuffer);
                }

                copyBuffer |= this.renderPopups(copyBuffer);

                if (copyBuffer) {
                    StdDraw.show();
                    frames++;
                }

                if (System.currentTimeMillis() - previousFrameUpdate > 1000) {
                    Logger.getInstanceGameLoop().log("FPS: " + frames);
                    frames = 0;
                    previousFrameUpdate = System.currentTimeMillis();
                }
            }
            catch (Exception e) {
                Logger.getInstanceGameLoop().write(e);
            }

        }

    }

    public void addMovementAnimation(MovementAnimation movementAnimation) {
        this.movementAnimation = movementAnimation;
    }

    private boolean renderPopups(boolean forceRender) {

        if (PopupRegistry.getInstance().needsRefresh() || forceRender) {

            PopupRegistry.getInstance().render();
            return true;

        }

        return false;
    }

    private boolean renderMovementAnimation(Game game, boolean forceRender) {

        if (this.movementAnimation == null) return false;

        if (this.movementAnimation.isFinished()) {
            this.movementAnimation = null;
            return false;
        }

        if (this.unitMovingClockSync.needsRefresh() || forceRender) {

            final int gridX = this.movementAnimation.getGridX();
            final int gridY = this.movementAnimation.getGridY();

            game.getView().focus(game.getGrid().getCase(gridX, gridY));

            final double x = this.movementAnimation.getPixelX();
            final double y = this.movementAnimation.getPixelY() + Config.PIXEL_PER_CASE / 8;

            final UnitAnimation unitAnimation = movementAnimation.getUnitAnimation();
            final UnitType unitType = movementAnimation.getUnit().getType();

            DisplayUtil.drawPicture(x, y, PathUtil.getUnitAnimationPath(unitType, game.getCurrentPlayer().getType(), unitAnimation, unitMovingClockSync.getFrame()), Config.PIXEL_PER_CASE, Config.PIXEL_PER_CASE);

            if (this.unitMovingClockSync.needsRefresh()) {
                this.movementAnimation.nextStep();
                this.unitMovingClockSync.nextFrame();
            }

            return true;

        }

        return false;

    }

    private boolean renderOverlay(Game game, boolean forceRender) {

        if (forceRender) {

            final Set<Case> cases = game.getOverlayCases();

            for (Case c : cases) {

                Coordinate coordinate = c.getCoordinate();

                if (game.getView().isVisible(game.getGrid().getCase(coordinate.getX(), coordinate.getY()))) {

                    int offsetX = game.getView().offsetX(coordinate.getX());
                    int offsetY = game.getView().offsetY(coordinate.getY());

                    double x = DisplayUtil.getCenterX(offsetX, game.getWidth());
                    double y = DisplayUtil.getCenterY(offsetY, game.getHeight());

                    DisplayUtil.drawPicture(x, y, PathUtil.getOverlayPath(game.getOverlayType().getTextureName()), Config.PIXEL_PER_CASE, Config.PIXEL_PER_CASE);

                }

            }

            return true;

        }

        return false;
    }

    private boolean renderMenu(Menu menu, boolean forceRender) {

        if (!menu.isVisible()) return false;
        if (menu.needsRefresh() || forceRender) {

            menu.render();
            if (menu instanceof AnimatedMenu) {
                if (menu.needsRefresh()) ((AnimatedMenu) menu).nextFrame();
            }
            menu.needsRefresh(false);
            return true;

        }

        return false;

    }

    /**
     * Rendre la carte sur l'ecran
     *
     * @param game        La partie en cours
     * @param forceRender Forcer le rendu
     *
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

                    final double x = DisplayUtil.getCenterX(i, mapWidth);
                    final double y = DisplayUtil.getCenterY(j, mapHeight);

                    gameView.getCase(i, j).renderTerrain(x, y, weather, this.terrainClockSync.getFrame());

                    if (!gameView.getCase(i, j).isFoggy()) {
                        gameView.getCase(i, j).renderUnit(x, y, this.unitClockSync.getFrame());
                    }

                }
            }

            return true;

        }

        return false;

    }

    private boolean renderCursor(Game game, GameState gameState, boolean forceRender) {

        Cursor cursor = game.getCursor();

        if (cursor.needsRefresh() || forceRender) {
            if (gameState == GameState.PLAYING_SELECTING_TARGET)
                DisplayUtil.drawCrosshair(game.getView().getCursorX(), game.getView().getCursorY(), game.getWidth(), game.getHeight());
            else
                DisplayUtil.drawCursor(game.getView().getCursorX(), game.getView().getCursorY(), game.getWidth(), game.getHeight());
            game.getCursor().needsRefresh(false);
            return true;
        }
        return false;
    }

    private boolean renderMovement(Game game, boolean forceRender) {

        if (game == null) return false;
        if (game.getMovement() == null) return false;

        if (game.getMovement().needsRefresh() || forceRender) {

            final Movement movement = game.getMovement();
            final GameView gameView = game.getView();
            final int mapWidth = game.getWidth();
            final int mapHeight = game.getHeight();

            // Rendre la fleche
            for (Movement.Arrow arrow : movement.toDirectionalArrows()) {

                Case c = arrow.getCase();

                if (gameView.isVisible(c)) {

                    int x = gameView.offsetX(c.getCoordinate().getX()); // Coordonnees reelles de la case -> Coordonnees de l'ecran
                    int y = gameView.offsetY(c.getCoordinate().getY());

                    DisplayUtil.drawPictureInCase(x, y, mapWidth, mapHeight, arrow.getPath(game.getCurrentPlayer().getType()));
                }

            }

            if (game.getView().isVisible(movement.getMovementHead())) {

                int x = gameView.offsetX(movement.getMovementHead().getCoordinate().getX());
                int y = gameView.offsetY(movement.getMovementHead().getCoordinate().getY());

                gameView.getCase(x, y).renderUnit(x, y, this.unitClockSync.getFrame());
            }

            game.getMovement().needsRefresh(false);

            return true;

        }

        return false;

    }

}
