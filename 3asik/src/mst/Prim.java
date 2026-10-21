
package mst;

import graph.Edge;
import graph.Graph;
import java.util.*;

public class Prim {

    private static class Candidate {
        final int vertex;
        final int parent;
        final int weight;

        Candidate(int vertex, int parent, int weight) {
            this.vertex = vertex;
            this.parent = parent;
            this.weight = weight;
        }
    }

    public static MST run(Graph graph) {
        if (graph.isDirected()) {
            throw new IllegalArgumentException(
                    "Prim requires an undirected graph."
            );
        }

        int n = graph.vertexCount();

        boolean[] visited = new boolean[n];

        PriorityQueue<Candidate> pq =
                new PriorityQueue<>(
                        Comparator.comparingInt(c -> c.weight)
                );

        List<Edge> chosen = new ArrayList<>();

        long total = 0;
        long operations = 0;

        for (int start = 0; start < n; start++) {
            if (visited[start]) {
                continue;
            }

            pq.add(new Candidate(start, -1, 0));

            while (!pq.isEmpty()) {
                Candidate c = pq.poll();
                operations++;

                if (visited[c.vertex]) {
                    continue;
                }

                visited[c.vertex] = true;

                if (c.parent != -1) {
                    chosen.add(
                            new Edge(c.parent, c.vertex, c.weight)
                    );
                    total += c.weight;
                }

                for (Edge edge : graph.neighbors(c.vertex)) {
                    operations++;

                    if (!visited[edge.to]) {
                        pq.add(
                                new Candidate(
                                        edge.to,
                                        c.vertex,
                                        edge.weight
                                )
                        );
                    }
                }
            }
        }

        boolean connected = chosen.size() == n - 1;

        return new MST(
                chosen,
                total,
                connected,
                operations
        );
    }
}
