import graph.Graph;
import mst.Kruskal;
import mst.Prim;
import mst.MST;
import connectivity.TopologicalSort;
import connectivity.SCC;
import shortestpath.Dijkstra;
import experiments.GraphGenerator;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(5, false);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 8);
        graph.addEdge(3, 4, 3);

        MST kruskal = Kruskal.run(graph);
        MST prim = Prim.run(graph);

        System.out.println(
                "Kruskal weight: " + kruskal.totalWeight
        );

        System.out.println(
                "Prim weight: " + prim.totalWeight
        );

        System.out.println(
                "Connected: " + kruskal.connected
        );

        System.out.println(
                "Same MST weight: " +
                        (kruskal.totalWeight == prim.totalWeight)
        );


        // Test topological sorting
        Graph dag = new Graph(5, true);

        dag.addEdge(0, 1, 1);
        dag.addEdge(0, 2, 1);
        dag.addEdge(1, 3, 1);
        dag.addEdge(2, 3, 1);

        System.out.println(
                "Topological order: " +
                        TopologicalSort.run(dag)
        );

        // Test strongly connected components
        Graph directed = new Graph(5, true);

        directed.addEdge(0, 1, 1);
        directed.addEdge(1, 0, 1);
        directed.addEdge(1, 2, 1);
        directed.addEdge(2, 3, 1);
        directed.addEdge(3, 2, 1);

        System.out.println(
                "Strongly connected components: " +
                        SCC.kosaraju(directed)
        );

        // Test cycle detection
        Graph cyclic = new Graph(3, true);

        cyclic.addEdge(0, 1, 1);
        cyclic.addEdge(1, 2, 1);
        cyclic.addEdge(2, 0, 1);

        System.out.println(
                "Cycle detected: " +
                        TopologicalSort.run(cyclic).isEmpty()
        );


        System.out.println("\n--- Dijkstra ---");

        Dijkstra.Result result = Dijkstra.run(graph, 0);

        for (int v = 0; v < graph.vertexCount(); v++) {
            if (result.distances[v] == Long.MAX_VALUE) {
                System.out.println(
                        "0 -> " + v + ": unreachable"
                );
            } else {
                System.out.println(
                        "0 -> " + v
                                + ": distance=" + result.distances[v]
                                + ", path=" + result.pathTo(v)
                );
            }
        }


        Graph generated =
                GraphGenerator.connectedUndirected(10, 30, 42L);

        System.out.println(
                "Generated vertices: " +
                        generated.vertexCount()
        );

        System.out.println(
                "Generated edges: " +
                        generated.edges().size()
        );

        System.out.println(
                "Kruskal MST connected: " +
                        Kruskal.run(generated).connected
        );


    }
}
