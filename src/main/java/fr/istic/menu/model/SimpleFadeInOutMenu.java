package fr.istic.menu.model;

import fr.istic.StdDraw;
import fr.istic.menu.FadeMenu;
import fr.istic.menu.MenuModel;
import fr.istic.Config;

import java.awt.*;

/**
 * Classe representant un fr.istic.menu avec un fondu entrant
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class SimpleFadeInOutMenu extends FadeMenu {

    /**
     * Constructeur du fr.istic.menu
     */
    public SimpleFadeInOutMenu() {

        super(20, 20);

    }

    /**
     * Methode gerant l'affichage du fr.istic.menu
     */
    @Override
    public void render() {

        final Color backgroundColor = new Color(0, 0, 0, this.getAlpha());

        StdDraw.setPenColor(backgroundColor);
        StdDraw.filledRectangle(Config.WIDTH / 2d, Config.HEIGHT / 2d, Config.WIDTH / 2d, Config.HEIGHT / 2d);

    }

    @Override
    public MenuModel getModel() {
        return MenuModel.SIMPLE_FADE_IN_OUT_MENU;
    }

}
