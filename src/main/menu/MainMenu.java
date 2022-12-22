package main.menu;

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
                Config.HEIGHT
        );
        this.id = id;
        this.weather = Weather.CLEAR;
    }


    @Override
    public void render() {

        this.render(0);

    }

    @Override
    public void render(int frame) {

        final String backgroundPath = PathUtil.getBackgroundPath(this.weather, this.id);
        final String title = PathUtil.getUiComponentPath("title_screen_" + frame);

        DisplayUtil.drawPicture(super.getX(), super.getY(), backgroundPath, super.getWidth(), super.getHeight());
        DisplayUtil.drawPicture(super.getX(), 170d, title);

    }

}
