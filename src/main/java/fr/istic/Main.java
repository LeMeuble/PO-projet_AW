package fr.istic;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        try {

            MiniWars miniWars = MiniWars.launch();

        }
        catch (Exception e) {
            System.out.println("Erreur lors de l'execution du jeu : " + e.getMessage());
        }

    }

}
