package fr.istic.menu.model;

import fr.istic.animation.AnimationClock;
import fr.istic.menu.AnimatedMenu;
import fr.istic.menu.MenuModel;
import fr.istic.weather.Weather;
import fr.istic.Config;
import fr.istic.DisplayUtil;
import fr.istic.PathUtil;

/**
 * Classe representant le fr.istic.menu principal (ecran d'accueil)
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class MainMenu extends AnimatedMenu {

    public static final double WIDTH = Config.WIDTH;
    public static final double HEIGHT = Config.HEIGHT;
    private static final int PRIORITY = 10;
    private static final double CENTER_X = Config.WIDTH / 2;
    private static final double CENTER_Y = Config.HEIGHT / 2;

    private final int id;
    private final Weather weather;

    /**
     * Constructeur du fr.istic.menu principal
     */
    public MainMenu() {
        super(PRIORITY, new AnimationClock(Config.MAIN_MENU_ANIMATION_FRAME_COUNT, Config.MAIN_MENU_ANIMATION_FRAME_DURATION));
        this.id = (int) (Math.random() * Config.MAIN_MENU_BACKGROUND_VARIATION_COUNT);
        this.weather = Weather.CLEAR; //Weather.random();
    }


    /**
     * Methode gerant l'affichage du fr.istic.menu principal
     */
    @Override
    public void render() {

        final int frame = this.getFrame();

        final String backgroundPath = PathUtil.getBackgroundPath(this.weather, this.id);
        final String title = PathUtil.getUiComponentPath("title_screen_" + frame + ".png");

        // Affiche l'immage du fr.istic.menu, avec le titre du jeu
        DisplayUtil.drawPicture(CENTER_X, CENTER_Y, backgroundPath, WIDTH, HEIGHT);
        DisplayUtil.drawPicture(CENTER_X, 0.18d * Config.HEIGHT, title);

    }

    public MenuModel getModel() {
        return MenuModel.MAIN_MENU;
    }

}
