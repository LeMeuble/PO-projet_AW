package main.menu;

import librairies.StdDraw;
import main.map.MapMetadata;
import main.map.MapSelector;
import ressources.Config;
import ressources.MapParsing;

public class MapSelectionMenu extends Menu {

    private final MapSelector mapSelector;

    public MapSelectionMenu(MapSelector mapSelector) {
        super(0, 0, 0, 0);
        this.mapSelector = mapSelector;
    }

    @Override
    public void render() {

        if(this.mapSelector.getSelectedMap() != null) {
            StdDraw.text(Config.WIDTH / 2, Config.HEIGHT / 2, this.mapSelector.getSelectedMap().getName());
        } else {
            StdDraw.text(Config.WIDTH / 2, Config.HEIGHT / 2, "No map found");
        }

    }

    @Override
    public boolean needsRefresh() {
        return false;
    }

}
