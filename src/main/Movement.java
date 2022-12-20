package main;

import main.map.Case;
import ressources.Chemins;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Movement {

    enum Direction {

        BEGIN(Chemins.DIRECTION_DEBUT,Integer.MAX_VALUE, Integer.MAX_VALUE),
        END(Chemins.DIRECTION_FIN, Integer.MIN_VALUE, Integer.MIN_VALUE),
        UP(Chemins.DIRECTION_HAUT, 0, 1),
        DOWN(Chemins.DIRECTION_BAS, 0, -1),
        LEFT(Chemins.DIRECTION_GAUCHE, -1, 0),
        RIGHT(Chemins.DIRECTION_DROITE, 1, 0);

        private final String path;
        private final int dx;
        private final int dy;

        Direction(String path, int dx, int dy) {
            this.path = path;
            this.dx = dx;
            this.dy = dy;
        }

        /**
         * Retourne une direction a partir des deltas x et y
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

    protected class Arrow {

        private Case c;
        private Direction from;
        private Direction to;

        public Arrow(Case c, Direction from, Direction to) {
            this.c = c;
            this.from = from;
            this.to = to;
        }

        public Case getCase() {
            return c;
        }

        public String getPath() {
            return Chemins.getCheminFleche(from.path, to.path);
        }

    }

    private final Case startingPoint;
    private List<Case> cases;

    public Movement(Case startingPoint) {

        this.startingPoint = startingPoint;
        this.cases = new LinkedList<>();

    }

    public void update(Case newCase) {

        if (newCase.equals(this.startingPoint)) {
            this.cases.clear();
        } else if (this.cases.contains(newCase)) {
            // TODO: Improve this code cause it's a shitty code
            this.cases = new LinkedList<>(this.cases.subList(0, this.cases.indexOf(newCase) + 1));

        } else this.cases.add(newCase);

    }

    public boolean isEmpty() {

        return cases.isEmpty();

    }

    public Case getHead() {

        return this.startingPoint;

    }

    public Case getTail() {

        return ((LinkedList<Case>) cases).peekLast();

    }

    public List<Arrow> toDirectionalArrows() {

        ListIterator<Case> cases = this.cases.listIterator();
        List<Arrow> directionnalArrows = new LinkedList<>();

        Case previous = null;
        Case current = startingPoint;
        Case next;
        while (cases.hasNext()) {

            next = cases.next();

            directionnalArrows.add(calculateDirection(previous, current, next));

            previous = current;
            current = next;

        }

        directionnalArrows.add(calculateDirection(previous, current, null)); // Cas ou le mouvement est vide

        return directionnalArrows;

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

    /**
     * Reprensation textuelle du mouvement
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
