/**
 * package principal
 */
package main;

import main.control.KeystrokeListener;
import main.game.*;
import main.map.MapMetadata;
import main.menu.MenuManager;
import main.menu.MenuModel;
import main.menu.model.MainMenu;
import main.menu.model.MapSelectionMenu;
import main.menu.model.NextTurnMenu;
import main.render.Renderer;

/**
 * Classe principale du jeu Cette classe est le point d'entrée du programme Elle initialise les composants du jeu et
 * lance la boucle principale
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 */
public class MiniWars {

    private static MiniWars instance;

    private final KeystrokeListener keystrokeListener;
    private final ActionHandler actionHandler;
    private final GameLoop gameLoop;
    private volatile Game currentGame;
    private GameState gameState;

    /**
     * Constructeur de MiniWars
     * Initialise les instances gerant les entrees clavier, la boucle du jeu
     * Ouvre le menu de selection des cartes
     */
    private MiniWars() {

        this.currentGame = null;
        this.gameState = GameState.MENU_TITLE_SCREEN;

        this.keystrokeListener = new KeystrokeListener();
        this.keystrokeListener.setHandler(this::handleKey);

        this.gameLoop = new GameLoop();
        this.gameLoop.setHandler(this::update);

        this.actionHandler = new ActionHandler(this);

        this.registerDefaultMenus();
        this.gameLoop.start();
        this.keystrokeListener.start();

        this.update();

    }

    public static MiniWars launch() {

        instance = new MiniWars();
        return instance;

    }

    public static MiniWars getInstance() {

        return MiniWars.instance;

    }

    /**
     * @return true si le jeu est termine, false sinon
     */
    public boolean isOver() {
        return this.gameState == null;
    }

    public void handleKey(KeystrokeListener.KeyCodes keycode) {
        if (this.actionHandler.handle(keycode)) this.update();

        if (this.isPlaying()) {

            final Game game = this.getCurrentGame();
            if (game.getSettings().isAutoEndTurn()) {
                if (!game.hasRemainingAction()) {

                    NextTurnMenu menu = new NextTurnMenu();
                    MenuManager.getInstance().addMenu(menu);

                    menu.fadeIn();
                    game.nextTurn();

                    this.setGameState(GameState.PLAYING_SELECTING_NEXT_TURN_APPROVAL);

                }
            }
        }
    }

    public void update() {
        Renderer.getInstance().render(this.gameState, this.currentGame);
    }

    public synchronized boolean isPlaying() {
        return this.currentGame != null;
    }

    /**
     * @return L'instance du jeu
     */
    public synchronized Game getCurrentGame() {
        return this.currentGame;
    }

    public synchronized void newGame(MapMetadata mapMetadata, Settings settings) {

        this.currentGame = new Game(mapMetadata, settings);
        this.currentGame.startGame();
    }

    public synchronized void endGame() {
        this.currentGame = null;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void end() {
        this.keystrokeListener.stop();
        this.gameLoop.stop();
        Logger.closeAll();
    }

    private void registerDefaultMenus() {

        MenuManager.getInstance().addMenu(new MainMenu());
        MenuManager.getInstance().addMenu(new MapSelectionMenu());
        MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(false);

    }

}

