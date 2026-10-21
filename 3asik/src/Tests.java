
import graph.Graph;
import mst.Kruskal;
import mst.Prim;
import mst.MST;
import connectivity.TopologicalSort;
import connectivity.SCC;
import shortestpath.Dijkstra;

public class Tests {

    public static void main(String[] args) {

        // Test 1: Single vertex
        Graph single = new Graph(1, false);

        System.out.println(
                "Single vertex MST: " +
                        Kruskal.run(single).totalWeight
        );

        // Test 2: Disconnected graph
        Graph disconnected = new Graph(4, false);
        disconnected.addEdge(0, 1, 5);
        disconnected.addEdge(2, 3, 7);

        System.out.println(
                "Disconnected Kruskal: " +
                        Kruskal.run(disconnected).connected
        );

        System.out.println(
                "Disconnected Prim: " +
                        Prim.run(disconnected).connected
        );

        // Test 3: Cycle detection
        Graph cyclic = new Graph(3, true);
        cyclic.addEdge(0, 1, 1);
        cyclic.addEdge(1, 2, 1);
        cyclic.addEdge(2, 0, 1);

        System.out.println(
                "Cycle detected: " +
                        TopologicalSort.run(cyclic).isEmpty()
        );

        // Test 4: Strongly connected components
        Graph sccGraph = new Graph(3, true);
        sccGraph.addEdge(0, 1, 1);
        sccGraph.addEdge(1, 0, 1);
        sccGraph.addEdge(1, 2, 1);

        System.out.println(
                "SCC count: " +
                        SCC.kosaraju(sccGraph).size()
        );

        // Test 5: Unreachable vertex
        Graph pathGraph = new Graph(3, false);
        pathGraph.addEdge(0, 1, 4);

        Dijkstra.Result result = Dijkstra.run(pathGraph, 0);

        System.out.println(
                "Vertex 2 unreachable: " +
                        (result.distances[2] == Long.MAX_VALUE)
        );

        // Test 6: Duplicate edge weights
        Graph equalWeights = new Graph(3, false);
        equalWeights.addEdge(0, 1, 5);
        equalWeights.addEdge(1, 2, 5);
        equalWeights.addEdge(0, 2, 5);

        MST k = Kruskal.run(equalWeights);
        MST p = Prim.run(equalWeights);

        System.out.println(
                "Equal weights MST match: " +
                        (k.totalWeight == p.totalWeight)
        );
    }
}
