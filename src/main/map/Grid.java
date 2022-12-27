package main.map;

import main.unit.Unit;

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
     * @param grid Grille sous forme d'un tableau 2D de Case
     */
    public Grid(Case[][] grid) {

        this.grid = grid;

    }

    public Case getCase(int x, int y) {

        return grid[y][x];

    }

    /**
     * Renvoie les cases presentes dans un cercle autour d'une coordonnee
     * @param x La coordonnee x sur la carte
     * @param y La coordonnee y sur la carte
     * @param minRadius Le rayon minimum du cercle
     * @param maxRadius La rayon maximum du cercle
     * @return Une liste doublement chainee de Cases
     */
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

        for(int i=minX; i <= maxX; i++) {

            for(int j=minY; j<=maxY; j++) {

                double d = Math.sqrt( Math.pow(x - i, 2) + Math.pow(y - j, 2));

                if(d >= minRadius && d <= maxRadius) {

                    cases.add(this.grid[j][i]);

                }

            }

        }
        return cases;

    }

    public List<Unit> getUnitsAround(List<Case> casesAround) {

        List<Unit> output = new LinkedList<>();

        for(Case c : casesAround) {

            if(c.hasUnit()) {
                output.add(c.getUnit());
            }

        }
        return output;
    }

}
