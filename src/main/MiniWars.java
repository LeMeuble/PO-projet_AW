/**
 * package principal
 */
package main;

import main.control.KeystrokeListener;
import main.game.ActionHandler;
import main.game.Game;
import main.game.GameLoop;
import main.game.GameState;
import main.map.MapMetadata;
import main.menu.MenuManager;
import main.menu.MenuModel;
import main.menu.model.MainMenu;
import main.menu.model.MapSelectionMenu;
import main.parser.MapParser;
import main.render.Renderer;
import main.unit.UnitType;
import main.util.OptionSelector;
import main.weather.Weather;
import ressources.Config;

/**
 * Classe principale du jeu Cette classe est le point d'entrée du programme Elle initialise les composants du jeu et
 * lance la boucle principale
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 */
public class MiniWars {

    private final OptionSelector<MapMetadata> mapSelector;
    private final MenuManager menuManager;
    private final Renderer renderer;
    private final KeystrokeListener keystrokeListener;
    private final ActionHandler actionHandler;
    private final GameLoop gameLoop;
    private Game currentGame;
    private GameState gameState;


    /**
     * Constructeur de MiniWars
     * Initialise les instances gerant les entrees clavier, la boucle du jeu
     * Ouvre le menu de selection des cartes
     */
    public MiniWars() {

        this.currentGame = null;
        this.gameState = GameState.MENU_TITLE_SCREEN;

        this.mapSelector = new OptionSelector<>(MapParser.listAvailableMaps());
        this.menuManager = new MenuManager();
        this.renderer = new Renderer(this.menuManager);

        this.keystrokeListener = new KeystrokeListener();
        this.keystrokeListener.setHandler(this::handleKey);

        this.actionHandler = new ActionHandler(this);

        this.gameLoop = new GameLoop();
        this.gameLoop.setHandler(this::update);

        this.registerDefaultMenus();
        this.keystrokeListener.start();
        this.gameLoop.start();
        this.update();

    }

    /**
     * @return true si le jeu est termine, false sinon
     */
    public boolean isOver() {
        return this.gameState == null;
    }

    public void handleKey(KeystrokeListener.KeyCodes keycode) {
        if (this.actionHandler.handle(keycode)) this.update();
    }

    public synchronized void update() {

        this.renderer.render(this.gameState, this.currentGame);

    }

    public boolean isPlaying() {
        return this.currentGame != null;
    }

    /**
     * @return L'instance du jeu
     */
    public Game getCurrentGame() {
        return this.currentGame;
    }

    public void newGame(MapMetadata mapMetadata) {
        this.currentGame = new Game(mapMetadata);
        this.gameState = GameState.PLAYING_SELECTING;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Renderer getRenderer() {
        return this.renderer;
    }

    public OptionSelector<MapMetadata> getMapSelector() {
        return this.mapSelector;
    }

    public MenuManager getMenuManager() {
        return this.menuManager;
    }

    public void end() {
        this.keystrokeListener.stop();
        this.gameLoop.stop();
    }

    private void registerDefaultMenus() {

        int mainMenuVariant = (int) (Math.random() * Config.MAIN_MENU_BACKGROUND_VARIATION_COUNT);

        // TODO: Weather-based background
        this.menuManager.addMenu(MenuModel.MAIN_MENU, new MainMenu(mainMenuVariant, Weather.CLEAR));
        this.menuManager.addMenu(MenuModel.MAP_SELECTION_MENU, new MapSelectionMenu(this.mapSelector));
        this.menuManager.getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(false);
    }

}

