package connectivity;

import graph.Edge;
import graph.Graph;
import java.util.*;

public class SCC {

    public static List<List<Integer>> kosaraju(Graph graph) {
        if (!graph.isDirected()) {
            throw new IllegalArgumentException(
                    "SCC requires a directed graph."
            );
        }

        int n = graph.vertexCount();
        boolean[] visited = new boolean[n];
        List<Integer> order = new ArrayList<>();

        // First DFS: record vertices by finishing time
        for (int v = 0; v < n; v++) {
            if (!visited[v]) {
                dfsOrder(graph, v, visited, order);
            }
        }

        // Build the reversed graph as an adjacency list
        List<List<Integer>> reverse = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            reverse.add(new ArrayList<>());
        }

        for (Edge edge : graph.edges()) {
            reverse.get(edge.to).add(edge.from);
        }

        // Second DFS: traverse reversed graph in reverse finish order
        Arrays.fill(visited, false);
        List<List<Integer>> components = new ArrayList<>();

        for (int i = order.size() - 1; i >= 0; i--) {
            int v = order.get(i);

            if (!visited[v]) {
                List<Integer> component = new ArrayList<>();
                dfsReverse(v, reverse, visited, component);
                components.add(component);
            }
        }

        return components;
    }

    private static void dfsOrder(
            Graph graph,
            int vertex,
            boolean[] visited,
            List<Integer> order) {

        visited[vertex] = true;

        for (Edge edge : graph.neighbors(vertex)) {
            if (!visited[edge.to]) {
                dfsOrder(graph, edge.to, visited, order);
            }
        }

        order.add(vertex);
    }

    private static void dfsReverse(
            int vertex,
            List<List<Integer>> reverse,
            boolean[] visited,
            List<Integer> component) {

        visited[vertex] = true;
        component.add(vertex);

        for (int next : reverse.get(vertex)) {
            if (!visited[next]) {
                dfsReverse(next, reverse, visited, component);
            }
        }
    }
}
