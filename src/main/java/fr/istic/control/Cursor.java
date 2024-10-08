package fr.istic.control;


import fr.istic.map.Coordinate;

/**
 * Classe representant le curseur de selection
 * Le curseur est represente par un objet {@link Coordinate} ayant pour coordonnees
 * (x et y) pouvant aller respectivement de :
 * x : 0 a mapWidth - 1
 * y : 0 a mapHeight - 1
 *
 * @author PandaLunatique
 * @author LeMeuble
 * @see Coordinate
 * @see Renderable
 */
public class Cursor {

    private final int maxWidth;
    private final int maxHeight;
    private final Coordinate coordinate;
    private boolean needsRefresh;

    /**
     * Constructeur de Curseur
     * Le curseur est initialise a la position (0, 0)
     *
     * @param maxWidth  Largeur de la carte (nombre de cases en largeur)
     * @param maxHeight Hauteur de la carte (nombre de cases en hauteur)
     */
    public Cursor(int maxWidth, int maxHeight) {

        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;

        this.coordinate = new Coordinate();

    }

    /**
     * Obtenir une instance des coordonnees du curseur.
     *
     * @return Une instance des coordonnees du curseur, independante de celle
     * du curseur.
     */
    public Coordinate getCoordinate() {
        return this.coordinate.clone();
    }

    /**
     * Modifier les coordonnees du curseur aux nouvelles coordonnes
     * passees en parametre.
     *
     * @param coordinate Nouvelles coordonnees du curseur.
     */
    public void setCoordinate(Coordinate coordinate) {

        this.coordinate.setX(Math.min(coordinate.getX(), this.maxWidth - 1));
        this.coordinate.setY(Math.min(coordinate.getY(), this.maxHeight - 1));

    }

    /**
     * Fait bouger le curseur d'une case vers le haut.
     *
     * @return true si la position du curseur a ete modifiee, false sinon.
     */
    public boolean up() {

        if (this.coordinate.getY() < this.maxHeight - 1) {
            this.coordinate.add(0, 1);
            this.needsRefresh = true;
            return true;
        }
        return false;

    }

    /**
     * Fait bouger le curseur d'une case vers le bas.
     *
     * @return true si la position du curseur a ete modifiee, false sinon.
     */
    public boolean down() {

        if (this.coordinate.getY() > 0) {
            this.coordinate.add(0, -1);
            this.needsRefresh = true;
            return true;
        }
        return false;

    }

    /**
     * Fait bouger le curseur d'une case vers la droite.
     *
     * @return true si la position du curseur a ete modifiee, false sinon.
     */
    public boolean right() {

        if (this.coordinate.getX() < this.maxWidth - 1) {
            this.coordinate.add(1, 0);
            this.needsRefresh = true;
            return true;
        }
        return false;

    }

    /**
     * Fait bouger le curseur d'une case vers la gauche.
     *
     * @return true si la position du curseur a ete modifiee, false sinon.
     */
    public boolean left() {

        if (this.coordinate.getX() > 0) {
            this.coordinate.add(-1, 0);
            this.needsRefresh = true;
            return true;
        }
        return false;

    }

    /**
     * Determine si le curseur a besoin d'etre rafraichi.
     * a l'ecran
     *
     * @return True si il faut rafraichir l'ecran, false sinon.
     *
     * @see Renderable#needsRefresh()
     */
    public boolean needsRefresh() {
        return this.needsRefresh;
    }

    /**
     * Stipule qu'on n'a plus besoin de rafraichir l'ecran.
     *
     * @param needsRefresh true si on a besoin de rafraichir le curseur
     *                     a l'ecran, false sinon.
     *
     * @see Renderable#needsRefresh(boolean)
     */
    public void needsRefresh(boolean needsRefresh) {
        this.needsRefresh = needsRefresh;
    }


}
