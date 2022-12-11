package ressources;

import java.awt.Color;

import librairies.AssociationTouches;
import librairies.StdDraw;

import java.awt.Font;
/**
Classe qui gere l'affichage; vous pouvez la modifier si vous souhaiter changer l'affichage. 
 */
public class Affichage {

    /**
     * Donne l'abscisse correspondant a <code>x</code> dans le referentiel du rectangle: par exemple, <code>x=O.0</code> -> gauche du rectangle, <code>x = 1.0</code> -> droite du rectangle.
     * @param gauche Abscisse gauche du rectangle
     * @param droite Abscisse droite du rectangle
     * @param haut Ordonnee superieure du rectangle
     * @param bas Ordonnee inferieure du rectangle
     * @param x
     * @return L'abscisse correspondant a x a l'interieur du rectangle
     */
    private static double positionInterneRectangleX(double gauche, double droite, double haut, double bas, double x) {
        return ((1-x)*gauche + x*droite);
    }

    /**
     * Donne l'ordonnee correspondant a <code>y</code> dans le referentiel du rectangle: par exemple, <code>y=0.0</code> -> bas du rectangle, <code>y = 1.0</code> -> haut du rectangle.
     * @param gauche Abscisse gauche du rectangle
     * @param droite Abscisse droite du rectangle
     * @param haut Ordonnee superieure du rectangle
     * @param bas Ordonnee inferieure du rectangle
     * @param x
     * @return L'ordonnee correspondant a <code>y</code> dans le referentiel du rectangle
     */
    private static double positionInterneRectangleY(double gauche, double droite, double haut, double bas, double y) {
        return ((1-y)*bas + y*haut);
    }

    /**
     * Donne l'abscisse correspondant a <code>x</code> dans le referentiel de la carte: par exemple, <code>x=O.0</code> -> gauche de la carte, <code>x = 1.0</code> -> droite de la carte.
     * @param x 
     * @return L'abscisse correspondant a <code>x</code> dans le referentiel de la carte
     */
    private static double positionInterneCarteX(double x) {
        return (positionInterneRectangleX(Config.limiteGaucheCarte, Config.limiteDroiteCarte, Config.limiteHautCarte, Config.limiteBasCarte, x));
    }

    /**
     * Donne l'ordonnee correspondant a <code>y</code> dans le referentiel de la carte: : par exemple, <code>y=O.0</code> -> bas de la carte, <code>y = 1.0</code> -> haut de la carte.
     * @param y
     * @return L'ordonnee correspondant a <code>y</code> dans le referentiel du rectangle
     */
    private static double positionInterneCarteY(double y) {
        return (positionInterneRectangleY(Config.limiteGaucheCarte, Config.limiteDroiteCarte, Config.limiteHautCarte, Config.limiteBasCarte, y));
    }

    /**
     * Donne l'abscisse du centre d'une case situee sur la colonne <code>x</code>
     * @param x Indice d'une colonne, entre <code>0</code> et <code>Config.mapXLengthSquares-1</code>
     * @return l'abscisse du centre
     */
    public static double coordXCentreCase(int x) {
		return (positionInterneCarteX(((double) x+0.5)/(double) Config.longueurCarteXCases));
	}

    /**
    * Donne l'ordonnee du centre d'une case situee sur la ligne <code>y</code>
    * @param y Indice d'une ligne entre  <code>0</code> et <code>Config.mapYLengthSquares</code> 
    * @return l'ordonnee du centre
    */
	public static double coordYCentreCase(int y) { 
    	return (positionInterneCarteY(((double) y +0.5) / (double) Config.longueurCarteYCases));
    }

    /**
    * Donne l'abscisse qui, dans le referentiel de la zone de texte, correspond a <code>x</code>: <code>x=0.0</code> -> gauche de la zone, <code>x = 1.0</code> -> droite de la zone.
    * @param x proportion en abscisse
    * @return L'abscisse du point qui est a <code>x</code> dans le referentiel de la zone de texte
    */
        public static double coordXZoneTexte(double x) {
        return(Config.limiteGaucheTexte + x * Config.zoneTexteXTaille);
    }

