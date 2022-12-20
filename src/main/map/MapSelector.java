package main.map;

import main.menu.MapSelectionMenu;
import ressources.MapParsing;

import java.util.List;

public class MapSelector {

    private List<MapMetadata> maps;
    private int selected;

    public MapSelector() {
        this.maps = MapParsing.listAvailableMaps();
        this.selected = this.maps.size() == 0 ? -1 : 0;
    }

    public void update() {
        this.maps = MapParsing.listAvailableMaps();
    }

    public List<MapMetadata> getMaps() {
        return maps;
    }

    public MapMetadata getSelectedMap() {
        return this.selected != -1 ? maps.get(this.selected) : null;
    }

    public void next() {
        if(this.selected != -1) {
            this.selected = (this.selected + 1) % this.maps.size();
        }
    }

    public void previous() {
        if(this.selected != -1) {
            this.selected = (this.selected - 1 + this.maps.size()) % this.maps.size();
        }
    }

}
