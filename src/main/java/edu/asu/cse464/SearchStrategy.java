package edu.asu.cse464;

/**
 * Strategy interface for graph-search algorithms. Concrete strategies
 * (BFS, DFS, random walk) implement this contract so that {@link Graph}
 * can pick a strategy at runtime based on the {@link Algorithm} parameter.
 */
public interface SearchStrategy {

    /**
     * Searches for a path from {@code src} to {@code dst} in {@code graph}.
     *
     * @return a {@link Path} from src to dst, or {@code null} if none exists.
     */
    Path search(Graph graph, String src, String dst);
}
