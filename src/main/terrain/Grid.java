package main.terrain;

import java.util.LinkedList;
import java.util.List;

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

    public List<Case> getCasesAround(int x, int y, int minRadius, int maxRadius) {

        List<Case> cases = new LinkedList<>();

        int minX = Math.max(x - maxRadius, 0);
        int maxX = Math.min(x + maxRadius, grid[0].length - 1);

        int minY = Math.max(y - maxRadius, 0);
        int maxY = Math.min(y + maxRadius, grid.length - 1);

        System.out.println("minX:" + minX);
        System.out.println("maxX:" + maxX);
        System.out.println("minY:" + minY);
        System.out.println("maxY:" + maxY);

        for(int i=minX; i<=maxX; i++) {

            for(int j=minY; j<=maxY; j++) {

                double d = Math.sqrt( Math.pow(x - i, 2) + Math.pow(y - j, 2));

                if(d >= minRadius && d <= maxRadius) {

                    cases.add(this.grid[j][i]);

                }

            }

        }
        return cases;

    }

    public void newTurn() {

        for(int i=0;i<grid.length;i++) {

            for(int j=0;j<grid[0].length;i++) {

                if(grid[j][i].hasUnit()) {
                    grid[j][i].getUnit().reset();
                }

            }

        }

    }
}
