package main.menu.model;

import main.animation.AnimationClock;
import main.menu.AnimatedMenu;
import main.menu.MenuModel;
import main.weather.Weather;
import ressources.Config;
import ressources.DisplayUtil;
import ressources.PathUtil;

public class MainMenu extends AnimatedMenu {

    private static final int PRIORITY = 10;

    private final int id;
    private final Weather weather;


    public MainMenu() {
        super(
                Config.WIDTH / 2,
                Config.HEIGHT / 2,
                Config.WIDTH,
                Config.HEIGHT,
                MainMenu.PRIORITY,
                new AnimationClock(
                        Config.MAIN_MENU_ANIMATION_FRAME_COUNT,
                        Config.MAIN_MENU_ANIMATION_FRAME_DURATION
                )
        );
        this.id = (int) (Math.random() * Config.MAIN_MENU_BACKGROUND_VARIATION_COUNT);
        this.weather = Weather.CLEAR; //Weather.random();
    }


    @Override
    public void render() {

        final int frame = this.getFrame();

        final String backgroundPath = PathUtil.getBackgroundPath(this.weather, this.id);
        final String title = PathUtil.getUiComponentPath("title_screen_" + frame);

        DisplayUtil.drawPicture(super.getX(), super.getY(), backgroundPath, super.getWidth(), super.getHeight());
        DisplayUtil.drawPicture(super.getX(), 0.18d * Config.HEIGHT, title);

    }

    public MenuModel getModel() {
        return MenuModel.MAIN_MENU;
    }

}
