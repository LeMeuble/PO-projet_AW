package main.render;

import librairies.StdDraw;
import main.GameState;
import main.map.Case;
import main.map.GameMap;
import main.map.MapSelector;
import main.menu.AnimatedMenu;
import main.menu.MainMenu;
import main.menu.MapSelectionMenu;
import main.menu.Menu;
import main.terrain.AnimatedTerrain;
import main.terrain.Property;
import main.terrain.Terrain;
import main.weather.Weather;
import ressources.Config;
import ressources.DisplayUtil;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Renderer {

    private AnimatedMenu titleScreen;
    private Menu mapSelectionMenu;

    private AnimationClock mainMenuClockSync;
    private AnimationClock terrainClockSync;

    public Renderer() {

        this.titleScreen = null;
        this.mapSelectionMenu = null;

        this.mainMenuClockSync = new AnimationClock(Config.MAIN_MENU_ANIMATION_FRAME_COUNT, Config.MAIN_MENU_ANIMATION_FRAME_DURATION);
        this.terrainClockSync = new AnimationClock(Config.MAP_ANIMATION_FRAME_COUNT, Config.MAP_ANIMATION_FRAME_DURATION, true);

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

    public void render(GameState gameState, GameMap gameMap, MapSelector mapSelector) {

        switch (gameState) {
            case MENU_TITLE_SCREEN:
                this.renderMenuTitleScreen();
                break;
            case MENU_MAP_SELECTION:
                this.renderMenuMapSelection(mapSelector);
                break;
            case PLAYING_SELECTING:
                this.renderMap(gameMap);
                break;
        }

    }

    private void renderMenuTitleScreen() {

        if (this.titleScreen == null) {

            Weather randomThemeWeather = Weather.random();
            int randomThemeId = (int) (Math.random() * Config.MAIN_MENU_BACKGROUND_VARIATION_COUNT);
            this.titleScreen = new MainMenu(randomThemeId, randomThemeWeather);

        }

        if (this.mainMenuClockSync.needsRefresh()) {
            this.titleScreen.render(this.mainMenuClockSync.getFrame());
            this.mainMenuClockSync.nextFrame();
            StdDraw.show();
        }

    }

    private void renderMenuMapSelection(MapSelector mapSelector) {

        if (this.mapSelectionMenu == null) this.mapSelectionMenu = new MapSelectionMenu(mapSelector);
        this.mapSelectionMenu.render();
        StdDraw.show();

    }

    private void renderMap(GameMap gameMap) {

        if (gameMap == null) return;

        boolean needsRefresh = false;
        List<Case> unitRenderQueue = new LinkedList<>();

        if (this.terrainClockSync.needsRefresh()) {

            for (int i = 0; i < gameMap.getWidth(); i++) {
                for (int j = gameMap.getHeight() - 1; j >= 0; j--) {

                    Case c = gameMap.getGrid().getCase(i, j);
                    Terrain terrain = c.getTerrain();

                    if (terrain instanceof AnimatedTerrain) {
                        DisplayUtil.drawTerrainInCase(i, j, ((AnimatedTerrain) terrain).getFile(gameMap.getWeather(), false, this.terrainClockSync.getFrame()));
                    } else if (terrain instanceof Property) {
                        DisplayUtil.drawPropertyInCase(i, j, terrain.getFile(gameMap.getWeather(), false));
                    } else {
                        DisplayUtil.drawTerrainInCase(i, j, terrain.getFile(gameMap.getWeather(), false));
                    }

                    if(c.hasUnit()) unitRenderQueue.add(c);

                }

            }

            this.terrainClockSync.nextFrame();
            needsRefresh = true;

        }

        if (needsRefresh) {
            System.out.println("Rendered!");
            StdDraw.show();
        }

    }

}
