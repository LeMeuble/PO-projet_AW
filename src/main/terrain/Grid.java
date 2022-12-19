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
    public Grid(String[][] grid) {

        this.grid = this.parse(grid);

    }

    public Case[][] parse(String[][] grid) {

        Case[][] parsed = new Case[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j=0; j < grid[i].length; j++){
                parsed[i][j] = Case.parse(j, i, grid[i][j]);

            }
        }

        return parsed;

    }

    public Case getCase(int x, int y) {

        return grid[y][x];

    }

}
