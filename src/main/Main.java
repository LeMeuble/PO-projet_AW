package main;

import librairies.SelecteurDeFichier;
import librairies.StdDraw;
import ressources.Chemins;

import java.io.IOException;
import java.io.InterruptedIOException;


public class Main {

	final static boolean SAUTER_SELECTION_CARTE = false; // mettre a false pour avoir l'outil de selection de carte
	
	public static void main(String[] args) throws IOException, InterruptedException {

		try {

			Jeu jeu = new Jeu();

			while (!jeu.isOver()) {
				jeu.update();
				Thread.sleep(100);
			}

			jeu.end();

		} catch (Exception e) {
			System.out.println("Erreur lors de l'execution du jeu : " + e.getMessage());
			e.printStackTrace();
		}

//		MapMetadata map = MapParsing.listAvailableMaps().get(0);
//		Grid grid = MapParsing.parseMap(MapParsing.listAvailableMaps().get(0));
//
//		for (int i = 0; i < map.getHeight(); i++) {
//			for (int j = 0; j < map.getWidth(); j++) {
//				System.out.print(grid.getCase(j, i).getTerrain());
//			}
//			System.out.println();
//		}

//		StdDraw.setCanvasSize(500, 500);
//		StdDraw.enableDoubleBuffering();
//		StdDraw.setTitle("Mini Wars");
//
//		Thread.sleep(2000);
//
//		int pixelPerCase = 48;
//
//		int casePerRow = 16;
//		int casePerColumn = 16;
//
//		int bottomMenu = pixelPerCase * 3;
//
//		int width = casePerColumn * pixelPerCase;
//		int height = casePerRow * pixelPerCase;
//
//		System.out.println("width: " + width + " height: " + (height + bottomMenu));
//
//		StdDraw.setCanvasSize(width, height + bottomMenu);
//		StdDraw.enableDoubleBuffering();
//		StdDraw.setXscale(0, width);
//		StdDraw.setYscale(0, height + bottomMenu);
//		StdDraw.setTitle("Mini Wars");
//
//		for (int i = 0; i < casePerColumn; i++) {
//
//			for (int j = 0; j < casePerRow; j++) {
//
//				StdDraw.rectangle((i * pixelPerCase + (float) pixelPerCase / 2), (j * pixelPerCase + (float) pixelPerCase / 2) + bottomMenu, pixelPerCase / 2, pixelPerCase / 2);
//			}
//
//
//		}
//
//		StdDraw.show();

//		try {
//
//			String cheminCarte = selectionCarte();
//			Jeu jeu = new Jeu(cheminCarte);
//			StdDraw.show();  //StdDraw est utilise en mode buffer pour fluidifier l'affichage: utiliser StdDraw.show() pour afficher ce qui est dans le buffer
//
//			while (!jeu.isOver()) {
//				jeu.update();
// 			}
//
//			jeu.end();
//
//		} catch (Exception e) {
//			System.out.println("Erreur lors de l'execution du jeu : " + e.getMessage());
//			e.printStackTrace();
//		}

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
