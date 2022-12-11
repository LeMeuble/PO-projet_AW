package main;
import ressources.Affichage;
import ressources.Chemins;

import java.io.IOException;
import java.io.InterruptedIOException;

import librairies.SelecteurDeFichier;
import librairies.StdDraw;


public class Main {

	final static boolean SAUTER_SELECTION_CARTE = false; // mettre a false pour avoir l'outil de selection de carte
	
	public static void main(String[] args) throws IOException {
		try{
			String cheminCarte = selectionCarte();
			Jeu jeu= new Jeu(cheminCarte);
			jeu.initialDisplay();
			StdDraw.show();  //StdDraw est utilise en mode buffer pour fluidifier l'affichage: utiliser StdDraw.show() pour afficher ce qui est dans le buffer
			while (!jeu.isOver()) {
				jeu.update();
 			}

		} catch (Exception e) {
			System.out.println("Erreur lors de l'execution du jeu : " + e.getMessage());
		}
	}

	private static String selectionCarte() throws IOException {
		try
		{
			SelecteurDeFichier selecteur = new SelecteurDeFichier(Chemins.getDossierCartes());
			return selecteur.selectFile();
		}
		catch (InterruptedIOException excep)
		{
			throw new IOException("Selection annulée, jeu non lancé.");
		}
	}
} 