    /**
    * Donne l'ordonnee qui, dans le referentiel de la zone de texte, correspond a <code>y</code>:  <code>y=0.0</code> -> bas de la zone, <code>y = 1.0</code> -> haut de la zone.
    * @param y proportion en ordonnee
    * @return L'abscisse du point qui est a <code>y</code> dans le referentiel de la zone de texte
    */
    public static double coordYZoneTexte(double y) {
        return (Config.limiteBasTexte + y * Config.zoneTexteYTaille);
    }

    /**
    * Dessine un rectangle (contour seulement)
    * @param gauche Abscisse de la gauche du rectangle
    * @param droite Abscisse de la droite du retangle
    * @param haut Ordonnee du haut du rectangle
    * @param bas Ordonnee du bas du rectangle
    * @param c Couleur du contour
    */
    public static void dessineContourRectangle(double gauche, double droite, double haut, double bas, Color c) {
        StdDraw.setPenColor(c);
        StdDraw.rectangle((gauche + droite)/2.0, (haut+bas)/2.0, (droite-gauche)/2.0, (haut - bas)/2.0); 
    }

    /**
    * Dessine un rectangle rempli.
    * @param gauche Abscisse de la gauche du rectangle
    * @param droite Abscisse de la droite du retangle
    * @param haut  Ordonnee du haut du rectangle
    * @param bas Ordonnee du bas du rectangle
    * @param couleur La couleur de l'interieur du rectangle 
    */
        public static void dessineRectanglePlein(double gauche, double droite, double haut, double bas, Color couleur) {
        StdDraw.setPenColor(couleur);
        StdDraw.filledRectangle((gauche + droite)/2.0, (haut+bas)/2.0, (droite-gauche)/2.0, (haut - bas)/2.0); 
    }

