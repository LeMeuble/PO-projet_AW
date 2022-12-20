package main;

import librairies.AssociationTouches;
import librairies.SelecteurDeFichier;
import librairies.StdDraw;
import main.terrain.Grid;
import ressources.Chemins;
import ressources.GameMap;
import ressources.MapParsing;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.List;


public class Main {

	final static boolean SAUTER_SELECTION_CARTE = false; // mettre a false pour avoir l'outil de selection de carte
	
	public static void main(String[] args) throws IOException {

//		GameMap map = MapParsing.listAvailableMaps().get(0);
//		Grid grid = MapParsing.parseMap(MapParsing.listAvailableMaps().get(0));
//
//		for (int i = 0; i < map.getHeight(); i++) {
//			for (int j = 0; j < map.getWidth(); j++) {
//				System.out.print(grid.getCase(j, i).getTerrain());
//			}
//			System.out.println();
//		}

		try {

			String cheminCarte = selectionCarte();
			Jeu jeu = new Jeu(cheminCarte);
			StdDraw.show();  //StdDraw est utilise en mode buffer pour fluidifier l'affichage: utiliser StdDraw.show() pour afficher ce qui est dans le buffer

			while (!jeu.isOver()) {
				jeu.update();
 			}

			jeu.end();

		} catch (Exception e) {
			System.out.println("Erreur lors de l'execution du jeu : " + e.getMessage());
			e.printStackTrace();
		}

	}

	private static String selectionCarte() throws IOException {
		try
		{
			SelecteurDeFichier selecteur = new SelecteurDeFichier(Chemins.getDossierCartes());
			return selecteur.selectFile();
		}
		catch (InterruptedIOException exception)
		{
			throw new IOException("Selection annulée, jeu non lancé.");
		}
	}
} 
