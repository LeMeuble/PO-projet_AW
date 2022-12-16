package main;

import main.terrain.Case;
import ressources.Affichage;
import ressources.Chemins;

import java.util.LinkedList;
import java.util.List;

public class Movement {

    private Case startingPoint;
    private List<Case> cases;

    public Movement(Case startingPoint) {

        this.startingPoint = startingPoint;
        this.cases = new LinkedList<>();

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

        Case previous = startingPoint;

        if (this.cases.size() == 0) {

            String chemin = Chemins.getCheminFleche(Chemins.DIRECTION_DEBUT, Chemins.DIRECTION_FIN);
            Affichage.dessineImageDansCase(startingPoint.getX(), startingPoint.getY(), chemin);

        } else {

            for (Case c : this.cases) {

                int dx = compareX(previous, c);
                int dy = compareY(previous, c);

                if (dx < 0 && dy == 0) {
                    String chemin = Chemins.getCheminFleche(Chemins.DIRECTION_HAUT, Chemins.DIRECTION_BAS);
                    Affichage.dessineImageDansCase(startingPoint.getX(), startingPoint.getY(), chemin);
                }

                previous = c;

            }

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
