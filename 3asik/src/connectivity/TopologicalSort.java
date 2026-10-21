package connectivity;

import graph.Edge;
import graph.Graph;
import java.util.*;

public class TopologicalSort {

    public static List<Integer> run(Graph graph) {
        if (!graph.isDirected()) {
            throw new IllegalArgumentException(
                    "Topological sort requires a directed graph."
            );
        }

        int n = graph.vertexCount();
        int[] indegree = new int[n];

        // Count incoming edges for every vertex
        for (Edge edge : graph.edges()) {
            indegree[edge.to]++;
        }

        // Start with vertices that have no incoming edges
        Queue<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> order = new ArrayList<>();

        while (!queue.isEmpty()) {
            int vertex = queue.remove();
            order.add(vertex);

            for (Edge edge : graph.neighbors(vertex)) {
                indegree[edge.to]--;

                if (indegree[edge.to] == 0) {
                    queue.add(edge.to);
                }
            }
        }

        // If not all vertices were processed, a cycle exists
        if (order.size() != n) {
            return Collections.emptyList();
        }

        return order;
    }
}
