package main.game;


import main.map.Case;
import main.render.Renderable;
import main.unit.Unit;
import main.weather.Weather;
import ressources.PathUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Classe representant un mouvement d'une unite
 * avec une liste de cases a parcourir et un point de depart.
 *
 * @author GRAVOT Lucien
 * @author LECONTE--DENIS Tristan
 *
 * @see Renderable
 */
public class Movement implements Renderable {

    /**
     * Enumeration des directions possibles
     * pour un mouvement
     */
    enum Direction {

        BEGIN(Integer.MAX_VALUE, Integer.MAX_VALUE),
        END(Integer.MIN_VALUE, Integer.MIN_VALUE),
        UP(0, 1),
        DOWN(0, -1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        private final int dx;
        private final int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        /**
         * Retourne une direction a partir des deltas x et y
         *
         * @param dx Delta x
         * @param dy Delta y
         * @return Direction correspondante
         */
        public static Direction fromDelta(int dx, int dy) {
            for (Direction direction : Direction.values()) {
                if (direction.dx == dx && direction.dy == dy) {
                    return direction;
                }
            }
            return null;
        }

        public String getName() {
            return this.name().toLowerCase();
        }

        /**
         * Opposite direction
         * @return Opposite direction
         */
        public Direction opposite() {

            switch (this) {
                case BEGIN:
                    return END;
                case END:
                    return BEGIN;
                case UP:
                    return DOWN;
                case DOWN:
                    return UP;
                case LEFT:
                    return RIGHT;
                case RIGHT:
                    return LEFT;
            }

            return null;
        }
    }

    public static class Arrow {

        private final Case c;
        private final Direction from;
        private final Direction to;

        public Arrow(Case c, Direction from, Direction to) {
            this.c = c;
            this.from = from;
            this.to = to;
        }

        public Case getCase() {
            return c;
        }

        public String getPath(Player.Type owner) {
            return PathUtil.getArrowPath(owner, from.getName(), to.getName());
        }

    }

    private final Case startingPoint;
    private final List<Case> cases;
    private boolean needsRefresh;

    public Movement(Case startingPoint) {

        this.startingPoint = startingPoint;
        this.cases = new LinkedList<>();

    }

    public void update(Case newCase) {

        if(this.cases.contains(newCase)) {

            int index = this.cases.indexOf(newCase);
            this.cases.subList(index + 1, this.cases.size()).clear();

        }
        else if(newCase.equals(startingPoint)) this.cases.clear();
        else this.cases.add(newCase);

        this.needsRefresh = true;

    }

    public List<Case> getCases() {
        return this.cases;
    }

    public void goBack() {
        ((LinkedList<Case>) cases).removeLast();
    }

    public boolean isEmpty() {
        return cases.isEmpty();
    }

    public Case getMovementHead() {
        return this.startingPoint;
    }

    public Case getMovementTail() {

        if (this.cases.isEmpty()) return this.startingPoint;
        return this.cases.get(this.cases.size() - 1);

    }

    public int getCost(Unit unit, Weather weather) {

        int cost = 0;

        for(Case c : this.cases) {
            cost += unit.getMovementCostTo(c.getTerrain(), weather);
        }

        return cost;
    }

    public List<Arrow> toDirectionalArrows() {

        List<Arrow> directionalArrows = new LinkedList<>();
        synchronized (this) {

            ListIterator<Case> cases = this.cases.listIterator();

            Case previous = null;
            Case current = startingPoint;
            Case next;
            while (cases.hasNext()) {

                next = cases.next();

                directionalArrows.add(calculateDirection(previous, current, next));

                previous = current;
                current = next;

            }

            directionalArrows.add(calculateDirection(previous, current, null)); // Cas ou le mouvement est vide

        }



        return directionalArrows;

    }

    /**
     * Calculer la direction de la fleche a afficher selon les cases precedente et suivante
     * @param previous Case precedente
     * @param current Case actuelle
     * @param next Case suivante
     * @return Retourne une direction selon les cases adjacentes
     */
    public Arrow calculateDirection(Case previous, Case current, Case next) {

        if(current == null) return null;

        Direction from;
        Direction to;

        if(previous == null) from = Direction.BEGIN;
        else from = Direction.fromDelta(current.getX() - previous.getX(), current.getY() - previous.getY()).opposite();

        if(next == null) to = Direction.END;
        else to = Direction.fromDelta(next.getX() - current.getX(), next.getY() - current.getY());

        return new Arrow(current, from, to);

    }

    @Override
    public boolean needsRefresh() {
        return this.needsRefresh;
    }

    @Override
    public void needsRefresh(boolean needsRefresh) {
        this.needsRefresh = needsRefresh;
    }

    /**
     * Reprensation textuelle du mouvement
     *
     * @return Representer le mouvement sous forme de chaine de caractere
     */
    public String toString() {

        String result = "";

        for (Case c : this.cases) {
            result += "(" + c.getX() + " " + c.getY() + ") -> ";
        }
        return result.substring(0, result.length() - 4);
    }

}
