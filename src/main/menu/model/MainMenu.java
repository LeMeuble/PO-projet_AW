package main.menu.model;

import main.menu.AnimatedMenu;
import main.render.AnimationClock;
import main.weather.Weather;
import ressources.Config;
import ressources.DisplayUtil;
import ressources.PathUtil;

public class MainMenu extends AnimatedMenu {

    private final int id;
    private final Weather weather;


    public MainMenu(int id, Weather weather) {
        super(
                Config.WIDTH / 2,
                Config.HEIGHT / 2,
                Config.WIDTH,
                Config.HEIGHT,
                new AnimationClock(Config.MAIN_MENU_ANIMATION_FRAME_COUNT, Config.MAIN_MENU_ANIMATION_FRAME_DURATION)
        );
        this.id = id;
        this.weather = weather;
    }


    @Override
    public void render() {

        final int frame = this.getFrame();

        final String backgroundPath = PathUtil.getBackgroundPath(this.weather, this.id);
        final String title = PathUtil.getUiComponentPath("title_screen_" + frame);

        DisplayUtil.drawPicture(super.getX(), super.getY(), backgroundPath, super.getWidth(), super.getHeight());
        DisplayUtil.drawPicture(super.getX(), 170d, title);

    }

    public Model getModel() {
        return Model.MAIN_MENU;
    }

}
