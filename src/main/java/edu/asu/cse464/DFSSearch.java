package edu.asu.cse464;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * DFS implementation of the search template. The frontier is a LIFO stack,
 * so the most recently extended path is explored first — diving deep before
 * backtracking.
 */
public class DFSSearch extends AbstractGraphSearch {

    private final Deque<Path> frontier = new ArrayDeque<>();

    @Override
    protected void initFrontier(Path startPath) {
        frontier.clear();
        frontier.push(startPath);
    }

    @Override
    protected void addToFrontier(Path path) {
        frontier.push(path);
    }

    @Override
    protected Path fetchNext() {
        return frontier.pop();
    }

    @Override
    protected boolean hasNext() {
        return !frontier.isEmpty();
    }
}
