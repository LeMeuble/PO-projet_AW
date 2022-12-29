package main;

import java.io.IOException;


public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {

		try {

			MiniWars miniWars = new MiniWars();
			System.out.println("MiniWars started");

		} catch (Exception e) {
			System.out.println("Erreur lors de l'execution du jeu : " + e.getMessage());
			e.printStackTrace();
		}

	}

}
