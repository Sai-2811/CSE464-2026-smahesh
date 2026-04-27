package edu.asu.cse464;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an ordered sequence of nodes describing a path from a source
 * node to a destination node in a {@link Graph}.
 */
public class Path {

    private final List<Node> nodes;

    public Path() {
        this.nodes = new ArrayList<>();
    }

    public Path(List<Node> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public List<Node> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public Node getLast() {
        if (nodes.isEmpty()) {
            return null;
        }
        return nodes.get(nodes.size() - 1);
    }

    public int size() {
        return nodes.size();
    }

    /**
     * Returns a new {@code Path} that contains all nodes of this path with
     * the specified node appended. The receiver is left unchanged.
     */
    public Path extend(Node next) {
        List<Node> extended = new ArrayList<>(this.nodes);
        extended.add(next);
        return new Path(extended);
    }

    /**
     * Returns a string of node labels joined by " -> ", which is the friendly
     * format used by tests and end-user output.
     */
    public String toLabelString() {
        return nodes.stream()
                .map(Node::getLabel)
                .collect(Collectors.joining(" -> "));
    }

    @Override
    public String toString() {
        return "Path{nodes=" + nodes + "}";
    }
}
