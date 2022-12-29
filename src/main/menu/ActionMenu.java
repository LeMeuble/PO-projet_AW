package main.menu;

import main.util.OptionSelector;
import ressources.Config;
import ressources.DisplayUtil;
import ressources.PathUtil;

public abstract class ActionMenu<T> extends SelectionMenu<T> {

    public ActionMenu(OptionSelector<T> optionSelector) {
        super(0, 0, 0, 0, optionSelector);
    }

    @Override
    public void render() {

        double x = Config.MENU_ACTION_WIDTH / 2.0d + Config.MENU_ACTION_MARGIN;
        double y = (Config.HEIGHT - Config.MENU_ACTION_MARGIN) - Config.MENU_ACTION_TOP_HEIGHT / 2.0d;

        int count = this.getOptions().size();

        DisplayUtil.drawPicture(x, y, PathUtil.getGuiPath("top"), Config.MENU_ACTION_WIDTH, Config.MENU_ACTION_TOP_HEIGHT);

        y -= Config.MENU_ACTION_TOP_HEIGHT / 2.0d + Config.MENU_ACTION_MIDDLE_HEIGHT / 2.0d;
        for (int i = 0; i < count; i++) {

            DisplayUtil.drawPicture(x, y, PathUtil.getGuiPath("middle"), Config.MENU_ACTION_WIDTH, Config.MENU_ACTION_MIDDLE_HEIGHT);
            y -= Config.MENU_ACTION_MIDDLE_HEIGHT;

        }
        y -= Config.MENU_ACTION_BOTTOM_HEIGHT / 2.0d - Config.MENU_ACTION_MIDDLE_HEIGHT / 2.0d;

        DisplayUtil.drawPicture(x, y, PathUtil.getGuiPath("bottom"), Config.MENU_ACTION_WIDTH, Config.MENU_ACTION_BOTTOM_HEIGHT);

    }

}
