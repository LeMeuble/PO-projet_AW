package main.menu.model;

import librairies.StdDraw;
import main.map.MapMetadata;
import main.menu.MenuModel;
import main.menu.SelectionMenu;
import main.util.OptionSelector;
import ressources.Config;

import java.awt.*;

public class MapSelectionMenu extends SelectionMenu<MapMetadata> {

    public MapSelectionMenu(OptionSelector<MapMetadata> mapSelector) {
        super(0, 0, 0, 0, 10, mapSelector);
    }

    @Override
    public void render() {

        if(this.getSelectedOption() != null) {
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(Config.WIDTH / 2, Config.HEIGHT / 2, this.getSelectedOption().getName());
        } else {
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(Config.WIDTH / 2, Config.HEIGHT / 2, "No map found");
        }

    }

    public MenuModel getModel() {
        return MenuModel.MAP_SELECTION_MENU;
    }

}
