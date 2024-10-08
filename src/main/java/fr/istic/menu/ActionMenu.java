package fr.istic.menu;

import fr.istic.util.OptionSelector;
import fr.istic.Config;
import fr.istic.DisplayUtil;
import fr.istic.PathUtil;

import java.util.List;

/**
 * Implementation abstraite de {@link SelectionMenu} pour le cas des menus de selection d'action
 * ou d'achat d'unite.
 * Ce fr.istic.menu permet de selectionner une action parmi une liste d'actions.
 * Exemple : Selection d'action d'unite (deplacement, attaque, ...)
 *
 * @param <T> Le type d'objet a selectionner
 *
 * @author PandaLunatique
 * @author LeMeuble
 * @see OptionSelector<T>
 * @see SelectionMenu<T>
 */
public abstract class ActionMenu<T> extends SelectionMenu<T> {

    public static final int PRIORITY = 1;

    public interface Text {

        String getText();

    }

    private final boolean showUnavailable;

    /**
     * Constructeur d'un fr.istic.menu d'action
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
     * Render le fr.istic.menu d'action.
     * Cette methode ne permet que de rendre le fond du fr.istic.menu.
     * Les actions sont rendues par la method {@see ActionMenu#fr.istic.render()}
     * des classes filles.
     *
     * @see ActionMenu#render()
     */
    @Override
    public void render() {

        int width = Config.MENU_ACTION_WIDTH;

        List<T> values = this.showUnavailable ? this.getValues() : this.getAvailableValues();
        boolean isText = values.size() > 0 && values.get(0) instanceof Text;

        if (isText) {

            int maxWidth = 0;
            for (T value : values) {
                int textWidth = DisplayUtil.getTextWidth(Config.FONT_20, ((Text) value).getText());
                if (textWidth > maxWidth) {
                    maxWidth = textWidth;
                }
            }
            width = maxWidth + 72;
        }

        double x = width / 2.0d + Config.MENU_ACTION_MARGIN;
        double y = (Config.HEIGHT - Config.MENU_ACTION_MARGIN) - Config.MENU_ACTION_TOP_HEIGHT / 2.0d;

        DisplayUtil.drawPicture(x, y, PathUtil.getActionGuiPath("top"), width, Config.MENU_ACTION_TOP_HEIGHT);

        y -= Config.MENU_ACTION_TOP_HEIGHT / 2.0d + Config.MENU_ACTION_MIDDLE_HEIGHT / 2.0d;
        for (T value : values) {

            DisplayUtil.drawPicture(x, y, PathUtil.getActionGuiPath("middle"), width, Config.MENU_ACTION_MIDDLE_HEIGHT);
            if (value == this.getSelectedValue()) {
                DisplayUtil.drawPicture(x - width / 2, y + 2, PathUtil.getUiComponentPath("selector.png"), 32, 32);
            }
            y -= Config.MENU_ACTION_MIDDLE_HEIGHT;

        }
        y -= Config.MENU_ACTION_BOTTOM_HEIGHT / 2.0d - Config.MENU_ACTION_MIDDLE_HEIGHT / 2.0d;

        DisplayUtil.drawPicture(x, y, PathUtil.getActionGuiPath("bottom"), width, Config.MENU_ACTION_BOTTOM_HEIGHT);

    }

}
