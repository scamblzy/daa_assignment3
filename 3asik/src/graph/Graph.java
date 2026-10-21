package graph;

import java.util.*;

public class Graph {
    private final int vertices;
    private final boolean directed;
    private final List<Edge> edges = new ArrayList<>();
    private final List<List<Edge>> adjacency;

    public Graph(int vertices, boolean directed) {
        if (vertices < 1) {
            throw new IllegalArgumentException(
                    "Graph must have at least one vertex."
            );
        }

        this.vertices = vertices;
        this.directed = directed;
        adjacency = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            adjacency.add(new ArrayList<>());
        }
    }

    public int vertexCount() {
        return vertices;
    }

    public boolean isDirected() {
        return directed;
    }

    public List<Edge> edges() {
        return Collections.unmodifiableList(edges);
    }

    public List<Edge> neighbors(int vertex) {
        checkVertex(vertex);
        return Collections.unmodifiableList(adjacency.get(vertex));
    }

    public void addEdge(int from, int to, int weight) {
        checkVertex(from);
        checkVertex(to);

        if (weight < 0) {
            throw new IllegalArgumentException(
                    "Negative weights are not allowed in this project."
            );
        }

        Edge edge = new Edge(from, to, weight);
        edges.add(edge);
        adjacency.get(from).add(edge);

        if (!directed && from != to) {
            adjacency.get(to).add(
                    new Edge(to, from, weight)
            );
        }
    }

    public void checkVertex(int vertex) {
        if (vertex < 0 || vertex >= vertices) {
            throw new IllegalArgumentException(
                    "Vertex must be in range 0.." + (vertices - 1)
            );
        }
    }
}
