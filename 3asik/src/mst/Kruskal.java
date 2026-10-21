
package mst;

import dsu.DSU;
import graph.Edge;
import graph.Graph;
import java.util.*;

public class Kruskal {

    public static MST run(Graph graph) {
        if (graph.isDirected()) {
            throw new IllegalArgumentException(
                    "Kruskal requires an undirected graph."
            );
        }

        List<Edge> sorted = new ArrayList<>(graph.edges());

        sorted.sort(Comparator.comparingInt(e -> e.weight));

        DSU dsu = new DSU(graph.vertexCount());

        List<Edge> chosen = new ArrayList<>();
        long total = 0;
        long operations = 0;

        for (Edge edge : sorted) {
            operations++;

            if (dsu.union(edge.from, edge.to)) {
                chosen.add(edge);
                total += edge.weight;

                if (chosen.size() == graph.vertexCount() - 1) {
                    break;
                }
            }
        }

        return new MST(
                chosen,
                total,
                dsu.components() == 1,
                operations
        );
    }
}
