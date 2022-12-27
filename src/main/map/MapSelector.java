package main.map;

import ressources.MapParsing;

import java.util.List;

public class MapSelector {

    private List<MapMetadata> maps;
    private int selected;

    public MapSelector(List<MapMetadata> maps) {
        this.maps = maps;
        this.selected = this.maps.isEmpty() ? -1 : 0;
    }

    public List<MapMetadata> getMaps() {
        return this.maps;
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
