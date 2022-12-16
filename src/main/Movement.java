package main;

import main.terrain.Case;
import ressources.Affichage;
import ressources.Chemins;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Movement {

    enum DirectionType {
        UP,
        DOWN,
        LEFT,
        RIGHT;


        public DirectionType opposite() {

            switch (this) {
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

    private class Direction {


        private DirectionType previous;
        private Case aCase;
        private DirectionType next;

        public Direction(DirectionType previous, Case aCase, DirectionType next) {

            this.previous = previous;
            this.aCase = aCase;
            this.next = next;

        }

    }


    private final Case startingPoint;
    private List<Case> cases;

    public Movement(Case startingPoint) {

        this.startingPoint = startingPoint;
        this.cases = new ArrayList<>(); // preferable a linked list pour les acces aleatoire

    }

     public void update(Case newCase) {

        if (newCase.equals(this.startingPoint)) {
            this.cases.clear();
        } else if (this.cases.contains(newCase)) {

            this.cases = this.cases.subList(0, this.cases.indexOf(newCase) + 1);

        } else this.cases.add(newCase);

    }

    public int compareX(Case a, Case b) {

        return Integer.compare(a.getX(), b.getX());

    }

    public int compareY(Case a, Case b) {

        return Integer.compare(a.getY(), b.getY());

    }

    public void render() {

        LinkedList<Case> cases = new LinkedList<>(this.cases);

        Case previous = null;
        Case current = startingPoint;
        Case next = null;
        while(cases.peek() != null) {

            next = cases.poll();

            System.out.println("previous: " + previous);
            System.out.println("current: " + current);
            System.out.println("next: " + next);

            Direction dir = calculateDirection(previous, current, next);

            previous = current;
            current = next;

        }



    }

    public Direction calculateDirection(Case previous, Case current, Case next) {

        String previousDirection = null;
        String nextDirection = null;

        if (previous == null) previousDirection = Chemins.DIRECTION_DEBUT;
        

        if (next == null) {



        }



    }

    public String toString() {

        String result = "";

        for (Case c : this.cases) {
            result += "(" + c.getX() + " " + c.getY() + ") -> ";
        }
        return result.substring(0, result.length() - 4);
    }

}
