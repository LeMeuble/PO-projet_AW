package main.render;

/**
 * Interface representant un objet pouvant etre affiche.
 * Il donne les deux methodes permettant de connaitre
 * l'etat de l'objet et de le mettre a jour.
 * Il permet au {@link Renderer} de savoir si l'objet a
 * besoin d'etre rafraichi ou non.
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 */
public interface Renderable {

    boolean needsRefresh();
    void needsRefresh(boolean needsRefresh);

}
