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

    /**
     * Indique si l'objet qui implement {@link Renderable}
     * a besoin d'etre rafraichi a l'ecran.
     *
     * @return true si l'objet a besoin d'etre rafraichi, false sinon
     */
    boolean needsRefresh();

    /**
     * Change l'etat de l'objet pour indiquer qu'il a ou non
     * besoin d'etre rafraichi a l'ecran.
     *
     * @param needsRefresh true si l'objet a besoin d'etre rafraichi, false sinon
     */
    void needsRefresh(boolean needsRefresh);

}
