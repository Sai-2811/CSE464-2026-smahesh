package edu.asu.cse464;

import java.util.HashSet;
import java.util.Set;

/**
 * Template Method base class for frontier-based graph search.
 *
 * <p>The skeleton of the algorithm is fixed in {@link #search(Graph, String, String)}:
 * <ol>
 *   <li>Validate inputs.</li>
 *   <li>Initialize the frontier with the starting path.</li>
 *   <li>Loop: pull a path from the frontier, log it, return if it ends at
 *       the destination, otherwise extend it by every unvisited neighbor and
 *       push the extensions back into the frontier.</li>
 * </ol>
 *
 * <p>Subclasses customize three primitive operations:
 * <ul>
 *   <li>{@link #initFrontier(Path)} — how to seed the frontier.</li>
 *   <li>{@link #fetchNext()} — how to pick the next path to explore.</li>
 *   <li>{@link #addToFrontier(Path)} — how to put a new path back.</li>
 * </ul>
 *
 * <p>BFS uses a FIFO queue, DFS uses a LIFO stack, random walk samples
 * uniformly from the frontier — same skeleton, different policies.
 */
public abstract class AbstractGraphSearch implements SearchStrategy {

    /**
     * Template method. Defines the invariant skeleton of the search.
     * Marked {@code final} so subclasses cannot accidentally change the
     * algorithm shape — only the primitive operations.
     */
    @Override
    public final Path search(Graph graph, String srcLabel, String dstLabel) {
        if (!graph.getNodes().contains(srcLabel)) {
            throw new IllegalArgumentException("Source node does not exist: " + srcLabel);
        }
        if (!graph.getNodes().contains(dstLabel)) {
            throw new IllegalArgumentException("Destination node does not exist: " + dstLabel);
        }

        Node src = new Node(srcLabel);
        Node dst = new Node(dstLabel);

        Path startPath = new Path();
        startPath.addNode(src);

        Set<String> visited = new HashSet<>();
        visited.add(srcLabel);

        initFrontier(startPath);

        while (hasNext()) {
            Path current = fetchNext();
            onVisit(current);

            Node tail = current.getLast();
            if (tail.equals(dst)) {
                return current;
            }

            for (GraphEdge edge : graph.getEdges()) {
                if (edge.getSrc().equals(tail.getLabel())
                        && !visited.contains(edge.getDst())) {
                    visited.add(edge.getDst());
                    Path extended = current.extend(new Node(edge.getDst()));
                    addToFrontier(extended);
                }
            }
        }
        return null;
    }

    /** Hook called whenever a path is dequeued. Default prints the trace. */
    protected void onVisit(Path path) {
        System.out.println("visiting " + path);
    }

    /** Seed the frontier with the starting path. */
    protected abstract void initFrontier(Path startPath);

    /** Add a candidate path to the frontier. */
    protected abstract void addToFrontier(Path path);

    /** Remove and return the next path to explore. */
    protected abstract Path fetchNext();

    /** True iff the frontier still contains paths to explore. */
    protected abstract boolean hasNext();
}
