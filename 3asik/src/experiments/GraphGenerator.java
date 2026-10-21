package experiments;

import graph.Graph;
import java.util.*;

public class GraphGenerator {

    public static Graph connectedUndirected(
            int vertices,
            int edges,
            long seed) {

        long maxEdges = (long) vertices * (vertices - 1) / 2;

        if (vertices < 1 ||
                edges < vertices - 1 ||
                edges > maxEdges) {
            throw new IllegalArgumentException(
                    "Invalid edge count for a connected graph."
            );
        }

        Random random = new Random(seed);
        Graph graph = new Graph(vertices, false);
        Set<Long> used = new HashSet<>();

        // First, create a chain to guarantee connectivity
        for (int v = 1; v < vertices; v++) {
            int weight = 1 + random.nextInt(100);

            graph.addEdge(v - 1, v, weight);
            used.add(key(v - 1, v));
        }

        // Add unique edges until we reach the requested count
        while (graph.edges().size() < edges) {
            int a = random.nextInt(vertices);
            int b = random.nextInt(vertices);

            if (a == b) {
                continue;
            }

            int low = Math.min(a, b);
            int high = Math.max(a, b);

            if (used.add(key(low, high))) {
                int weight = 1 + random.nextInt(100);
                graph.addEdge(low, high, weight);
            }
        }

        return graph;
    }

    public static Graph directed(
            int vertices,
            int edges,
            long seed,
            boolean dag) {

        long maxEdges = (long) vertices * (vertices - 1) / 2;

        if (vertices < 1 || edges < 0 ||
                (dag && edges > maxEdges) ||
                (!dag && edges > (long) vertices * (vertices - 1))) {
            throw new IllegalArgumentException(
                    "Invalid edge count for directed graph."
            );
        }

        Random random = new Random(seed);
        Graph graph = new Graph(vertices, true);
        Set<Long> used = new HashSet<>();

        while (graph.edges().size() < edges) {
            int a = random.nextInt(vertices);
            int b = random.nextInt(vertices);

            if (a == b) {
                continue;
            }

            if (dag && a > b) {
                int temp = a;
                a = b;
                b = temp;
            }

            long edgeKey = key(a, b);

            if (used.add(edgeKey)) {
                int weight = 1 + random.nextInt(100);
                graph.addEdge(a, b, weight);
            }
        }

        return graph;
    }

    private static long key(int a, int b) {
        return ((long) a << 32) | (b & 0xffffffffL);
    }
}
