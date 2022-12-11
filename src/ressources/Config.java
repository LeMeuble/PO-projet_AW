
package ressources;
import librairies.AssociationTouches;
import librairies.StdDraw;

import java.awt.Color;
import java.awt.Font;
/**
Classe de configuration, qui contient des constantes utiles notamment a l'affichage, et qui initialise les variables d'affichage en fonction de la dimension de la carte
 */
public class Config {

    /** 
 	La liste des caracteres pertinents; rajoutez ici des caracteres si vous voulez qu'ils soient detectes
 	Vous pouvez ensuite detecter l'appuie sur votre nouvelle touche avec la methode AssociationTouches.isCaractere
 	*/
    public static final char[] TOUCHES_PERTINENTES_CARACTERES = {'t'};
	
	
    /** Couleur de l'arriere plan du jeu, utile pour effacer  */
    final protected static Color COULEUR_ARRIERE_PLAN = Color.white;

    /** Nombre de pixels par case du jeu */
    final private static int PIXELS_PAR_CASE = 48; 

    /** Marge sur les cotes, en pixels */
    final private static int PIXELS_MARGE = 20; 

    /** Hauteur de la zone de texte, en pixels */
    final private static int PIXELS_HAUTEUR_ZONE_TEXTE = 50;

    /** Marge entre la zone de texte et la carte, en pixels */
    final private static int PIXELS_MARGE_TEXTE_CARTE = 50;

    /** Couleur du texte qui s'affiche au dessus de la carte */
    final protected static Color COULEUR_TEXTE_DESCRIPTIF = new Color(0,0,51);

    /** Place verticale occupee par chaque option dans la fenetre, en pixels */
    final protected static int PIXELS_PAR_ITEM_POPUP = 35;

    /** Couleur d'arriere-plan de la fenetre de popup */
    final protected static Color POPUP_COULEUR_ARRIERE_PLAN = new Color(255,255,230);

    /** Couleur du cadre de la fenetre de popup */
    final protected static Color POPUP_COULEUR_CADRE = new Color(120, 40, 0);

    /** Police par defaut, celle du texte au-dessus de la carte */
    final protected static Font POLICE_PAR_DEFAUT = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

    /** Police du texte de popup */
    final protected static Font POLICE_POPUP = new Font("Arial", Font.BOLD, 16);

    /** Couleur du texte dans la fenetre de popup */
    final protected static Color POPUP_COULEUR_TEXTE = new Color(0,0,0);

   
   /** Nombre de colonnes de la carte */
    public static int longueurCarteXCases;  

    /** Nombre de lignes de la carte */
    public static int longueurCarteYCases; 

    /** Taille en X d'une case */
    protected static double largeurCaseX;

    /** Taille en Y d'une case */
    protected static double largeurCaseY;  

    /** Moitie de la taille en X d'une case */
    protected static double demiLargeurCaseX; 

    /** Moitie de la taille en Y d'une case */
    protected static double demiLargeurCaseY;

    /** Largeur en X de la zone de texte */
    protected static double zoneTexteXTaille;

    /** Largeur en Y de la zone de texte */
    protected static double zoneTexteYTaille; 

    /** Marge a gauche et a droit */
    private static double margeX;

    /** Marge en haut et en bas */
    private static double margeY;

    /** Taille en X de la carte */
    protected static double carteTailleX;

    /** Taille en Y de la carte */
    protected static double carteTailleY; 

    /** Nombre total de pixels en X */
    protected static int nbPixelsX;

    /** Nombre total de pixels en Y */
    protected static int nbPixelsY;

    /** Abscisse de la limite gauche de la carte */
    protected static double limiteGaucheCarte;

    /** Abscisse de la limite droite de la carte */
    protected static double limiteDroiteCarte;

    /** Ordonnee de la limite haute de la carte */
    protected static double limiteHautCarte;

    /** Ordonnee de la limite basse de la carte */
    protected static double limiteBasCarte;

    /** Abscisse de la limite gauche de la zone de texte */
    protected static double limiteGaucheTexte;

    /** Abscisse de la limite droite de la zone de texte */
    protected static double limiteDroiteTexte;

    /** Ordonnee de la limite haute de la zone de texte */
    protected static double limiteHautTexte;

    /** Ordonnee de la limite basse de la zone de texte */
    protected static double limiteBasTexte;

    /** Taille en X de la fenetre de popup */
    protected static double taillePopupX; 

    /** Indique si le setup a deja ete fait */
    private static boolean setup_fait = false;



    
/**
 * Initialise toutes les constantes necessaires a l'affichage; a appeler une fois avant d'afficher quoi que ce soit 
 * @param nbCasesX Le nombre de cases dans la dimension X de la carte
 * @param nbCasesY Le nombre de cases dans la dimension Y de la carte 
 */
    public static void setDimension(int nbCasesX, int nbCasesY) {
        if (setup_fait) {
            return;
        }
        longueurCarteXCases = nbCasesX;
        longueurCarteYCases = nbCasesY;
        nbPixelsX = (longueurCarteXCases*PIXELS_PAR_CASE)+2*PIXELS_MARGE;
        nbPixelsY = (longueurCarteYCases*PIXELS_PAR_CASE)+2*PIXELS_MARGE+ PIXELS_HAUTEUR_ZONE_TEXTE + PIXELS_MARGE_TEXTE_CARTE;
        carteTailleX = (double) (longueurCarteXCases*PIXELS_PAR_CASE) / (double) nbPixelsX;
        carteTailleY = (double) (longueurCarteYCases*PIXELS_PAR_CASE) / (double) nbPixelsY;
        StdDraw.setCanvasSize(nbPixelsX, nbPixelsY);
        StdDraw.setFont(POLICE_PAR_DEFAUT);
        zoneTexteXTaille = carteTailleX; 
        zoneTexteYTaille = (double) PIXELS_HAUTEUR_ZONE_TEXTE / (double) nbPixelsY;
        margeX = (double) PIXELS_MARGE/ (double) nbPixelsX;
        margeY = (double) PIXELS_MARGE / (double) nbPixelsY;
        largeurCaseX = carteTailleX / (double) longueurCarteXCases;
        largeurCaseY = carteTailleY / (double) longueurCarteYCases;
        demiLargeurCaseX = largeurCaseX / 2.0;
        demiLargeurCaseY = largeurCaseY / 2.0;
        limiteGaucheCarte = margeX;
        limiteDroiteCarte = margeX + carteTailleX;
        limiteBasCarte = margeY;
        limiteHautCarte = margeY + carteTailleY;
        limiteGaucheTexte = limiteGaucheCarte;
        limiteDroiteTexte = limiteDroiteCarte;
        limiteHautTexte = 1.0- margeY;
        limiteBasTexte = 1.0 - margeY - zoneTexteYTaille; 
        taillePopupX = carteTailleX / 1.3;
        setup_fait = true;
    }


}
