package ressources;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

/** Cette classe gere le parseur de cartes. Nous vous conseillons de ne pas la modifier, sauf eventuellement si vous voulez ajouter une unite qui n'est pas dans la liste de l'enonce. Dans ce cas faites attention que le parseur reste compatible avec le format original.  */
public class ParseurCartes {
	

	/**
	 * Ajoute les variantes de joueurs pour une entitee donnee
	 * Par exemple si l'entite est l'"Usine", dont l'encodage est "U", 
	 * qu'il y a 2 joueurs et que la variantes neutre est requise,
	 * cette methode ajoutera  au dictionnaire les entrees
	 * "U0"->"Usine0"; "U1"->"Usine1"; "U2"->"Usine2"
	 * @param dictionnaire Dictionnaire ou il faut ajouter les variantes 
	 * @param encodage Encodage du fichier awdcmap correspondant a l'entite
	 * @param entite Entite correspondant a l'encodage
	 * @param nbJoueurs Nombre de joueurs pour lesquels il faut generer des variantes
	 * @param inclureNeutre Signifie s'il faut ajouter ou non la variante 0 correspondant au neutre
	 */
	private static void ajouterVariationsDEntitesAuDictionnaire (Map<String, String> dictionnaire,String encodage, String entite, int nbJoueurs, boolean inclureNeutre) {
		int playerId = 1;
		if (inclureNeutre) {
			playerId = 0;
		}
		for (; playerId<=nbJoueurs; playerId++) {
			dictionnaire.put(encodage+playerId, entite+":"+playerId);
		}
	}
	
	/**
	 * Genere le dictionnaire des types de terrain possibles.
	 * Ce dictionnaire determine les encodages de terrain lus
	 * @param nbJoueurs Determine les variantes de propriete a generer
	 * @return Un dictionnaire dont la clef est l'encodage lu dans le fichier .awdcmap
	 */
	private static Map<String, String> genereDictionnaireTerrain(int nbJoueurs) {
		Map<String, String> dicoTerrain = new HashMap<String, String>();
		dicoTerrain.put("M", "Montagne");
		dicoTerrain.put("F", "Foret");
		dicoTerrain.put("P", "Plaine");
		dicoTerrain.put("E", "Eau");
		ajouterVariationsDEntitesAuDictionnaire(dicoTerrain,"V","Ville",nbJoueurs,true);
		ajouterVariationsDEntitesAuDictionnaire(dicoTerrain,"U","Usine",nbJoueurs,true);
		ajouterVariationsDEntitesAuDictionnaire(dicoTerrain,"Q","QG",nbJoueurs,false);
		return dicoTerrain;
	}
	
	
	/**
	 * Genere le dictionnaire des types de terrain possibles.
	 * Ce dictionnaire determine les encodages d'unites lus
	 * @param nbJoueurs Determine les variantes des unites
	 * @return Un dictionnaire dont la clef est le symbole lu dans le fichier .awdcmap
	 */
	private static Map<String, String> genereDictionnaireTroupes(int nbJoueurs) {
		Map<String, String> dicoTroupes = new HashMap<String, String>();
		ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"a","Artillerie",nbJoueurs,false);
		ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"b","Bombardier",nbJoueurs,false);
		ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"z","Bazooka",nbJoueurs,false);
		ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"c","Convoit",nbJoueurs,false);
		ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"d","DCA",nbJoueurs,false);
		ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"h","Helico",nbJoueurs,false);
		ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"i","Infanterie",nbJoueurs,false);
		ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"t","Tank",nbJoueurs,false);
		return dicoTroupes;
	}
	
	/**
	 * Lit les metadonnees de la categorie correspondante
	 * @param entree La chaine de caractere dans laquelle chercher la metadonnee
	 * @param expected Le nom attendu de la variable de metadonnee
	 * @return La valeur de la metadonnee
	 * @throws IOException
	 */
	private static int lireMetadonnees(String entree, String expected) throws IOException
	{
		String[] data = entree.split("=");
		if(!data[0].equals(expected))
		{
			throw new IOException("metadonnée incorrecte : " + expected +
					" etait attendu, pas : " + data[0]);
		}
		try {
			return Integer.parseInt(data[1]);
		}catch(NumberFormatException e) {
			throw new IOException("Ne peut pas extraire une valeur entière de metadonne depuis " + entree);
		}
	}
	
