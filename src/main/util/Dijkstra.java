package main.util;

import com.sun.istack.internal.Nullable;
import main.map.Case;
import main.map.Grid;
import main.unit.Unit;
import main.weather.Weather;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe permettant d'implementer l'algorithme de Dijkstra.
 * Code base sur l'implementation commune : <a href="https://rosettacode.org/wiki/Dijkstra%27s_algorithm#Java">Dijkstra
 * Rosetta Code</a>
 */
public class Dijkstra {

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

        @Override
        public int compareTo(Vertex o) {
            if (this.distance == o.distance) {
                return this.toString().compareTo(o.toString());

            }
            return Integer.compare(this.distance, o.distance);
        }

        @Override
        public String toString() {
            return this.c.toString();
        }

    }

    private final Map<Case, Vertex> graph;
    private final Case source;
    private final Unit unit;
    private final Weather weather;

    public Dijkstra(Case source, Grid grid, Unit unit, Weather weather) {
        this(source, grid.getCases(), unit, weather);
    }

    public Dijkstra(Case source, List<Case> cases, Unit unit, Weather weather) {
        this.source = source;
        this.graph = new HashMap<>();
        this.unit = unit;
        this.weather = weather;

        this.addVertices(cases);
        this.addEdges();
        this.run();
    }

    public Set<Case> getReachableCases(int cost) {
        return this.graph.keySet().stream().filter(c -> this.graph.get(c).distance <= cost).collect(Collectors.toSet());
    }

    @Nullable
    public List<Case> getShortestPathTo(Case target, int point) {

        Vertex vertex = this.graph.get(target);

        if (vertex == null) return null;
        if (vertex.distance == Integer.MAX_VALUE) return null;

        List<Case> path = new ArrayList<>();

        while (vertex != null && vertex.c != this.source) {
            if(vertex.distance <= point) path.add(vertex.c);
            vertex = vertex.previous;
        }

        Collections.reverse(path);

        return path;
    }


    private void run() {

        final Vertex startVertex = this.graph.get(this.source);

        NavigableSet<Vertex> set = new TreeSet<>();
        for (Vertex v : this.graph.values()) {
            v.previous = v == startVertex ? startVertex : null;
            v.distance = v == startVertex ? 0 : Integer.MAX_VALUE;
            set.add(v);
        }

        Vertex current;
        Vertex neighbour;
        while (!set.isEmpty()) {

            current = set.pollFirst();
            if (current == null || current.distance == Integer.MAX_VALUE) break;

            for (Map.Entry<Vertex, Integer> e : current.neighbours.entrySet()) {

                neighbour = e.getKey();

                final int alternateDist = current.distance + e.getValue();

                if (alternateDist < neighbour.distance) {
                    set.remove(neighbour);
                    neighbour.distance = alternateDist;
                    neighbour.previous = current;
                    set.add(neighbour);
                }

            }

        }

    }

    private void addEdges() {

        for (Vertex v : this.graph.values()) {

            for (Vertex v2 : this.graph.values()) {

                if (v.c.isAdjacent(v2.c)) {

                    if (this.unit.canMoveTo(v2.c, this.weather) || v2.c.isFoggy()) {
                        if (this.unit.getMovementCostTo(v2.c, this.weather) != -1) {
                            v.neighbours.put(v2, this.unit.getMovementCostTo(v2.c, this.weather));
                        }
                    }

                }

            }

        }

    }

    private void addVertices(List<Case> cases) {
        for (Case c : cases) {
            this.graph.put(c, new Vertex(c));
        }
    }

}
