package fr.istic.menu;

/**
 * Enumeration des modeles de {@link Menu}
 *
 * @author PandaLunatique
 * @author LeMeuble
 * @see Menu
 */
public enum MenuModel {
    MAIN_MENU(true),
    MAP_SELECTION_MENU(true),
    UNIT_ACTION_MENU(false),
    DROP_UNIT_MENU(false),
    FACTORY_ACTION_MENU(false),
    BOTTOM_MENU(true),
    PAUSE_MENU(false),
    NEXT_TURN_MENU(false),
    NEXT_TURN_ASK_MENU(false),
    SIMPLE_FADE_IN_OUT_MENU(false),
    ENDING_MENU(false);

    private final boolean isPersistent;

    MenuModel(boolean isPersistent) {
        this.isPersistent = isPersistent;
    }

    public boolean isPersistent() {
        return this.isPersistent;
    }
}
