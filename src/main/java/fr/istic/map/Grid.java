package fr.istic.map;

import fr.istic.MiniWars;
import fr.istic.animation.MovementAnimation;
import fr.istic.game.Game;
import fr.istic.game.GameState;
import fr.istic.game.Movement;
import fr.istic.game.Player;
import fr.istic.menu.MenuManager;
import fr.istic.menu.model.UnitActionMenu;
import fr.istic.render.Popup;
import fr.istic.render.PopupRegistry;
import fr.istic.render.Renderer;
import fr.istic.terrain.Property;
import fr.istic.terrain.type.Forest;
import fr.istic.terrain.type.Mountain;
import fr.istic.unit.Flying;
import fr.istic.unit.Unit;
import fr.istic.unit.UnitAction;
import fr.istic.util.Dijkstra;
import fr.istic.util.OptionSelector;
import fr.istic.weather.Weather;

import java.util.*;

/**
 * Classe representant la grille du plateau de jeu sous forme d'un
 * tableau 2D de cases
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Grid {

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

        final Coordinate srcCoordinate = center.getCoordinate();

        List<Case> around = this.getCasesAround(srcCoordinate, 1, unit.getMovementPoint(weather));
        around.add(center);

        Dijkstra dijkstra = new Dijkstra(center, around, unit, weather);

        return dijkstra.getReachableCases(unit.getMovementPoint(weather));

    }

    /**
     * Verifie si l'unite presente sur la case est vivante, sinon la fait disparaitre
     */
    public void garbageUnit() {

        this.getCases().forEach(c -> {
            if (c.hasUnit() && !c.getUnit().isAlive()) {
                c.setUnit(null);
            }
        });

    }

    /**
     * Renvoie la Case correspondante pour des coordonnees
     *
     * @param coordinate L'instance de coordonnee sur la carte
     *
     * @return La case presente en x:y
     *
     * @see Coordinate
     */
    public Case getCase(Coordinate coordinate) {

        return grid[coordinate.getY()][coordinate.getX()];

    }

    /**
     * Obtenir la liste des cases en reduisant la grille a deux dimensions sous
     * forme d'une liste a une dimension.
     *
     * @return La liste des cases
     */
    public List<Case> getCases() {

        List<Case> cases = new LinkedList<>();
        for (int i = 0; i < grid.length; i++) {
            cases.addAll(Arrays.asList(grid[i]));
        }
        return cases;
    }

    /**
     * Renvoie une liste des cases adjacentes a une case passee en parametre
     * La liste ne contient pas la case passee en parametre
     *
     * @param c Un case
     *
     * @return Une liste de Case
     */
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
     * @param coordinate Les coordonnees du centre du cercle
     * @param minRadius  Le rayon minimum du cercle
     * @param maxRadius  La rayon maximum du cercle
     *
     * @return Une liste doublement chainee de Cases
     */
    public List<Case> getCasesAround(Coordinate coordinate, int minRadius, int maxRadius) {

        final List<Case> cases = new LinkedList<>();

        final int x = coordinate.getX();
        final int y = coordinate.getY();

        // On parcourt les cases du cercle seulement pour eviter de faire des calculs inutiles
        final int minX = Math.max(x - maxRadius, 0);
        final int maxX = Math.min(x + maxRadius, grid[0].length - 1);

        final int minY = Math.max(y - maxRadius, 0);
        final int maxY = Math.min(y + maxRadius, grid.length - 1);

        for (int i = minX; i <= maxX; i++) {

            for (int j = minY; j <= maxY; j++) {

                double d = Math.abs(x - i) + Math.abs(y - j);

                if (d >= minRadius && d <= maxRadius) {

                    cases.add(this.getCase(new Coordinate(i, j)));

                }

            }

        }

        return cases;

    }

    /**
     * A partir d'une liste de cases, renvoie une liste des unites contenues dans ces cases
     *
     * @param casesAround Une liste de case
     *
     * @return Une liste d'unites
     */
    public List<Unit> getUnitsAround(List<Case> casesAround) {

        List<Unit> output = new LinkedList<>();

        for (Case c : casesAround) {

            if (c.hasUnit()) {
                output.add(c.getUnit());
            }

        }
        return output;
    }

    /**
     * Deplace une unite, en suivant un chemin
     *
     * @param path Le chemin a suivre
     */
    public void moveUnit(Movement path) {

        final Game game = MiniWars.getInstance().getCurrentGame();
        // On coupe le chemin, si jamais il y a une unite ennemie sur ce dernier
        final Movement untrappedPath = path.cutTrappedPath(game.getCurrentPlayer().getType());
        final boolean trapped = path.isPathTrappedFor(game.getCurrentPlayer().getType());

        final Case source = path.getMovementHead();
        final Case destination = untrappedPath.getMovementTail();

        final Unit unit = source.getUnit();
        unit.setMoved(true);
        unit.setEnergy(unit.getEnergy() - untrappedPath.getCost(unit, game.getWeather()));

        source.setUnit(null);

        MiniWars.getInstance().setGameState(GameState.PLAYING_RENDERING_MOVING_UNIT);
        MovementAnimation animation = new MovementAnimation(unit, untrappedPath);

        Renderer.getInstance().setMovementAnimation(animation);

        animation.waitUntilFinished(); // bloque tout
        destination.setUnit(unit);
        game.setSelectedCase(destination);
        game.getCursor().setCoordinate(destination.getCoordinate());

        if (trapped) {
            PopupRegistry.getInstance().push(new Popup("Trapped !", "Your fr.istic.unit was trapped during their movement."));
            unit.setPlayed(true);
            MiniWars.getInstance().setGameState(GameState.PLAYING_SELECTING);
        }
        else {
            MiniWars.getInstance().setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);
            OptionSelector<UnitAction> actions = destination.getUnit().getAvailableActions(destination, this);
            MenuManager.getInstance().addMenu(new UnitActionMenu(actions));
        }

        // On actualise le brouillard de guerre pour toutes les cases entre l'unite et la destination
        this.updateFogOfWar(destination, unit);

    }

    /**
     * Redefinit toutes les cases comme etant dans un certain etat de brouillard de guerre
     *
     * @param foggy L'etat de brouillard de guerre a definir
     */
    public void resetFogOfWar(boolean foggy) {
        this.getCases().forEach(c -> c.setFoggy(foggy));
    }

    /**
     * Actualise le brouillard de guerre pour les batiments, a partir d'une case
     *
     * @param c La case
     */
    public void updateFogOfWarBuilding(Case c) {

        Player.Type p = MiniWars.getInstance().getCurrentGame().getCurrentPlayer().getType();

        // Si la case est une propriete appartenant au joueur courant
        if (c.getTerrain() instanceof Property && ((Property) c.getTerrain()).getOwner() == p) {
            // On enleve le brouillard de toutes les cases autour (elle meme comprise)
            for (Case caseAround : this.getCasesAround(c.getCoordinate(), 0, 2)) {
                caseAround.setFoggy(false);
            }
        }

    }

    /**
     * Actualise le brouillard de guerre pour une unite
     *
     * @param c    La case a actualiser
     * @param unit L'unite
     */
    public void updateFogOfWarUnit(Case c, Unit unit) {

        Player.Type p = MiniWars.getInstance().getCurrentGame().getCurrentPlayer().getType();

        if (unit.getOwner() == p || (c.getTerrain() instanceof Property && ((Property) c.getTerrain()).getOwner() == p)) {

            if (c.getTerrain() instanceof Mountain || unit instanceof Flying) {

                int weatherModifier = 0;
                if (MiniWars.getInstance().getCurrentGame().getWeather() == Weather.RAINY) {
                    weatherModifier = 2;
                }

                for (Case caseAround : this.getCasesAround(c.getCoordinate(), 0, 4 - weatherModifier)) {
                    updateFogOfWarCase(caseAround, unit);
                }

            }
            else {
                int weatherModifier = 0;
                if (MiniWars.getInstance().getCurrentGame().getWeather() == Weather.RAINY) {
                    weatherModifier = 1;
                }

                for (Case caseAround : this.getCasesAround(c.getCoordinate(), 0, 2 - weatherModifier)) {
                    updateFogOfWarCase(caseAround, unit);
                }
            }
        }

    }

    /**
     * Actualise le brouillard de guerre en fonction d'une case et d'une unite
     *
     * @param c Une case
     * @param u Une unite
     */
    public void updateFogOfWarCase(Case c, Unit u) {

        Player.Type p = MiniWars.getInstance().getCurrentGame().getCurrentPlayer().getType();

        // Condition pour permettre aux proprietes ennemies et aux forets de bloquer le champ de vision des unites du joueur courant
        if ((c.getTerrain() instanceof Property && ((Property) c.getTerrain()).getOwner() != p) || c.getTerrain() instanceof Forest) {

            if (c.getCoordinate().distance(u.getCoordinate()) == 1 || c.getCoordinate().equals(u.getCoordinate())) {
                c.setFoggy(false);
            }
        }
        else {
            c.setFoggy(false);
        }
    }

    /**
     * Actualise le brouillard de guerre
     *
     * @param c    Une case
     * @param unit Une unite
     */
    public void updateFogOfWar(Case c, Unit unit) {

        if (unit == null) {
            updateFogOfWarBuilding(c);
        }
        else {
            updateFogOfWarUnit(c, unit);
        }

    }

}