    /**
    * Affiche du texte dans une case 
    * @param xCase La colonne de la case
    * @param yCase La ligne de la case
    * @param texte La chaine de caractere a afficher, ne doit pas etre trop long sinon elle depassera
    * @param couleur La couleur du texte
    * @param xInterne L'abscisse du debut du texte dans le referentiel de la case; <code>0.0</code> -> gauche de la case, <code>1.0</code> -> droite de la case
    * @param yInterne L'ordonnee du debut du texte dans le referentiel de la case; <code>0.0</code> -> bas de la case, <code>1.0</code> -> haut de la case
    * @param f Police de l'affichage
    */
   public static void afficheTexteDansCase(int xCase, int yCase, String texte, Color couleur, double xInterne, double yInterne, Font f) {
        StdDraw.setPenColor(couleur);
        StdDraw.setFont(f);
        StdDraw.text(coordXCentreCase(xCase)+ (xInterne-0.5)*Config.largeurCaseX, coordYCentreCase(yCase)+ (yInterne-0.5)*Config.largeurCaseY, texte);
    } 

/**
 * Affiche une image dans une case, centree au centre de la case.
 * @param x L'indice en abscisse de la case
 * @param y L'indice en ordonnee de la case
 * @param cheminImage Le chemin vers l'image
 */
    public static void dessineImageDansCase(int x, int y, String cheminImage) {
        StdDraw.picture(coordXCentreCase(x), coordYCentreCase(y), cheminImage, Config.largeurCaseX, Config.largeurCaseY);
    } 

/**
 * Dessine une ligne.
 * @param departX Abscisse de depart
 * @param departY Ordonnee de depart
 * @param arriveeX Abscisse d'arrivee
 * @param arriveeY Ordonnee d'arrivee
 * @param couleur Couleur de la ligne
 */
    private static void dessineLigne(double departX, double departY, double arriveeX, double arriveeY, Color couleur) {
        StdDraw.setPenColor(couleur);
        StdDraw.line(departX, departY, arriveeX, arriveeY);
    }

/**
 * Dessine le curseur sur la case demandee.
 * @param x L'indice en abscisse de la case
 * @param y L'indice en ordonnee de la case
 */
    public static void dessineCurseur(int x, int y) {
        double margeX = 0.1* Config.largeurCaseX; //distance entre curseur et bord de la case
        double margeY = 0.1* Config.largeurCaseY;
        double longueurX = 0.1 * Config.largeurCaseX; // longueur de chaque petite ligne du curseur
        double longueurY = 0.1 * Config.largeurCaseY;
        double decalageX =  Config.largeurCaseX/2.0 - margeX; //distance entre l'abscisse du centre de la case et le trait du curseur qui en est le plus loin 
        double decalageY =  Config.largeurCaseY/2.0 - margeY;
        for (int vertical = 0; vertical <= 1; vertical++) {
            for (int gauche = 0; gauche <= 1; gauche++) {
                for (int haut = 0; haut <= 1; haut++) {
                    int horizontal = 1 - vertical;
                    int droite = 1 - gauche;    
                    int bas = 1 - haut;
                    double debutX = coordXCentreCase(x) + decalageX * (droite - gauche);
                    double debutY = coordYCentreCase(y) + decalageY * (haut - bas);
                    double finX = debutX + longueurX * (horizontal * gauche -droite * horizontal);
                    double finY = debutY + longueurY * (bas * vertical - haut * vertical);
                    dessineLigne(debutX, debutY, finX, finY, Color.black);
                }
            }
        }
    }

/**
 * Vide la case en affichant la couleur de l'arriere-plan a la place.
 * @param x L'indice en abscisse de la case 
 * @param y L'indice en ordonnee de la case
 */
    public static void videCase(int x, int y) {  
        StdDraw.setPenColor(Config.COULEUR_ARRIERE_PLAN);
        StdDraw.filledRectangle(coordXCentreCase(x), coordYCentreCase(y), 0.95*Config.demiLargeurCaseX, 0.95*Config.demiLargeurCaseY); 
    }

/** 
Vide la zone de texte en affichant la couleur de l'arriere-plan a la place.
 */
    public static void videZoneTexte() {
        dessineRectanglePlein(Config.limiteGaucheTexte, Config.limiteDroiteTexte, Config.limiteHautTexte, Config.limiteBasTexte, Config.COULEUR_ARRIERE_PLAN);       
}

/**
 * Affiche un texte sur ue seule ligne dans la zone situee au-dessus de la carte.
 * @param message Le message a afficher
 */
    public static void afficheTexteDescriptif(String message) {
        videZoneTexte();
        StdDraw.setPenColor(Config.COULEUR_TEXTE_DESCRIPTIF);
        StdDraw.setFont(Config.POLICE_PAR_DEFAUT);
        StdDraw.text(coordXZoneTexte(0.5), coordYZoneTexte(0.2), message);
    }




/**
Affiche la grille.
 */
    public static void dessineGrille() {
    for (int i=0;i<= Config.longueurCarteXCases; i++) {
            dessineLigne(positionInterneCarteX((double) i / (double) Config.longueurCarteXCases), positionInterneCarteY(0), positionInterneCarteX((double) i / (double) Config.longueurCarteXCases), positionInterneCarteY(1), Color.black);
        }
    for (int i=0;i<= Config.longueurCarteYCases; i++) {
            dessineLigne(positionInterneCarteX(0),positionInterneCarteY(((double) i) / (double) Config.longueurCarteYCases), positionInterneCarteX(1), positionInterneCarteY(((double) i) / (double) Config.longueurCarteYCases), Color.black);
        }
    }

    
/**
 * Ouvre une fenetre popup et retourne le choix de l'utilisateur.
 * @param messageExplicatif Message d'explication, ne doit pas etre trop long au rique de sortir la fenetre
 * @param options Liste d'options possibles pour l'utilisateur; si les <code>String</code> des options sont trop longue, l'affichage risque de depasser de la fenetre
 * @param echapAutorise <code>true</code> si le popup peut etre ferme en appuyant sur echap (la fonction renvoie alors <code>-1</code>)
 * @param curseurDefaut La position du curseur lorsque la fenetre s'ouvre
 * @return L'indice entre <code>0</code> et <code>options.length-1</code> de l'option choisie par l'utilisateur, <code>-1</code> si echap
 */
    public static int popup(String messageExplicatif, String[] options, boolean echapAutorise, int curseurDefaut) {
        int nbOptions = options.length;
        double shareForOption = Config.PIXELS_PAR_ITEM_POPUP / (double) Config.nbPixelsY;
        double height  = shareForOption * (nbOptions + 2); //vertical height of the popup window
        int nombreMaxDOptionsAffichees = nbOptions;
        //S'il y a trop d'options a afficher comparer a la taille de la fenetre, il faut faire un popup avec defilement
        if (height>Config.carteTailleY) {
        	height = Config.carteTailleY;
        	nombreMaxDOptionsAffichees = (int) Math.floor(height / shareForOption - 2.0);
        }
        double left = positionInterneCarteX(0.5) - Config.taillePopupX/2.0; //abscisse de la gauche de la fenetre de popup
        double right = positionInterneCarteX(0.5) + Config.taillePopupX/2.; //abscisse de la droite de la fenetre de popup
        double top = positionInterneCarteY(0.5) + height/2.0; //ordonne du haut de la fenetre de popup
        double bottom  = positionInterneCarteY(0.5) - height/2.0; //ordonnee du bas de la fenetre de popup
        double explainationY = bottom + (nombreMaxDOptionsAffichees+1.0)*shareForOption; //ordonne du texte explicatif
        double XOption = 0.8 * left + 0.2*right; 
        double[] topPosOptions = new double[nombreMaxDOptionsAffichees];
        for (int i =0; i<nombreMaxDOptionsAffichees; i++) {
            topPosOptions[i] = explainationY - shareForOption * (1.5 +i);
            }
        int popupCursor = curseurDefaut; // position du curseur, entre 0 et nbOptions - 1
        if (popupCursor >= nombreMaxDOptionsAffichees) {
            popupCursor = 0; //si le départ est en-dehors de la zone, départ à 0
        }
        int offsetOptions = 0; //le rang de l'element le plus en haut actuellement
        AssociationTouches key;
        double centerX, centerY, widthX, widthY, pixelsCursor;
        do {
            dessineRectanglePlein(left, right, top, bottom, Config.POPUP_COULEUR_ARRIERE_PLAN);
            dessineContourRectangle(left, right, top, bottom, Config.POPUP_COULEUR_CADRE);
            StdDraw.setFont(Config.POLICE_POPUP);
            StdDraw.setPenColor(Config.POPUP_COULEUR_TEXTE); // couleur du texte de popup
            StdDraw.textLeft(0.95*left + 0.05*right, explainationY, messageExplicatif);
            for (int i = 0; i< nombreMaxDOptionsAffichees; i++)
            StdDraw.textLeft(XOption, topPosOptions[i], options[i+offsetOptions]);
            StdDraw.show();
            centerX = XOption-0.1*(right-left);
            centerY = topPosOptions[popupCursor];
            pixelsCursor = Math.min(0.1 * (right-left)/Config.largeurCaseX, shareForOption / Config.largeurCaseY); //pour s'assurer que l'image n'est pas deformee
            widthX = pixelsCursor * Config.largeurCaseX;
            widthY = pixelsCursor * Config.largeurCaseY;
            StdDraw.picture(centerX,centerY, Chemins.CHEMIN_CURSEUR_POPUP, widthX, widthY);
            StdDraw.show();
            key = AssociationTouches.trouveProchaineEntree();
            if (key.isHaut()) {
            	if (popupCursor == 1 && offsetOptions >0) {
            		offsetOptions --;
            	}
            	else if (popupCursor> 0) { 
	                dessineRectanglePlein(centerX-widthX/2.0, centerX+widthX/2.0, centerY+widthY/2.0, centerY-widthY/2.0, Config.POPUP_COULEUR_ARRIERE_PLAN);
	                popupCursor--;
            	}
            	
            	
            }
            if (key.isBas()) {
            	// TODO : quand le curseur est a l'avant derniere option affichee et qu'on appuie sur bas, ne pas deplacer le curseur mais augmenter le offset de 1
            	if (popupCursor == nombreMaxDOptionsAffichees-2 && offsetOptions <  (nbOptions- nombreMaxDOptionsAffichees)) {
            		offsetOptions ++;
                }	
            	else if (popupCursor < nombreMaxDOptionsAffichees -1) {
	                dessineRectanglePlein(centerX-widthX/2.0, centerX+widthX/2.0, centerY+widthY/2.0, centerY-widthY/2.0, Config.POPUP_COULEUR_ARRIERE_PLAN);
	                popupCursor++;
                }
            }
            if (key.isEchap() && echapAutorise) {
                StdDraw.setFont(Config.POLICE_PAR_DEFAUT);
                return -1;
            }
        }
        while(!key.isEntree());  //boucle jusqu'a qu'une cle pertinente soit relachee
        StdDraw.setFont(Config.POLICE_PAR_DEFAUT);
        return popupCursor+offsetOptions;
    }
}
