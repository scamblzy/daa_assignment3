package shortestpath;

import graph.Edge;
import graph.Graph;
import java.util.*;

public class Dijkstra {

    public static class Result {
        public final long[] distances;
        public final int[] previous;
        public final long operations;

        public Result(
                long[] distances,
                int[] previous,
                long operations) {
            this.distances = distances;
            this.previous = previous;
            this.operations = operations;
        }

        public List<Integer> pathTo(int target) {
            if (distances[target] == Long.MAX_VALUE) {
                return Collections.emptyList();
            }

            LinkedList<Integer> path = new LinkedList<>();

            for (int v = target; v != -1; v = previous[v]) {
                path.addFirst(v);
            }

            return path;
        }
    }

    private static class State {
        final int vertex;
        final long distance;

        State(int vertex, long distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    public static Result run(Graph graph, int source) {
        graph.checkVertex(source);

        int n = graph.vertexCount();

        long[] dist = new long[n];
        int[] prev = new int[n];

        Arrays.fill(dist, Long.MAX_VALUE);
        Arrays.fill(prev, -1);

        dist[source] = 0;

        PriorityQueue<State> pq =
                new PriorityQueue<>(
                        Comparator.comparingLong(
                                s -> s.distance
                        )
                );

        pq.add(new State(source, 0));

        long operations = 0;

        while (!pq.isEmpty()) {
            State current = pq.poll();
            operations++;

            if (current.distance != dist[current.vertex]) {
                continue;
            }

            for (Edge edge : graph.neighbors(current.vertex)) {
                operations++;

                long candidate =
                        current.distance + edge.weight;

                if (candidate < dist[edge.to]) {
                    dist[edge.to] = candidate;
                    prev[edge.to] = current.vertex;

                    pq.add(
                            new State(edge.to, candidate)
                    );
                }
            }
        }

        return new Result(dist, prev, operations);
    }
}