/**
 * Parse (=lit et interprete) le fichier de carte donne en entree
 * @param fichier Le chemin complet vers le fichier .awdcmap
 * @return Un tableau bidimensionnel de Strings, correspondant a l'interpretation du fichier: chaque case du tableau correspondont a une case de la carte de jeu; a vous de tranformer ensuite ce tableau de String en instances des classes que vous avez cree! 
 * @throws IOException Exception levee quand le fichier n'est pas correct
 */
	public static String[][] parseCarte(String fichier) throws IOException {
		InputStream is = null;
		Scanner sc = null;
		try {
			is = new FileInputStream(fichier);
			sc = new Scanner(is);
			String plateau_metadonnes_tmp = sc.nextLine();
			String [] plateau_metadonnes = plateau_metadonnes_tmp.split(",");
			if(plateau_metadonnes.length != 3)
			{
				throw new IOException("Metadonnes incorrectes : " + plateau_metadonnes_tmp + " dans le fichier " + fichier +
						". Les metadonnes doivent avoir cette forme : hauteur=Y,largeur=X,nbJoueurs=J (X,Y et J sont les valeurs numeriques associees)");
			}
			int hauteur = lireMetadonnees(plateau_metadonnes[0],"hauteur");
			int largeur = lireMetadonnees(plateau_metadonnes[1],"largeur");
			int nbJoueurs = lireMetadonnees(plateau_metadonnes[2],"nbJoueurs");
			boolean[] foundQG = new boolean[nbJoueurs];
			Map<String, String> dicoTerrain = genereDictionnaireTerrain(nbJoueurs);
			Map<String, String> dicoTroupes = genereDictionnaireTroupes(nbJoueurs);
			String[][] res = new String[hauteur][largeur];
			for (int i =0; i<hauteur; i++) {
				String tmp_units = sc.nextLine();
				String tmp_terrain = sc.nextLine();
				String [] units_line = tmp_units.split(",");
				String [] terrain_line = tmp_terrain.split(",");
				if(units_line.length != largeur)
				{
					throw new IOException("Nombre incorrect d'unites dans " + fichier + " a la ligne " + (2*i+2)+ "; " + units_line.length + " unites trouves, " + largeur + "attendu");
				}
				if(terrain_line.length != largeur)
				{
					throw new IOException("Nombre incorrect de terrains dans " + fichier + " a la ligne " + (2*i+3));
				}
				for (int j = 0; j<largeur; j++) {
					String terrain_square = terrain_line[j].replaceAll(" ", "");
					res[i][j] = dicoTerrain.get(terrain_square);
					if (res[i][j] == null) {
						throw new IOException("Terrain inconnu: " + terrain_square + " a la ligne " + (2*i+3));
					}
					for (int p = 1; p <= nbJoueurs; p++) {
						if (res[i][j].equals("QG:" +p)) {
							if (foundQG[p-1]) {
								throw new IOException("Deux QGs ont ete trouve pour le joueur "+ p);
							}
							foundQG[p-1] = true;
						}
					}
					String unit = units_line[j].replaceAll(" ", "");
					if(!unit.isEmpty()) {
						String unitParsed = dicoTroupes.get(unit);
						if (res[i][j] == null) {
							throw new IOException("Unite inconnue: " + unitParsed + " a la ligne " + (2*i+2));	
						}
						res[i][j] += (";"+dicoTroupes.get(unit));
					}
				}

			}
			sc.close();
			for (int p = 1; p <= nbJoueurs; p++) {
				if (!foundQG[p-1]) {
					throw new IOException("Pas de QG trouve pour le joueur "+ p);
				}
			}
			return res;
		} catch (FileNotFoundException e) {
			sc.close();
			throw e;
		} catch (IOException e) {
			sc.close();
			throw e;
		} finally {
			sc.close();
		}
	}	
}
