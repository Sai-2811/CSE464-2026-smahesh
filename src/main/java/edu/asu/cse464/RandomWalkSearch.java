package edu.asu.cse464;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Random walk implementation of the search template. The frontier is a list
 * and the next path to explore is drawn uniformly at random. This produces
 * different traversal orders on each run while still leveraging the same
 * template skeleton as BFS and DFS.
 */
public class RandomWalkSearch extends AbstractGraphSearch {

    private final List<Path> frontier = new ArrayList<>();
    private final Random random;

    public RandomWalkSearch() {
        this(new Random());
    }

    /** Test-friendly constructor; lets callers pin the seed for determinism. */
    public RandomWalkSearch(Random random) {
        this.random = random;
    }

    @Override
    protected void initFrontier(Path startPath) {
        frontier.clear();
        frontier.add(startPath);
    }

    @Override
    protected void addToFrontier(Path path) {
        frontier.add(path);
    }

    @Override
    protected Path fetchNext() {
        int idx = random.nextInt(frontier.size());
        return frontier.remove(idx);
    }

    @Override
    protected boolean hasNext() {
        return !frontier.isEmpty();
    }
}
