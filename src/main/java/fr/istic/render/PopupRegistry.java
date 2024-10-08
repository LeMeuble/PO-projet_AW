package fr.istic.render;

import fr.istic.StdDraw;
import fr.istic.Config;
import fr.istic.DisplayUtil;
import fr.istic.PathUtil;

import java.util.*;

/**
 * Classe listant/gerant les popups.
 * Il s'agit d'un singleton qui fait office de registre pour les popups.
 *
 * @author PandaLunatique
 * @author LeMeuble
 * @implNote Cette methode est concue pour est Thread-safe.
 */
public class PopupRegistry implements Renderable {

    public static final int MAX_POPUPS = 5;

    private static final class InstanceHolder {

        static final PopupRegistry instance = new PopupRegistry();

    }

    public volatile Queue<Popup> popups;
    public volatile boolean needsRefresh;

    /**
     * Constructeur privee pour le singleton {@link PopupRegistry}
     */
    private PopupRegistry() {
        this.popups = new PriorityQueue<>(Comparator.comparingLong(Popup::getCreationTime));
    }

    /**
     * Obtient l'instance du singleton de {@link PopupRegistry} de son
     * {@link InstanceHolder}
     *
     * @return Instance du register
     */
    public static PopupRegistry getInstance() {
        return InstanceHolder.instance;
    }

    /**
     * Cette methode permet d'ajouter une popup au registre.
     * Si la popup provoque un depassement du nombre maximale, la popup la plus "ancienne"
     * sera supprimee.
     *
     * @param popup La popup a ajouter.
     */
    public synchronized void push(Popup popup) {
        if (this.popups.size() >= MAX_POPUPS) {
            this.popups.poll();
        }
        this.popups.add(popup);
        this.needsRefresh = true;
    }


    /**
     * Obtenir la liste des popups
     *
     * @return Liste des popups actives.
     */
    public synchronized List<Popup> getPopups() {
        return new ArrayList<>(this.popups);
    }

    /**
     * Permet la suppression automatique des popups expirees.
     */
    public synchronized void garbageCollect() {
        this.needsRefresh = this.popups.removeIf(Popup::isExpired);
    }

    /**
     * Supprimer toutes les popups du registre.
     */
    public synchronized void clear() {
        this.popups.clear();
        this.needsRefresh = true;
    }

    /**
     * Determiner si il est necessaire d'actualiser l'ecran pour afficher de nouvelles popups ou
     * bien au contraire en supprimer.
     *
     * @return true s'il faut actualiser l'ecran, false sinon
     */
    public synchronized boolean needsRefresh() {
        return this.needsRefresh;
    }

    /**
     * Forcer l'etat de rafraichissement de l'objet.
     *
     * @param needsRefresh true si l'objet a besoin d'etre rafraichi, false sinon
     */
    public synchronized void needsRefresh(boolean needsRefresh) {
        this.needsRefresh = needsRefresh;
    }

    /**
     * Cette methode est appellee par le fr.istic.render pour permettre l'affichage de toutes les popups.
     */
    public void render() {

        double actualWidth = 960 + 32;
        double actualHeight = 406;

        double aroundMargin = 32;
        double innerMargin = 10;

        double x = (Config.WIDTH - (actualWidth / 10)) - aroundMargin + 5;
        double y = Config.HEIGHT - (actualHeight / 10) - aroundMargin;

        double widthOffset = 0;
        for (Popup popup : this.getPopups()) {
            widthOffset = Math.max(widthOffset, DisplayUtil.getTextWidth(Config.FONT_20, popup.getTitle()) + 40 - actualWidth / 5);
        }
        widthOffset += 20;

        for (Popup popup : this.getPopups()) {

            DisplayUtil.drawPicture(x, y, PathUtil.getGlobalGuiPath("popup"), actualWidth / 5 + widthOffset, actualHeight / 5);

            StdDraw.setFont(Config.FONT_20);
            StdDraw.setPenColor(StdDraw.BLACK);

            StdDraw.textLeft(x - actualWidth / 10 + 6, y + 18, popup.getTitle());
            StdDraw.setFont(Config.FONT_18);
            StdDraw.setPenColor(StdDraw.GRAY);

            int i = 0;
            for (String line : popup.getMessage(22)) {
                StdDraw.textLeft(x - actualWidth / 10 + 6, y - 2 - i * 20, line);
                i++;
            }

            y -= actualHeight / 5 + innerMargin;
        }
        this.needsRefresh(false);

    }

}
