package main.menu;

/**
 * Enumeration des modeles de {@link Menu}
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 *
 * @see Menu
 */
public enum MenuModel {
    MAIN_MENU(true),
    MAP_SELECTION_MENU(true),
    UNIT_ACTION_MENU(false),
    FACTORY_ACTION_MENU(false),
    PAUSE_MENU(false);

    private final boolean isPersistent;

    MenuModel(boolean isPersistent) {
        this.isPersistent = false;
    }

    public boolean isPersistent() {
        return this.isPersistent;
    }
}
