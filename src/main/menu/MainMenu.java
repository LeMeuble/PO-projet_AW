package main.menu;

import ressources.Config;
import ressources.DisplayUtil;

public class MainMenu extends AnimatedMenu {

    public MainMenu() {
        super(Config.WIDTH / 2, Config.HEIGHT / 2, Config.WIDTH, Config.HEIGHT, 2, 500);
    }


    @Override
    public void render() {

        DisplayUtil.drawPicture(super.getX(), super.getY(), "pictures/ui/backgrounds/background_0.png", super.getWidth(), super.getHeight());
        DisplayUtil.drawPicture(super.getX(), 170d, "pictures/ui/title_screen_" + this.getFrame() + ".png");

        this.nextFrame();

    }

}
