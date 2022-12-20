package main.render;

import librairies.StdDraw;
import main.GameState;
import main.Jeu;
import main.map.GameMap;
import main.map.MapSelector;
import main.menu.MainMenu;
import main.menu.MapSelectionMenu;
import main.menu.Menu;
import ressources.Config;
import ressources.DisplayUtil;

public class Renderer {

    private Menu titleScreen;
    private Menu mapSelectionMenu;

    public Renderer() {

        this.titleScreen = null;
        this.mapSelectionMenu = null;

        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(Config.WIDTH, Config.HEIGHT);
        StdDraw.setXscale(0, Config.WIDTH);
        StdDraw.setYscale(0, Config.HEIGHT);
        StdDraw.setTitle(Config.TITLE);
        StdDraw.setIcon(Config.ICON_PATH);

    }


    public void render(GameState gameState, GameMap gameMap, MapSelector mapSelector) {

        StdDraw.clear();
        switch (gameState) {
            case MENU_TITLE_SCREEN:
                this.renderMenuTitleScreen();
                break;
            case MENU_MAP_SELECTION:
                this.renderMenuMapSelection(mapSelector);
                break;
            case PLAYING_SELECTING:
                this.renderPlaying(gameMap);
                break;
        }
        StdDraw.show();

    }

    private void renderMenuTitleScreen() {

        if(this.titleScreen == null) this.titleScreen = new MainMenu();

        if(titleScreen.needsRefresh()) titleScreen.render();

    }

    private void renderMenuMapSelection(MapSelector mapSelector) {

        if(this.mapSelectionMenu == null) this.mapSelectionMenu = new MapSelectionMenu(mapSelector);

        mapSelectionMenu.render();

    }

    private void renderPlaying(GameMap gameMap) {

        if(gameMap == null) return;

        for (int i = 0; i < gameMap.getWidth(); i++) {
            for (int j = 0; j < gameMap.getHeight(); j++) {
                DisplayUtil.drawTerrainInCase(i, j, gameMap.getGrid().getCase(i, j).getTerrain().getFile());
            }
        }

    }

}
