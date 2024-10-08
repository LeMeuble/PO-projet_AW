package fr.istic.render;

import fr.istic.StdDraw;
import fr.istic.Logger;
import fr.istic.MiniWars;
import fr.istic.animation.AnimationClock;
import fr.istic.animation.MovementAnimation;
import fr.istic.control.Cursor;
import fr.istic.game.Game;
import fr.istic.game.GameState;
import fr.istic.game.GameView;
import fr.istic.game.Movement;
import fr.istic.map.Case;
import fr.istic.map.Coordinate;
import fr.istic.menu.AnimatedMenu;
import fr.istic.menu.Menu;
import fr.istic.menu.MenuManager;
import fr.istic.unit.UnitAnimation;
import fr.istic.unit.UnitType;
import fr.istic.weather.Weather;
import fr.istic.Config;
import fr.istic.DisplayUtil;
import fr.istic.PathUtil;

import java.awt.*;
import java.util.Set;

/**
 * Classe qui gere tout ce qui est lie au rendu des elements a l'ecran
 *
 * @author PandaLunatique
 * @author LeMeuble
 * @see Renderable
 * @see AnimationClock
 * @see MovementAnimation
 */
public class Renderer {

    private static Renderer instance;

    private final AnimationClock terrainClockSync;
    private final AnimationClock unitClockSync;
    private final AnimationClock unitMovingClockSync;

    private MovementAnimation movementAnimation;

    /**
     * Constructeur du renderer
     */
    public Renderer() {

        // Les boucles d'animation (synchronisation)
        this.terrainClockSync = new AnimationClock(Config.MAP_ANIMATION_FRAME_COUNT, Config.MAP_ANIMATION_FRAME_DURATION, true);
        this.unitClockSync = new AnimationClock(Config.UNIT_ANIMATION_FRAME_COUNT, Config.UNIT_ANIMATION_FRAME_DURATION, true);
        this.unitMovingClockSync = new AnimationClock(Config.UNIT_ANIMATION_FRAME_COUNT, Config.UNIT_MOVING_FRAME_DURATION, true);

        this.movementAnimation = null;

        // Initialisation de la fenetre
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize((int) Config.WIDTH, (int) Config.HEIGHT);
        StdDraw.setXscale(0, Config.WIDTH);
        StdDraw.setYscale(0, Config.HEIGHT);
        StdDraw.setTitle(Config.TITLE);
        StdDraw.setIcon(Config.ICON_PATH);

    }

    /**
     * Obtenir l'instance du {@link Renderer} actuelle ou
     * en creer une nouvelle si cette derniere n'existe pas.
     *
     * @return L'instance commune de {@link Renderer}
     */
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

    /**
     * Effacer l'ecran
     */
    public void clearBuffer() {
        StdDraw.clear(Color.BLACK);
    }

