package edu.asu.cse464;

import java.util.HashSet;
import java.util.Set;

/**
 * This is the base class for searching graphs using the Template Method pattern.
 *
 * The main search logic is locked in the search() method so we don't mess it up:
 * 1. Check if the nodes actually exist.
 * 2. Start the list with the source node.
 * 3. Keep pulling paths, check if we reached the end, and if not,
 *    add all unvisited neighbors back to our list.
 *
 * Subclasses just need to tell it:
 * - How to start the list (initFrontier)
 * - How to pick the next path (fetchNext)
 * - How to add a path back (addToFrontier)
 *
 * For example, BFS uses a Queue, DFS uses a Stack, and RandomWalk just picks randomly!
 */
public abstract class AbstractGraphSearch implements SearchStrategy {

    /**
     * Template method. Defines the invariant skeleton of the search.
     * Marked {@code final} so subclasses cannot accidentally change the
     * algorithm shape — only the primitive operations.
     */
    private void validateNodeExists(Graph graph, String label, String type) {
        if (!graph.getNodes().contains(label)) {
            throw new IllegalArgumentException(type + " node does not exist: " + label);
        }
    }

    @Override
    public final Path search(Graph graph, String srcLabel, String dstLabel) {
        validateNodeExists(graph, srcLabel, "Source");
        validateNodeExists(graph, dstLabel, "Destination");

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
