/**
 * package principal
 */
package main;

import main.control.KeystrokeListener;
import main.game.ActionHandler;
import main.game.Game;
import main.game.GameState;
import main.map.MapMetadata;
import main.map.MapSelector;
import main.menu.Menu;
import main.menu.MenuManager;
import main.menu.model.MainMenu;
import main.menu.model.MapSelectionMenu;
import main.parser.MapParser;
import main.render.Renderer;
import main.weather.Weather;
import ressources.Config;

/**
 * Classe principale du jeu
 * Cette classe est le point d'entrée du programme
 * Elle initialise les composants du jeu et lance la boucle principale
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 */
public class MiniWars {

    private final MapSelector mapSelector;
    private final MenuManager menuManager;
    private final Renderer renderer;
    private final KeystrokeListener keystrokeListener;
    private final ActionHandler actionHandler;
    private final Thread gameLoop;
    private Game currentGame;
    private GameState gameState;

    public MiniWars() {

        this.currentGame = null;
        this.gameState = GameState.MENU_TITLE_SCREEN;

        this.mapSelector = new MapSelector(MapParser.listAvailableMaps());
        this.keystrokeListener = new KeystrokeListener();
        this.actionHandler = new ActionHandler(this);

        this.keystrokeListener.setHandler((keycode) -> {
            boolean updateDisplay = this.actionHandler.handle(keycode);
            if (updateDisplay) this.update();
        });
        this.keystrokeListener.start();

        this.menuManager = new MenuManager();
        this.registerDefaultMenus();

        this.renderer = new Renderer(this.menuManager);
        this.update();

        this.gameLoop = new Thread(() -> {
            synchronized (this) {
                while (!this.isOver()) {
                    this.update();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ignored) {
                    }
                }

                this.end();
            }
        });
        this.gameLoop.start();

    }

    public boolean isOver() {
        return this.gameState == GameState.PLAYING_ENDIND_SCREEN;
    }


    public void update() {

        this.renderer.render(this.gameState, this.currentGame);

    }

    public boolean isPlaying() {
        return this.currentGame != null;
    }

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

    public MapSelector getMapSelector() {
        return this.mapSelector;
    }

    public MenuManager getMenuManager() {
        return this.menuManager;
    }

    public void end() {
        this.keystrokeListener.stop();
        this.gameLoop.interrupt();
    }

    private void registerDefaultMenus() {

        int mainMenuVariant = (int) (Math.random() * Config.MAIN_MENU_BACKGROUND_VARIATION_COUNT);

        // TODO: Weather-based background
        this.menuManager.addMenu(Menu.Model.MAIN_MENU, new MainMenu(mainMenuVariant, Weather.CLEAR));
        this.menuManager.addMenu(Menu.Model.MAP_SELECTION_MENU, new MapSelectionMenu(this.mapSelector));
        this.menuManager.getMenu(Menu.Model.MAP_SELECTION_MENU).setVisible(false);
    }

}