    /**
     * Methode appellee par {@link MiniWars#update()} pour mettre a jour l'ecran.
     * Cette methode est le point d'entree pour tout ce qui est lie au rendu sur l'ecran.
     * <p>
     * Cette methode va effectuer different rendu selon l'etat de jeu actuel.
     *
     * @param gameState Etat de jeu actuel
     * @param game      Instance de la partie si cette derniere existe
     */
    public void render(GameState gameState, Game game) {

        synchronized (this) {

            try {

                boolean copyBuffer = false;
                this.clearBuffer();

                // Selon l'etat de jeu, differentes actions sont realises
                switch (gameState) {

                    case MENU_MAP_SELECTION:
                    case PLAYING_ENDIND_SCREEN:
                    case MENU_TITLE_SCREEN: {
                        this.clearBuffer();
                        copyBuffer = MenuManager.getInstance().anyMenuNeedsRefresh();
                        break;
                    }
                    case PLAYING_MOVING_UNIT: {
                        copyBuffer = this.renderMap(gameState, game, game.getCursor().needsRefresh() || MenuManager.getInstance().anyMenuNeedsRefresh());
                        copyBuffer |= this.renderOverlay(game, copyBuffer);
                        copyBuffer |= this.renderMovement(game, copyBuffer);
                        copyBuffer |= this.renderCursor(game, gameState, copyBuffer);
                        break;
                    }
                    case PLAYING_RENDERING_MOVING_UNIT: {

                        copyBuffer = this.renderMap(gameState, game, game.getCursor().needsRefresh() || this.unitMovingClockSync.needsRefresh());
                        copyBuffer |= this.renderMovementAnimation(game, copyBuffer);

                        break;
                    }

                    default: {
                        if (MiniWars.getInstance().isPlaying() && game != null) {

                            copyBuffer = this.renderMap(gameState, game, game.getCursor().needsRefresh() || MenuManager.getInstance().anyMenuNeedsRefresh() || PopupRegistry.getInstance().needsRefresh());
                            copyBuffer |= this.renderOverlay(game, copyBuffer);
                            copyBuffer |= this.renderCursor(game, gameState, copyBuffer);
                        }
                        break;
                    }

                }

                // Affichage de tous les menus selon leur priorites (calculee dans getMenus())
                for (Menu menu : MenuManager.getInstance().getMenus()) {
                    copyBuffer |= this.renderMenu(menu, copyBuffer);
                }

                // Rendu des Popups
                copyBuffer |= this.renderPopups(copyBuffer);

                // Actualisation de l'ecran seulement si une action a ete effectuee sur le offscreen buffer
                if (copyBuffer) {
                    StdDraw.show();
                }

            }
            catch (Exception e) {
                e.printStackTrace();
                Logger.getLogger().write(e);
            }

        }

    }

    public void setMovementAnimation(MovementAnimation movementAnimation) {
        this.movementAnimation = movementAnimation;
    }

    /**
     * Permet de rendre les popups (en haut a droite de l'ecran) sur l'ecran.
     *
     * @param forceRender Indiquer si le rendu doit etre force
     *
     * @return true si au moins une popup a ete actualisee sur l'offscreen
     */
    private boolean renderPopups(boolean forceRender) {

        if (PopupRegistry.getInstance().needsRefresh() || forceRender) {

            PopupRegistry.getInstance().render();
            return true;

        }

        return false;
    }

