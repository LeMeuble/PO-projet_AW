package main.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuManager {

    private Map<Menu.Model, Menu> menus;
    private Menu.Model focusedMenu;

    public MenuManager() {
        this.menus = new HashMap<>();
        this.focusedMenu = null;
    }

    public void addMenu(Menu.Model menuKey, Menu menu) {
        this.addMenu(menuKey, menu, false);
    }

    public void addMenu(Menu.Model menuKey, Menu menu, boolean focus) {

        if(menu != null) {
            this.menus.put(menuKey, menu);
            if(focus) this.focusedMenu = menuKey;
        }
    }

    public boolean removeMenu(Menu.Model menuKey) {
        return this.menus.remove(menuKey) != null;
    }

    public void focusMenu(Menu.Model menuKey) {
        this.focusedMenu = menuKey;
    }

    public void unfocusMenu() {
        this.focusedMenu = null;
    }

    public Menu getFocusedMenu() {
        return this.menus.get(this.focusedMenu);
    }

    public Menu getMenu(Menu.Model menuKey) {
        return this.menus.get(menuKey);
    }

    public List<Menu> getMenus() {
        return new ArrayList<>(this.menus.values());
    }

}
