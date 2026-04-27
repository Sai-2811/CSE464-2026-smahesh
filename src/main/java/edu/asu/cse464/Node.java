package edu.asu.cse464;

import java.util.Objects;

/**
 * Represents a single node in the graph. Introduced as part of Refactor #5
 * (Replace Primitive (String) with Object) so that paths can carry richer
 * semantics and produce trace output in the form {@code Node{label}}.
 */
public final class Node {

    private final String label;

    public Node(String label) {
        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("Node label cannot be null or empty");
        }
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return Objects.equals(label, node.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        return "Node{" + label + "}";
    }
}
