package main.menu;

import main.util.OptionSelector;
import ressources.Config;
import ressources.DisplayUtil;
import ressources.PathUtil;

/**
 * Implementation abstraite de {@link SelectionMenu} pour le cas des menus de selection d'action
 * ou d'achat d'unite.
 * Ce menu permet de selectionner une action parmi une liste d'actions.
 * Exemple : Selection d'action d'unite (deplacement, attaque, ...)
 *
 * @param <T> Le type d'objet a selectionner
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 * @see OptionSelector<T>
 * @see SelectionMenu<T>
 */
public abstract class ActionMenu<T> extends SelectionMenu<T> {

    public static final int PRIORITY = 1;

    private final boolean showUnavailable;

    /**
     * Constructeur d'un menu d'action
     *
     * @param optionSelector Selectionneur d'option
     *
     * @see OptionSelector<T>
     */
    public ActionMenu(OptionSelector<T> optionSelector, boolean showUnavailable) {
        super(PRIORITY, optionSelector);
        this.showUnavailable = showUnavailable;
    }


    /**
     * Render le menu d'action.
     * Cette methode ne permet que de rendre le fond du menu.
     * Les actions sont rendues par la method {@see ActionMenu#render()}
     * des classes filles.
     *
     * @see ActionMenu#render()
     */
    @Override
    public void render() {

        double x = Config.MENU_ACTION_WIDTH / 2.0d + Config.MENU_ACTION_MARGIN;
        double y = (Config.HEIGHT - Config.MENU_ACTION_MARGIN) - Config.MENU_ACTION_TOP_HEIGHT / 2.0d;

        int count = this.showUnavailable ? this.getOptions().size() : this.getAvailableOptions().size();

        DisplayUtil.drawPicture(x, y, PathUtil.getActionGuiPath("top"), Config.MENU_ACTION_WIDTH, Config.MENU_ACTION_TOP_HEIGHT);

        y -= Config.MENU_ACTION_TOP_HEIGHT / 2.0d + Config.MENU_ACTION_MIDDLE_HEIGHT / 2.0d;
        for (int i = 0; i < count; i++) {

            DisplayUtil.drawPicture(x, y, PathUtil.getActionGuiPath("middle"), Config.MENU_ACTION_WIDTH, Config.MENU_ACTION_MIDDLE_HEIGHT);
            y -= Config.MENU_ACTION_MIDDLE_HEIGHT;

        }
        y -= Config.MENU_ACTION_BOTTOM_HEIGHT / 2.0d - Config.MENU_ACTION_MIDDLE_HEIGHT / 2.0d;

        DisplayUtil.drawPicture(x, y, PathUtil.getActionGuiPath("bottom"), Config.MENU_ACTION_WIDTH, Config.MENU_ACTION_BOTTOM_HEIGHT);

    }

}
