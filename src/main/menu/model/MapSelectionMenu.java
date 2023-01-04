package main.menu.model;

import librairies.StdDraw;
import main.map.MapMetadata;
import main.menu.MenuModel;
import main.menu.SelectionMenu;
import main.util.OptionSelector;
import ressources.Config;
import ressources.DisplayUtil;
import ressources.PathUtil;

import java.awt.*;

public class MapSelectionMenu extends SelectionMenu<MapMetadata> {

    public static final int PRIORITY = 10;

    public static final double TOP_MARGIN = 0.05d;

    public MapSelectionMenu(OptionSelector<MapMetadata> mapSelector) {
        super(PRIORITY, mapSelector);
    }

    @Override
    public void render() {

        MapMetadata map = this.getSelectedOption();

        double x = Config.WIDTH / 2;
        double y = Config.HEIGHT - (TOP_MARGIN * Config.HEIGHT + Config.HEIGHT / (1.8d * 2));

        DisplayUtil.drawPicture(x, y, PathUtil.getGlobalGuiPath("map_icon_background"), 896 / 2.05d, 1088 / 2.05d);

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(Config.FONT_32);
        if (map != null) {

            DisplayUtil.drawPicture(x, y - 12, map.getIcon(), 896 / 2.05d - 46, 1088 / 2.05d - 70);
            StdDraw.text(x - 150, y + 234, map.getSize());

            y -= Config.HEIGHT / (1.8d * 2) + 0.05 * Config.HEIGHT + Config.HEIGHT / (1.8d * 4);

            String name = map.getName().substring(0, Math.min(map.getName().length(), 14));
            if (map.getName().length() != name.length()) {
                name += "...";
            }

            StdDraw.setFont(Config.FONT_26);
            DisplayUtil.drawPicture(x, y, PathUtil.getGlobalGuiPath("map_info_background"), 896 / 2.05d, 552 / 2.05d);

            StdDraw.textLeft(x - 190, y + 106, name);

            String description = map.getDescription().substring(0, Math.min(map.getDescription().length(), 70));

            if (map.getDescription().length() != description.length()) {
                description += "...";
            }

            String[] words = description.replace("  ", " ").split(" ");
            String[] lines = new String[3];

            // Create lines with words without exceeding 30 characters
            int line = 0;
            for (int i = 0; i < words.length; i++) {

                if (lines[line] == null) {
                    lines[line] = words[i];
                }
                else {
                    lines[line] += " " + words[i];
                }

                if (lines[line].length() > 30) {
                    line++;
                }

            }

            StdDraw.setFont(Config.FONT_20);

            for (int i = 0; i < lines.length; i++) {
                if (lines[i] != null) {
                    StdDraw.textLeft(x - 190, y + 106 - 40 - (i) * 20, lines[i]);
                }
            }

            DisplayUtil.drawPicture(Config.WIDTH - 44, Config.HEIGHT / 2 + 32, PathUtil.getGlobalGuiPath("left_arrow"), 64, 64);
            DisplayUtil.drawPicture(44, Config.HEIGHT / 2 + 32, PathUtil.getGlobalGuiPath("right_arrow"), 64, 64);

        }
        else {
            StdDraw.text(x, y, "No map available");
        }

    }


    public MenuModel getModel() {
        return MenuModel.MAP_SELECTION_MENU;
    }

}
