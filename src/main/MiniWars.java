/**
 * package principal
 */
package main;

import main.controller.KeystrokeHandler;
import main.controller.KeystrokeListener;
import main.map.Game;
import main.map.MapMetadata;
import main.map.MapSelector;
import main.menu.Menu;
import main.menu.MenuManager;
import main.menu.model.MainMenu;
import main.menu.model.MapSelectionMenu;
import main.render.Renderer;
import main.weather.Weather;
import ressources.Config;
import ressources.MapParsing;

public class MiniWars {

    private final MapSelector mapSelector;
    private final MenuManager menuManager;
    private final Renderer renderer;
    private final KeystrokeListener keystrokeListener;
    private final KeystrokeHandler keystrokeHandler;
    private Game currentGame;
    private GameState gameState;

    public MiniWars() {

        this.currentGame = null;
        this.gameState = GameState.MENU_TITLE_SCREEN;

        this.mapSelector = new MapSelector(MapParsing.listAvailableMaps());
        this.keystrokeListener = new KeystrokeListener();
        this.keystrokeHandler = new KeystrokeHandler(this);

        this.keystrokeListener.setHandler((keycode) -> {
            boolean updateDisplay = this.keystrokeHandler.handle(keycode);
            if (updateDisplay) {
                this.update();
            }
        });
        this.keystrokeListener.start();

        this.menuManager = new MenuManager();
        this.registerDefaultMenus();

        this.renderer = new Renderer(this.menuManager);
        this.update();

    }

    public boolean isOver() {
        return this.gameState == GameState.ENDIND_SCREEN;
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
    }

    private void registerDefaultMenus() {
        this.menuManager.addMenu(Menu.Model.MAIN_MENU, new MainMenu((int) (Math.random() * Config.MAIN_MENU_BACKGROUND_VARIATION_COUNT), Weather.CLEAR));
        this.menuManager.addMenu(Menu.Model.MAP_SELECTION_MENU, new MapSelectionMenu(this.mapSelector));
        this.menuManager.getMenu(Menu.Model.MAP_SELECTION_MENU).setVisible(false);
    }

}

