package fr.istic.util;

import fr.istic.map.Case;
import fr.istic.map.Grid;
import fr.istic.unit.Unit;
import fr.istic.weather.Weather;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe permettant d'implementer l'algorithme de Dijkstra.
 * Code base sur l'implementation commune : <a href="https://rosettacode.org/wiki/Dijkstra%27s_algorithm#Java">Dijkstra
 * Rosetta Code</a>
 */
public class Dijkstra {

    /**
     * Classe permettant de stocker les informations relatives a un noeud.
     * Cette classe interne contient sa {@link Case} associee, sa distance par rapport au noeud de depart et son
     * predecesseur, ainsi que ses voisins.
     */
    public static class Vertex implements Comparable<Vertex> {

        public final Case c;
        public final Map<Vertex, Integer> neighbours;
        public Vertex previous;
        public int distance;

        public Vertex(Case c) {
            this.c = c;
            this.neighbours = new HashMap<>();
            this.previous = null;
            this.distance = Integer.MAX_VALUE;
        }

        /**
         * Permet de comparer deux noeuds en fonction de leur distance par rapport au noeud de depart.
         *
         * @param o Le noeud a comparer
         *
         * @return Un entier negatif si le noeud courant est plus proche du noeud de depart, un entier positif si le noeud
         * courant est plus eloigne du noeud de depart, 0 si les deux noeuds sont a la meme distance du noeud de
         * depart.
         */
        @Override
        public int compareTo(Vertex o) {
            if (this.distance == o.distance) {
                return this.toString().compareTo(o.toString());

            }
            return Integer.compare(this.distance, o.distance);
        }

        @Override
        public String toString() {
            return "(" + this.c + ", " + this.distance + ")";
        }

    }

    private final Map<Case, Vertex> graph;
    private final Case source;
    private final Unit unit;
    private final Weather weather;

    /**
     * Creer une instance de Dijkstra.
     *
     * @param source  La case de depart
     * @param grid    La grille de jeu
     * @param unit    L'unite qui se deplace
     * @param weather La meteo actuelle
     *
     * @see Case
     * @see Grid
     * @see Unit
     * @see Weather
     */
    public Dijkstra(Case source, Grid grid, Unit unit, Weather weather) {
        this(source, grid.getCases(), unit, weather);
    }

    /**
     * Creer une instance de Dijkstra.
     *
     * @param source  La case de depart
     * @param cases   La liste des cases de la grille de jeu
     * @param unit    L'unite qui se deplace
     * @param weather La meteo actuelle
     *
     * @see Case
     * @see Grid
     * @see Unit
     * @see Weather
     */
    public Dijkstra(Case source, List<Case> cases, Unit unit, Weather weather) {
        this.source = source;
        this.graph = new HashMap<>();
        this.unit = unit;
        this.weather = weather;

        this.addVertices(cases);
        this.addEdges();
        this.run();
    }

    /**
     * Permet d'obtenir les listes des cases atteignables en considerant un cout de deplacement.
     *
     * @param cost Le cout de deplacement
     *
     * @return La liste des cases atteignables
     */
    public Set<Case> getReachableCases(int cost) {
        return this.graph.keySet().stream().filter(c -> this.graph.get(c).distance <= cost).collect(Collectors.toSet());
    }

    /**
     * Permet d'obtenir le plus court chemin entre la case de depart et une case donnee, en considerant un cout de
     * deplacement. La case de depart n'est pas incluse dans le chemin.
     *
     * @param target La case d'arrivee
     * @param point  Le cout de deplacement
     *
     * @return La liste des cases constituant le plus court chemin
     *
     * @see Case
     */
    public List<Case> getShortestPathTo(Case target, int point) {

        Vertex vertex = this.graph.get(target);

        if (vertex == null) return null;
        if (vertex.distance == Integer.MAX_VALUE) return null;

        List<Case> path = new ArrayList<>();

        while (vertex != null && vertex.c != this.source) {
            if (vertex.distance <= point) path.add(vertex.c);
            vertex = vertex.previous;
        }

        Collections.reverse(path);

        return path;
    }

    /**
     * Methode principale de l'algorithme de Dijkstra.
     * Permet de calculer la distance minimale entre la case de depart et toutes les autres cases de la grille de jeu.
     *
     * @see Vertex
     */
    private void run() {

        // Noeud de depart
        final Vertex startVertex = this.graph.get(this.source);

        // Initialisation
        // On utilise un arbre binaire qui va s'occuper de garder les noeuds triees par distance
        NavigableSet<Vertex> set = new TreeSet<>();
        for (Vertex v : this.graph.values()) {
            v.previous = v == startVertex ? startVertex : null;
            v.distance = v == startVertex ? 0 : Integer.MAX_VALUE;
            set.add(v);
        }

        Vertex current;
        Vertex neighbour;
        while (!set.isEmpty()) {

            // On recupere le noeud le plus proche du noeud de depart
            current = set.pollFirst();
            if (current == null || current.distance == Integer.MAX_VALUE) break;

            // On met a jour les distances des voisins du noeud courant
            for (Map.Entry<Vertex, Integer> e : current.neighbours.entrySet()) {

                neighbour = e.getKey();

                final int alternateDist = current.distance + e.getValue();

                if (alternateDist < neighbour.distance) {
                    // On enleve le noeud de l'arbre binaire et le remet avec sa nouvelle distance pour actualiser
                    // l'ordre de tri
                    set.remove(neighbour);
                    neighbour.distance = alternateDist;
                    neighbour.previous = current;
                    set.add(neighbour);
                }

            }

        }

    }

    /**
     * Permet de creer les liens entre toutes les cases/noeuds adjacents avec leur distance,
     * representee par le cout de deplacement de l'unite sur la case adjacente.
     */
    private void addEdges() {

        for (Vertex v : this.graph.values()) {

            for (Vertex v2 : this.graph.values()) {

                // Verifie si les deux cases sont adjacentes
                if (v.c.isAdjacent(v2.c)) {

                    // Verifie si l'unite peut se deplacer sur la case adjacente
                    // et que cette derniere est visible par l'unite
                    if (this.unit.canMoveTo(v2.c, this.weather) || v2.c.isFoggy()) {

                        // Verifie si la case n'est pas inaccessible
                        if (this.unit.getMovementCostTo(v2.c, this.weather) != -1) {

                            // Ajoute le lien de v vers v2 avec le cout de deplacement
                            v.neighbours.put(v2, this.unit.getMovementCostTo(v2.c, this.weather));
                        }
                    }
                }
            }
        }

    }

    /**
     * Permet d'ajouter/creer les noeuds du graphe a partir d'une liste de cases.
     *
     * @param cases La liste des cases
     */
    private void addVertices(List<Case> cases) {
        for (Case c : cases) {
            this.graph.put(c, new Vertex(c));
        }
    }

}
