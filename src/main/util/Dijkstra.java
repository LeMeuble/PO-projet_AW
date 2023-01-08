package main.util;

import com.sun.istack.internal.Nullable;
import main.map.Case;
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

    public Dijkstra(Case source, List<Case> cases, Unit unit, Weather weather) {
        this.source = source;
        this.graph = new HashMap<>();
        this.unit = unit;
        this.weather = weather;

        this.addVertices(cases);
        this.addEdges();
        this.run();
    }

    public Set<Case> getReachables(int cost) {
        return this.graph.keySet().stream().filter(c -> this.graph.get(c).distance <= cost).collect(Collectors.toSet());
    }

    @Nullable
    public List<Case> getShortestPathTo(Case target) {

        Vertex v = this.graph.get(target);

        if (v == null) return null;
        if (v.distance == Integer.MAX_VALUE) return null;

        List<Case> path = new ArrayList<>();

        while (v != null && v.c != this.source) {
            path.add(v.c);
            v = v.previous;
        }

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

                    if (this.unit.canMoveTo(v2.c, this.weather)) {
                        v.neighbours.put(v2, this.unit.getMovementCostTo(v2.c, this.weather));
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
