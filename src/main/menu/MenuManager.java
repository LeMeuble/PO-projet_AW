package main.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuManager {

    private Map<MenuModel, Menu> menus;

    public MenuManager() {
        this.menus = new HashMap<>();
    }

    public void addMenu(MenuModel menuKey, Menu menu) {
        this.addMenu(menuKey, menu, false);
    }

    public void addMenu(MenuModel menuKey, Menu menu, boolean focus) {

        if(menu != null) {
            this.menus.put(menuKey, menu);
        }
    }

    public boolean removeMenu(MenuModel menuKey) {
        return this.menus.remove(menuKey) != null;
    }

    public Menu getMenu(MenuModel menuKey) {
        return this.menus.get(menuKey);
    }

    public List<Menu> getMenus() {
        return new ArrayList<>(this.menus.values());
    }

}
