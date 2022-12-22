/**
 * package principal
 */
package main;

import main.controller.KeystrokeHandler;
import main.controller.KeystrokeListener;
import main.map.GameMap;
import main.map.MapMetadata;
import main.map.MapSelector;
import main.render.Renderer;

public class Jeu {

    private GameState gameState;
    private GameMap gameMap;

    private final MapSelector mapSelector;
    private final Renderer renderer;
    private final KeystrokeListener keystrokeListener;
    private final KeystrokeHandler keystrokeHandler;

    public Jeu() throws Exception {

        this.gameState = GameState.MENU_TITLE_SCREEN;
        this.gameMap = null;

        this.mapSelector = new MapSelector();
        this.keystrokeListener = new KeystrokeListener();
        this.keystrokeHandler = new KeystrokeHandler(this);

        this.keystrokeListener.setHandler((keycode) -> {
            boolean updateDisplay = this.keystrokeHandler.handle(keycode);
            if(updateDisplay) this.update();
        });
        this.keystrokeListener.start();

        this.renderer = new Renderer();
        this.update();

    }

    public boolean isOver() {
        return this.gameState == GameState.ENDIND_SCREEN;
    }


    public void update() {

        this.renderer.render(this.gameState, this.gameMap, this.mapSelector);

    }

    public boolean isPlaying() {
        return this.gameMap != null;
    }

    public GameMap getGameMap() {
        return this.gameMap;
    }

    public void newGame(MapMetadata mapMetadata) {
        this.gameMap = new GameMap(this.mapSelector.getSelectedMap());
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

    public void end() {
        this.keystrokeListener.stop();
    }

}

