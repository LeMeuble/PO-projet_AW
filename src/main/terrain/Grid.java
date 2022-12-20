package main.terrain;

import main.Jeu;

/**
 * Classe representant la grille du plateau de jeu sous forme d'un
 * tableau 2D de cases
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 */
public class Grid {

    private final Case[][] grid;

    /**
     * Constructeur de la grille
     * Recupere le tableau 2D de String parse par {@link ressources.ParseurCartes}
     * @param grid Grille sous forme d'un tableau 2D de String
     */
    public Grid(Case[][] grid) {

        this.grid = grid;

    }


    public Case getCase(int x, int y) {

        return grid[y][x];

    }

}