    /**
     * Permet de rendre une animation de mouvement sur l'ecran.
     *
     * @param game        L'instance de la partie actuelle.
     * @param forceRender Indiquer si le rendu doit etre force
     *
     * @return true si l'animatiion a ete actualisee
     */
    private boolean renderMovementAnimation(Game game, boolean forceRender) {

        if (this.movementAnimation == null) return false;

        if (this.movementAnimation.isFinished()) {
            this.movementAnimation = null;
            return false;
        }

        if (this.unitMovingClockSync.needsRefresh() || forceRender) {

            final int gridX = this.movementAnimation.getGridX();
            final int gridY = this.movementAnimation.getGridY();

            game.getView().focus(game.getGrid().getCase(new Coordinate(gridX, gridY)));

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

    /**
     * Permet de rendre les overlays (cases en surbrillances) sur l'ecran.
     *
     * @param game        L'instance de la partie actuelle.
     * @param forceRender Indiquer si le rendu doit etre force
     *
     * @return true si le fr.istic.menu a ete actualisee sur l'offscreen
     */
    private boolean renderOverlay(Game game, boolean forceRender) {

        if (forceRender) {

            final Set<Case> cases = game.getOverlayCases();

            for (Case c : cases) {

                Coordinate coordinate = c.getCoordinate();

                if (game.getView().isVisible(game.getGrid().getCase(coordinate))) {

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

    /**
     * Permet de rendre une fr.istic.menu en particulier.
     *
     * @param menu        L'instance du fr.istic.menu a rendre.
     * @param forceRender Indiquer si le rendu doit etre force
     *
     * @return true si le fr.istic.menu a ete actualisee sur l'offscreen
     */
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
     * Permet d'effectuer le rendu de la fr.istic.map selon la {@link GameView}.
     *
     * @param gameState   Etat du jeu actuel
     * @param game        Partie en cours
     * @param forceRender Indiquer si le rendu doit etre force
     *
     * @return true si la fr.istic.map a ete actualisee sur l'offscreen
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
            final int maxIterationY = Math.min(mapHeight, Config.MAP_ROW_COUNT);

            for (int i = 0; i < maxIterationX; i++) {
                for (int j = maxIterationY - 1; j >= 0; j--) {

                    final double x = DisplayUtil.getCenterX(i, mapWidth);
                    final double y = DisplayUtil.getCenterY(j, mapHeight);

                    gameView.getCase(i, j).renderTerrain(x, y, weather, this.terrainClockSync.getFrame());

                    if (!gameView.getCase(i, j).isFoggy()) {
                        gameView.getCase(i, j).renderUnit(x, y, this.unitClockSync.getFrame());
                    }

                }
            }

            if (weather == Weather.RAINY) {

                DisplayUtil.drawPicture(Config.WIDTH / 2, Config.HEIGHT / 2 + Config.BOTTOM_MENU_MARGIN, PathUtil.getWeatherOverlayPath(weather, this.terrainClockSync.getFrame()), Config.WIDTH, Config.HEIGHT);

            }

            final double midX = DisplayUtil.getCenterX(maxIterationX / 2, mapWidth) - (maxIterationX % 2 == 0 ? Config.PIXEL_PER_CASE / 2d : 0);
            final double midY = DisplayUtil.getCenterY(maxIterationY / 2, mapHeight) - (maxIterationY % 2 == 0 ? Config.PIXEL_PER_CASE / 2d : 0);


            if(gameView.canMoveDown()) {
                DisplayUtil.drawPicture(midX, DisplayUtil.getCenterY(0, mapHeight), PathUtil.getGlobalGuiPath("down_arrow"), 32, 32);
            }

            if(gameView.canMoveUp()) {
                DisplayUtil.drawPicture(midX, DisplayUtil.getCenterY(maxIterationY - 1, mapHeight), PathUtil.getGlobalGuiPath("up_arrow"), 32, 32);
            }

            if(gameView.canMoveLeft()) {
                DisplayUtil.drawPicture(DisplayUtil.getCenterX(0, mapWidth), midY, PathUtil.getGlobalGuiPath("left_arrow"), 32, 32);
            }

            if(gameView.canMoveRight()) {
                DisplayUtil.drawPicture(DisplayUtil.getCenterX(maxIterationX - 1, mapWidth), midY, PathUtil.getGlobalGuiPath("right_arrow"), 32, 32);
            }

            return true;

        }

        return false;

    }

    /**
     * Permet d'effectuer le rendu du curseur selon la {@link GameView}.
     *
     * @param gameState   Etat du jeu actuel
     * @param game        Partie en cours
     * @param forceRender Indiquer si le rendu doit etre force
     *
     * @return true si le curseur a ete actualisee sur l'offscreen
     */
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

    /**
     * Permet de rendre le mouvement (fleches) sur l'ecran selon le {@link GameView} et le {@link Movement}.
     *
     * @param game        Partie en cours
     * @param forceRender Indiquer si le rendu doit etre force
     *
     * @return true si le mouvement a ete actualisee sur l'offscreen
     */
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

                int offsetX = gameView.offsetX(movement.getMovementHead().getCoordinate().getX());
                int offsetY = gameView.offsetY(movement.getMovementHead().getCoordinate().getY());

                final double x = DisplayUtil.getCenterX(offsetX, mapWidth);
                final double y = DisplayUtil.getCenterY(offsetY, mapHeight);

                gameView.getCase(offsetX, offsetY).renderUnit(x, y, this.unitClockSync.getFrame());
            }

            game.getMovement().needsRefresh(false);

            return true;

        }

        return false;

    }

}
