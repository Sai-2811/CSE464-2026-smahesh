package edu.asu.cse464;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A search strategy that just picks a random path to explore next.
 * It uses a simple list to hold the paths and picks one randomly each time.
 * It's cool because it gives a different path every time you run it!
 */
public class RandomWalkSearch extends AbstractGraphSearch {

    private final List<Path> frontier = new ArrayList<>();
    private final Random random;

    public RandomWalkSearch() {
        this(new Random());
    }

    /** Test-only constructor; lets callers pin the seed for determinism. */
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
