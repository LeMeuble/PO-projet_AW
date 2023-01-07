package main.menu;

import main.util.OptionSelector;
import ressources.Config;
import ressources.DisplayUtil;
import ressources.PathUtil;

import javax.swing.event.ListDataEvent;
import java.util.List;

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

    public interface Text {
        String getText();
    }

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

        int width = Config.MENU_ACTION_WIDTH;

        List<T> options = this.showUnavailable ? this.getOptions() : this.getAvailableOptions();
        boolean isText = options.size() > 0 && options.get(0) instanceof Text;

        if(isText) {

            int maxWidth = 0;
            for(T option : options) {
                int textWidth = DisplayUtil.getTextWidth(Config.FONT_20, ((Text) option).getText());
                if(textWidth > maxWidth) {
                    maxWidth = textWidth;
                }
            }
            width = maxWidth + 72;
        }

        double x = width / 2.0d + Config.MENU_ACTION_MARGIN;
        double y = (Config.HEIGHT - Config.MENU_ACTION_MARGIN) - Config.MENU_ACTION_TOP_HEIGHT / 2.0d;

        int count = this.showUnavailable ? this.getOptions().size() : this.getAvailableOptions().size();

        DisplayUtil.drawPicture(x, y, PathUtil.getActionGuiPath("top"), width, Config.MENU_ACTION_TOP_HEIGHT);

        y -= Config.MENU_ACTION_TOP_HEIGHT / 2.0d + Config.MENU_ACTION_MIDDLE_HEIGHT / 2.0d;
        for (int i = 0; i < count; i++) {

            DisplayUtil.drawPicture(x, y, PathUtil.getActionGuiPath("middle"), width, Config.MENU_ACTION_MIDDLE_HEIGHT);
            y -= Config.MENU_ACTION_MIDDLE_HEIGHT;

        }
        y -= Config.MENU_ACTION_BOTTOM_HEIGHT / 2.0d - Config.MENU_ACTION_MIDDLE_HEIGHT / 2.0d;

        DisplayUtil.drawPicture(x, y, PathUtil.getActionGuiPath("bottom"), width, Config.MENU_ACTION_BOTTOM_HEIGHT);

    }

}
