package experiments;

import graph.Graph;
import mst.Kruskal;
import mst.Prim;
import connectivity.TopologicalSort;
import connectivity.SCC;
import shortestpath.Dijkstra;

import java.util.Locale;

public class Benchmark {

    private interface Task {
        long run();
    }

    private static void measure(
            String algorithm,
            int vertices,
            int edges,
            Task task) {

        task.run(); // Warm-up

        long totalTime = 0;
        long operations = 0;
        int repetitions = 5;

        for (int i = 0; i < repetitions; i++) {
            long start = System.nanoTime();

            operations = task.run();

            long end = System.nanoTime();
            totalTime += end - start;
        }

        long averageTime = totalTime / repetitions;

        System.out.printf(
                Locale.US,
                "%s,%d,%d,%d,%d%n",
                algorithm,
                vertices,
                edges,
                averageTime,
                operations
        );
    }

    public static void main(String[] args) {

        int[][] configurations = {
                {10, 30},
                {100, 500},
                {1000, 5000}
        };

        System.out.println(
                "algorithm,vertices,edges,avg_time_ns,operations"
        );

        for (int[] config : configurations) {

            int vertices = config[0];
            int edges = config[1];

            Graph undirected =
                    GraphGenerator.connectedUndirected(
                            vertices,
                            edges,
                            20261021L + vertices
                    );

            measure("Kruskal", vertices, edges,
                    () -> Kruskal.run(undirected).operations);

            measure("Prim", vertices, edges,
                    () -> Prim.run(undirected).operations);

            measure("Dijkstra", vertices, edges,
                    () -> Dijkstra.run(undirected, 0).operations);

            int directedEdges = Math.min(
                    edges,
                    vertices * (vertices - 1) / 2
            );

            Graph dag = GraphGenerator.directed(
                    vertices, directedEdges,
                    1000L + vertices, true
            );

            measure("TopologicalSort", vertices, directedEdges,
                    () -> {
                        TopologicalSort.run(dag);
                        return vertices + directedEdges;
                    });

            Graph directed = GraphGenerator.directed(
                    vertices, directedEdges,
                    2000L + vertices, false
            );

            measure("SCC-Kosaraju", vertices, directedEdges,
                    () -> {
                        SCC.kosaraju(directed);
                        return vertices + directedEdges;
                    });
        }
    }
}
