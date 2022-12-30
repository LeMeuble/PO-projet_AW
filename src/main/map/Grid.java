package main.map;

import main.unit.Unit;

import java.util.*;
import java.util.function.Consumer;

/**
 * Classe representant la grille du plateau de jeu sous forme d'un
 * tableau 2D de cases
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 */
public class Grid implements Iterable<Case> {

    private final Case[][] grid;

    /**
     * Constructeur de la grille
     * Recupere le tableau 2D de String parse par {@link ressources.ParseurCartes}
     * @param grid Grille sous forme d'un tableau 2D de String
     */
    public Grid(Case[][] grid) {

        this.grid = grid;

    }

    /**
     * Renvoie la Case correspondante pour des coordonnees x et y
     * @param x La coordonnee x sur la carte
     * @param y La coordonnee y sur la carte
     * @return La Case presente en x:y
     */
    public Case getCase(int x, int y) {

        return grid[y][x];

    }

    public List<Case> getCases() {

        List<Case> cases = new LinkedList<>();
        for (int i = 0; i < grid.length; i++) {
            cases.addAll(Arrays.asList(grid[i]));
        }
        return cases;
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

    /**
     * Renovie les unites presentes dans une liste de Cases
     * @param casesAround Une liste de Cases
     * @return Une Liste d'Unitees
     */
    public List<Unit> getUnitsAround(List<Case> casesAround) {

        List<Unit> output = new LinkedList<>();

        for(Case c : casesAround) {

            if(c.hasUnit()) {
                output.add(c.getUnit());
            }

        }
        return output;
    }

    @Override
    public Iterator<Case> iterator() {
        return this.getCases().iterator();
    }

    @Override
    public void forEach(Consumer<? super Case> action) {
        this.getCases().forEach(action);
    }

    @Override
    public Spliterator<Case> spliterator() {
        return Iterable.super.spliterator();
    }

}
