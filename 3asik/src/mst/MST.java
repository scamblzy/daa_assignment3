
package mst;

import graph.Edge;
import java.util.*;

public class MST {
    public final List<Edge> edges;
    public final long totalWeight;
    public final boolean connected;
    public final long operations;

    public MST(
            List<Edge> edges,
            long totalWeight,
            boolean connected,
            long operations) {
        this.edges = Collections.unmodifiableList(
                new ArrayList<>(edges)
        );
        this.totalWeight = totalWeight;
        this.connected = connected;
        this.operations = operations;
    }
}
