package fr.istic.menu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe permettant de gerer les menus (gestionnaire de menus).
 * Cette classe permet de gerer la liste des menus a rendre.
 * Elle permet au {@link main.render.Renderer} de recuperer la liste des menus a et d'appeler
 * la methode {@link Menu#render()} de chaque fr.istic.menu si celui-ci est visible.
 *
 * @author PandaLunatique
 * @author LeMeuble
 * @see Menu
 * @see MenuModel
 */
public class MenuManager {

    private static MenuManager instance;

    private final Map<MenuModel, Menu> menus;


    /**
     * Constructeur du gestionnaire de menus
     *
     * @see Menu
     * @see MenuModel
     */
    private MenuManager() {
        this.menus = new HashMap<>();
    }

    public static MenuManager getInstance() {
        if (instance == null) {
            instance = new MenuManager();
        }
        return instance;
    }

    /**
     * Ajouter un fr.istic.menu au gestionnaire de menus
     *
     * @param menu Le fr.istic.menu a ajouter
     *
     * @see Menu
     * @see MenuModel
     */
    public void addMenu(Menu menu) {

        if (menu != null) {
            this.menus.put(menu.getModel(), menu);
        }

    }

    /**
     * @return true si au moins un fr.istic.menu a besoin d'etre actualise, false sinon
     */
    public synchronized boolean anyMenuNeedsRefresh() {
        return this.menus.values()
                .stream()
                .anyMatch(Menu::needsRefresh);
    }

    /**
     * Supprimer un fr.istic.menu du gestionnaire de menus
     * a partir de son modele
     *
     * @param model Le fr.istic.menu a supprimer
     *
     * @return true si le fr.istic.menu a ete supprime, false sinon
     *
     * @see Menu
     * @see MenuModel
     */
    public boolean removeMenu(MenuModel model) {
        return this.menus.remove(model) != null;
    }

    /**
     * Obtenir un fr.istic.menu du gestionnaire de menus
     * a partir de son modele
     *
     * @param model Le modele du fr.istic.menu a obtenir
     *
     * @return Le fr.istic.menu correspondant au modele
     *
     * @see Menu
     * @see MenuModel
     */
    public Menu getMenu(MenuModel model) {
        return this.menus.get(model);
    }

    /**
     * Obtenir la liste des menus du gestionnaire de menus
     * triee par ordre de priorite (du moins prioritaire, sois la couches la plus basse,
     * au plus prioritaire, sois la couche la plus haute)
     *
     * @return La liste des menus du gestionnaire de menus trier de la couche la plus basse a la plus haute
     *
     * @see Menu
     * @see MenuModel
     **/
    public List<Menu> getMenus() {
        return this.menus
                .values()
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Supprimer tous les menus non permanents du gestionnaire de menus
     *
     * @see Menu
     * @see MenuModel
     */
    public void clearNonPersistent() {

        List<MenuModel> toRemove = new LinkedList<>();

        this.menus.keySet().iterator().forEachRemaining(menuModel -> {
            if (!menuModel.isPersistent()) {
                toRemove.add(menuModel);
            }
        });

        for (MenuModel model : toRemove) {
            this.removeMenu(model);
        }

    }

}
