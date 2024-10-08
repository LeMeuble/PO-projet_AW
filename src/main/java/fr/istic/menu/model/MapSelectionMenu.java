package fr.istic.menu.model;

import fr.istic.StdDraw;
import fr.istic.game.Player;
import fr.istic.game.Settings;
import fr.istic.map.MapMetadata;
import fr.istic.menu.MenuModel;
import fr.istic.menu.SelectionMenu;
import fr.istic.parser.MapParser;
import fr.istic.util.OptionSelector;
import fr.istic.Config;
import fr.istic.DisplayUtil;
import fr.istic.PathUtil;

import java.awt.*;

/**
 * Classe representant un fr.istic.menu de selection de carte
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class MapSelectionMenu extends SelectionMenu<MapMetadata> {

    public static final int PRIORITY = 9;
    public static final double TOP_MARGIN = 0.05d;

    public enum Field {

        MAP,
        WEATHER,
        FOG,
        AUTO_SKIP_TURN

    }

    private final Settings settings;
    private Field field;

    /**
     * Constructeur du fr.istic.menu de selection de carte
     */
    public MapSelectionMenu() {
        super(PRIORITY, new OptionSelector<>(MapParser.listAvailableMaps()));
        this.settings = new Settings();
        this.field = Field.MAP;
    }

    public Settings getSettings() {
        return this.settings;
    }

    /**
     * Methode gerant l'affichage du fr.istic.menu
     */
    @Override
    public void render() {

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(Config.WIDTH / 2d, Config.HEIGHT / 2d, Config.WIDTH / 2d, Config.HEIGHT / 2d);

        MapMetadata map = this.getSelectedValue();

        double x = Config.WIDTH / 2;
        double y = Config.HEIGHT - (TOP_MARGIN * Config.HEIGHT + Config.HEIGHT / (1.8d * 2));

        DisplayUtil.drawPicture(x, y, PathUtil.getGlobalGuiPath("map_icon_background"), 896 / 2.05d, 1088 / 2.05d);

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(Config.FONT_32);
        // Si une carte a ete selectionnee
        if (map != null) {

            DisplayUtil.drawPicture(x, y - 12, map.getIcon(), 896 / 2.05d - 46, 1088 / 2.05d - 70);
            StdDraw.text(x - 150, y + 234, map.getSize());

            double playerIconX = x + 180;

            // Affiche les personnages, representants le nombre de joueurs jouables sur cette carte
            for (int i = map.getPlayerCount(); i >= 1; i--) {

                Player.Type player = Player.Type.fromValue(i);

                DisplayUtil.drawPicture(playerIconX, y + 242, PathUtil.getTeamIcon(player), 32, 48);
                playerIconX -= 16;

            }

            y -= Config.HEIGHT / (1.8d * 2) + 0.05 * Config.HEIGHT + Config.HEIGHT / (1.8d * 4);

            String name = map.getName().substring(0, Math.min(map.getName().length(), 14));
            if (map.getName().length() != name.length()) {
                name += "...";
            }

            StdDraw.setFont(Config.FONT_26);
            DisplayUtil.drawPicture(x, y, PathUtil.getGlobalGuiPath("map_info_background"), 896 / 2.05d, 552 / 2.05d);

            StdDraw.textLeft(x - 190, y + 106, name);

            String description = map.getDescription().substring(0, Math.min(map.getDescription().length(), 100));

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

            y -= 5;

            String fogOfWar = this.settings.isFogOfWar() ? "checked" : "notchecked";
            String autoEndTurn = this.settings.isAutoEndTurn() ? "checked" : "notchecked";
            String weather = this.settings.getWeatherMode().getName();

            StdDraw.text(Config.WIDTH / 2d, y, weather);
            StdDraw.textLeft(x - 100, y - 40, "Brouillard de guerre");
            StdDraw.textLeft(x - 100, y - 80, "Fin de tour automatique");

            DisplayUtil.drawPicture(x - 150, y, PathUtil.getGlobalGuiPath("selector_left"), 32, 32);
            DisplayUtil.drawPicture(x + 150, y, PathUtil.getGlobalGuiPath("selector_right"), 32, 32);
            DisplayUtil.drawPicture(x - 150, y - 40, PathUtil.getGlobalGuiPath(fogOfWar), 32, 32);
            DisplayUtil.drawPicture(x - 150, y - 80, PathUtil.getGlobalGuiPath(autoEndTurn), 32, 32);

            boolean rightLeft = this.field == Field.MAP || this.field == Field.WEATHER;
            boolean space = this.field == Field.FOG || this.field == Field.AUTO_SKIP_TURN;

            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setFont(Config.FONT_18);

            if (this.field == Field.MAP) {

                DisplayUtil.drawPicture(Config.WIDTH - 44, Config.HEIGHT / 2 + 32, PathUtil.getGlobalGuiPath("left_arrow"), 64, 64);
                DisplayUtil.drawPicture(44, Config.HEIGHT / 2 + 32, PathUtil.getGlobalGuiPath("right_arrow"), 64, 64);

                StdDraw.textRight(Config.WIDTH - 78, y - 55, "Carte");

            }
            else {

                double fingerY = y;

                if (this.field == Field.FOG) fingerY = y - 40;
                if (this.field == Field.AUTO_SKIP_TURN) fingerY = y - 80;

                DisplayUtil.drawPicture(x - Config.WIDTH / 3d, fingerY, PathUtil.getGlobalGuiPath("finger"), 48, 48);

            }

            DisplayUtil.drawPicture(Config.WIDTH - 48, y - 5, PathUtil.getKeytipPath("enter"), 24, 24);
            StdDraw.textRight(Config.WIDTH - 78, y - 5, "D\u00e9marrer");

            if (space) {
                DisplayUtil.drawPicture(Config.WIDTH - 48, y - 55, PathUtil.getKeytipPath("space"), 48, 24);
                StdDraw.textRight(Config.WIDTH - 78, y - 55, "Changer");
            }
            else if (rightLeft) {
                DisplayUtil.drawPicture(Config.WIDTH - 48, y - 55, PathUtil.getKeytipPath("left_right"), 48, 24);
                if (this.field != Field.MAP) StdDraw.textRight(Config.WIDTH - 78, y - 55, "S\u00e9l\u00e9ct.");
            }

            DisplayUtil.drawPicture(Config.WIDTH - 48, y - 105, PathUtil.getKeytipPath("up_down"), 24, 48);
            StdDraw.textRight(Config.WIDTH - 78, y - 105, "Options");

        }
        else {
            StdDraw.text(x, y, "No fr.istic.map available");
        }

    }

    @Override
    public void next() {
        if (this.field == Field.MAP) {
            super.next();
        }
        else if (this.field == Field.WEATHER) {
            this.settings.nextWeatherMode();
        }
    }


    @Override
    public void previous() {
        if (this.field == Field.MAP) {
            super.previous();
        }
        else if (this.field == Field.WEATHER) {
            this.settings.previousWeatherMode();
        }
    }

    public void toggleCurrentField() {

        if (this.field == Field.FOG)
            this.settings.toggleFogOfWar();
        else if (this.field == Field.AUTO_SKIP_TURN)
            this.settings.toggleAutoEndTurn();

    }


    public void nextField() {
        this.field = Field.values()[(this.field.ordinal() + 1) % Field.values().length];
    }


    public void previousField() {
        this.field = Field.values()[(this.field.ordinal() - 1 + Field.values().length) % Field.values().length];
    }

    public MenuModel getModel() {
        return MenuModel.MAP_SELECTION_MENU;
    }

}
