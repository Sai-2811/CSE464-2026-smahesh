package edu.asu.cse464;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * BFS implementation of the search template. The frontier is a FIFO queue,
 * so paths are explored in order of length — guaranteeing a shortest path.
 */
public class BFSSearch extends AbstractGraphSearch {

    private final Deque<Path> frontier = new ArrayDeque<>();

    @Override
    protected void initFrontier(Path startPath) {
        frontier.clear();
        frontier.addLast(startPath);
    }

    @Override
    protected void addToFrontier(Path path) {
        frontier.addLast(path);
    }

    @Override
    protected Path fetchNext() {
        return frontier.pollFirst();
    }

    @Override
    protected boolean hasNext() {
        return !frontier.isEmpty();
    }
}
