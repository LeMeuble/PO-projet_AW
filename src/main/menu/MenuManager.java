package main.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe permettant de gerer les menus (gestionnaire de menus).
 * Cette classe permet de gerer la liste des menus a rendre.
 * Elle permet au {@link main.render.Renderer} de recuperer la liste des menus a et d'appeler
 * la methode {@link Menu#render()} de chaque menu si celui-ci est visible.
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 * @see Menu
 * @see MenuModel
 */
public class MenuManager {

    private final Map<MenuModel, Menu> menus;


    /**
     * Constructeur du gestionnaire de menus
     *
     * @see Menu
     * @see MenuModel
     */
    public MenuManager() {
        this.menus = new HashMap<>();
    }

    /**
     * Ajouter un menu au gestionnaire de menus
     *
     * @param menu Le menu a ajouter
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
     * Supprimer un menu du gestionnaire de menus
     * a partir de son modele
     *
     * @param model Le menu a supprimer
     *
     * @return true si le menu a ete supprime, false sinon
     *
     * @see Menu
     * @see MenuModel
     */
    public boolean removeMenu(MenuModel model) {
        return this.menus.remove(model) != null;
    }

    /**
     * Obtenir un menu du gestionnaire de menus
     * a partir de son modele
     *
     * @param model Le modele du menu a obtenir
     *
     * @return Le menu correspondant au modele
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

}
