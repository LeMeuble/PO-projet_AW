package main.map;

import main.MiniWars;
import main.game.Movement;
import main.game.Player;
import main.terrain.Property;
import main.terrain.type.Mountain;
import main.terrain.type.Water;
import main.unit.Flying;
import main.unit.Motorized;
import main.unit.OnFoot;
import main.unit.Unit;
import main.weather.Weather;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
     * Recupere le tableau 2D de Case du plateau de jeu
     *
     * @param grid Grille sous forme d'un tableau 2D de String
     */
    public Grid(Case[][] grid) {

        this.grid = grid;

    }

    public Set<Case> getReachableCases(Case center, Unit unit, Weather weather) {

        return this.getCases()
                .stream()
                .filter(c -> c.distance(center) <= unit.getMovementPoint(weather))
                .filter(c -> unit.canMoveTo(c, weather))
                .collect(Collectors.toSet());

    }

    /**
     * Verifie si l'unite presente sur la case est vivante, sinon la fait disparaitre
     */
    public void garbageUnit() {

        this.getCases().forEach(c -> {
            if(c.hasUnit() && !c.getUnit().isAlive()) {
                c.setUnit(null);
            }
        });

    }

    /**
     * Renvoie la Case correspondante pour des coordonnees x et y.
     *
     * @param x La coordonnee x sur la carte
     * @param y La coordonnee y sur la carte
     *
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

    public List<Case> getAdjacentCases(Case c) {

        List<Case> adjacentCases = new ArrayList<>();

        int x = c.getCoordinate().getX();
        int y = c.getCoordinate().getY();

        if (x > 0) adjacentCases.add(grid[y][x - 1]);
        if (x < grid[y].length - 1) adjacentCases.add(grid[y][x + 1]);
        if (y > 0) adjacentCases.add(grid[y - 1][x]);
        if (y < grid.length - 1) adjacentCases.add(grid[y + 1][x]);

        return adjacentCases;

    }

    /**
     * Renvoie les cases presentes dans un cercle autour d'une coordonnee
     *
     * @param x         La coordonnee x sur la carte
     * @param y         La coordonnee y sur la carte
     * @param minRadius Le rayon minimum du cercle
     * @param maxRadius La rayon maximum du cercle
     *
     * @return Une liste doublement chainee de Cases
     */
    public List<Case> getCasesAround(int x, int y, int minRadius, int maxRadius) {
//todo : x, y -> case
        final List<Case> cases = new LinkedList<>();

        // On parcourt les cases du cercle seulement pour eviter de faire des calculs inutiles
        final int minX = Math.max(x - maxRadius, 0);
        final int maxX = Math.min(x + maxRadius, grid[0].length - 1);

        final int minY = Math.max(y - maxRadius, 0);
        final int maxY = Math.min(y + maxRadius, grid.length - 1);

        for (int i = minX; i <= maxX; i++) {

            for (int j = minY; j <= maxY; j++) {

                double d = Math.abs(x - i) + Math.abs(y - j);

                if (d >= minRadius && d <= maxRadius) {

                    cases.add(this.getCase(i, j));

                }

            }

        }

        return cases;

    }

    public List<Unit> getUnitsAround(List<Case> casesAround) {

        List<Unit> output = new LinkedList<>();

        for (Case c : casesAround) {

            if (c.hasUnit()) {
                output.add(c.getUnit());
            }

        }
        return output;
    }

    public void moveUnit(Case source, Case destination, Movement path) {

        Unit unit = destination.getUnit();

//        destination.setUnit(source.getUnit());
//        source.setUnit(null);

        // Todo : De l'opti la dessus
        List<Case> movement = path.getCases();
        for(Case mainCase : movement) {
            this.updateFogOfWar(mainCase, unit);
        }

    }

    // updateForOfWar(c) -> updateForOfWar(c, null)
    // updateForOfWar(c, u) -> updateForOfWar(c, u)

    public void updateFogOfWar(Case c) {
        this.updateFogOfWar(c, null);
    }

    public void updateFogOfWar(Case c, Unit unit) {

        int distance;
        Player.Type p = MiniWars.getInstance().getCurrentGame().getCurrentPlayer().getType();

        if(unit == null) {

            if(c.hasUnit() && c.getUnit().getOwner() == p) {

                Unit u = c.getUnit();

                if(u instanceof Flying) {
                    for(Case caseAround : this.getCasesAround(c.getCoordinate().getX(), c.getCoordinate().getY(), 0, 4)) {
                        caseAround.setIsFoggy(false);
                    }
                }
                else {

                    if(c.getTerrain() instanceof Mountain) {
                        for (Case caseAround : this.getCasesAround(c.getCoordinate().getX(), c.getCoordinate().getY(), 0, 4)) {
                            caseAround.setIsFoggy(false);
                        }
                    }

                    else{
                        for(Case caseAround : this.getCasesAround(c.getCoordinate().getX(), c.getCoordinate().getY(), 0, 2)) {
                            caseAround.setIsFoggy(false);
                        }
                    }
                }
            }

            else {

                if(c.getTerrain() instanceof Property && ((Property) c.getTerrain()).getOwner() == p) {
                    for(Case caseAround : this.getCasesAround(c.getCoordinate().getX(), c.getCoordinate().getY(), 0, 2)) {
                        caseAround.setIsFoggy(false);
                    }
                }
            }

        }

        else{

            if(unit instanceof Flying) {
                for(Case caseAround : this.getCasesAround(c.getCoordinate().getX(), c.getCoordinate().getY(), 0, 4)) {
                    caseAround.setIsFoggy(false);
                }
            }
            else {

                if(c.getTerrain() instanceof Mountain) {
                    for (Case caseAround : this.getCasesAround(c.getCoordinate().getX(), c.getCoordinate().getY(), 0, 4)) {
                        caseAround.setIsFoggy(false);
                    }
                }

                else{
                    for(Case caseAround : this.getCasesAround(c.getCoordinate().getX(), c.getCoordinate().getY(), 0, 2)) {
                        caseAround.setIsFoggy(false);
                    }
                }
            }

        }

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
