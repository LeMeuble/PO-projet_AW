package librairies;

import ressources.Config;

/** Gere les valeurs numeriques des touches. */
public class AssociationTouches
{
	public static enum SpecialKeys
	{
		ENTREE, GAUCHE, HAUT, DROIT, BAS, MAJ, ECHAP;
	}

	
  
    
    /** Ces attributs donnent les codes des touches speciales */
    private static final int CODE_HAUT = AssociationTouches.keycodeOf(AssociationTouches.SpecialKeys.HAUT);
	private static final int CODE_BAS = AssociationTouches.keycodeOf(AssociationTouches.SpecialKeys.BAS);
	private static final int CODE_DROITE = AssociationTouches.keycodeOf(AssociationTouches.SpecialKeys.DROIT);
	private static final int CODE_GAUCHE = AssociationTouches.keycodeOf(AssociationTouches.SpecialKeys.GAUCHE);
    private static final int CODE_ECHAP = AssociationTouches.keycodeOf(AssociationTouches.SpecialKeys.ECHAP);
    private static final int CODE_ENTREE = AssociationTouches.keycodeOf(AssociationTouches.SpecialKeys.ENTREE);

    /**La liste des touches speciales pertinentes; ne pas modifier cette liste */
    private static final AssociationTouches[] TOUCHES_PERTINENTES_SPECIALES = {new AssociationTouches(CODE_HAUT), new AssociationTouches(CODE_BAS), new AssociationTouches(CODE_DROITE), new AssociationTouches(CODE_GAUCHE), new AssociationTouches(CODE_ECHAP), new AssociationTouches(CODE_ENTREE)};

/**
 * Teste si la le controle est la touche correspondant au caractere.
 * @param c Le caractere a tester
* @return <code>true</code> si oui, <code>false</code> si non    
 */
    public boolean isCaractere(char c) {
        return (this.cle == AssociationTouches.keycodeOf(Character.toLowerCase(c)) || this.cle == AssociationTouches.keycodeOf(Character.toUpperCase(c)));
    }

    
    
    
/**
 * Renvoie la prochaine entree pertinente de l'utilisateur: cette entree n'est renvoyee que quand la touche est relachee, et la touche doit correspondre a un element de TOUCHES_PERTINENTES_SPECIALES ou de TOUCHES_PERTINENTES_CARACTERES
 * @return La prochaine entree pertinente de l'utilisateur 
 */
     public static AssociationTouches trouveProchaineEntree() {
        int i=0;
        while(true) {
            for (i=0; i<TOUCHES_PERTINENTES_SPECIALES.length; i++) {
                if (testTouche(TOUCHES_PERTINENTES_SPECIALES[i])) {
                    return TOUCHES_PERTINENTES_SPECIALES[i];
                }
            }
            for (i=0; i<Config.TOUCHES_PERTINENTES_CARACTERES.length; i++) {
                if (testTouche(controleCaractereMinuscule(Config.TOUCHES_PERTINENTES_CARACTERES[i])) || 
                testTouche(controleCaractereMajuscule(Config.TOUCHES_PERTINENTES_CARACTERES[i]))) {
                    return controleCaractereMinuscule(Config.TOUCHES_PERTINENTES_CARACTERES[i]);
                }
            }
        }
    }

/**
 * Teste si la touche est actuellent pressee.
 * @param controle La touche a tester 
 * @return <code>true</code> si oui, <code>false</code> si non    
 */
    private static boolean testTouche(AssociationTouches controle) {
        if (StdDraw.isKeyPressed(controle.getCle())) {
            while (StdDraw.isKeyPressed(controle.getCle())) {}
            return true;
        }
        return false;
    }

    /** Teste si le controle est la touche haut. 
    *@return <code>true</code> si oui, <code>false</code> si non    
    */
    public boolean isHaut() {
        return this.cle==CODE_HAUT;
    }

    
    /** Teste si le controle est la touche bas. 
    *@return <code>true</code> si oui, <code>false</code> si non    
    */
    public boolean isBas() {
        return this.cle==CODE_BAS;
    }

    /** Teste si le controle est la touche gauche.
    *@return <code>true</code> si oui, <code>false</code> si non      
    */
    public boolean isGauche() {
        return this.cle==CODE_GAUCHE;
    }

    
    /** Teste si le controle est la touche droite. 
    *@return <code>true</code> si oui, <code>false</code> si non    
    */
    public boolean isDroite() {
        return this.cle==CODE_DROITE;
    }

    /** Teste si le controle est la touche entree. 
    *@return <code>true</code> si oui, <code>false</code> si non    
    */
    public boolean isEntree() {
        return this.cle==CODE_ENTREE;
    }

    

    /** Teste si le controle est la touche echap. 
    *@return <code>true</code> si oui, <code>false</code> si non    
    */
    public boolean isEchap() {
        return this.cle==CODE_ECHAP;
    }



// Fonctionnement interne de la classe 
    private final int cle;

    private AssociationTouches(int cle2) {
        this.cle = cle2;
    }

    

    private int getCle() {
        return cle;
    }

    private static AssociationTouches controleCaractereMinuscule(char c) {
        return new AssociationTouches(AssociationTouches.keycodeOf(Character.toUpperCase(c)));
    }

    private static AssociationTouches controleCaractereMajuscule(char c) {
        return new AssociationTouches(AssociationTouches.keycodeOf(Character.toLowerCase(c)));
    }

	/**
	 * Get the keycode of the special keys. Useful for interacting with StdDraw.
	 * 
	 * @param key the special key
	 * @return the keycode of the key
	 */
	public static int keycodeOf(SpecialKeys key)
	{
		switch (key)
		{
		case ENTREE:
			return 10;
		case GAUCHE:
			return 37;
		case HAUT:
			return 38;
		case DROIT:
			return 39;
		case BAS:
			return 40;
		case MAJ:
			return 16;
		case ECHAP:
			return 27;
		}
		return -1;
	}

	public static int keycodeOf(char character)
	{
		return (int) character - 32;
	}
}
