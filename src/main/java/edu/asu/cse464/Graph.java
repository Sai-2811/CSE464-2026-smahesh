package edu.asu.cse464;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * In-memory directed graph.
 *
 * <p>Search is delegated to {@link SearchStrategy} instances chosen at
 * runtime by {@link Algorithm} (Strategy Pattern). The strategies share an
 * algorithm skeleton via {@link AbstractGraphSearch} (Template Pattern).
 */
public class Graph {

    private final Set<String> nodes = new LinkedHashSet<>();
    private final Set<GraphEdge> edges = new LinkedHashSet<>();

    // ---------- node/edge mutators ----------

    public void addNode(String label) {
        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("Node label cannot be null or empty");
        }
        if (nodes.contains(label)) {
            throw new IllegalArgumentException("Duplicate node label: " + label);
        }
        nodes.add(label);
    }

    public void addNodes(String[] labels) {
        if (labels == null) {
            throw new IllegalArgumentException("Labels array cannot be null");
        }
        for (String label : labels) {
            addNode(label);
        }
    }

    public void addEdge(String srcLabel, String dstLabel) {
        if (!nodes.contains(srcLabel)) {
            throw new IllegalArgumentException("Source node does not exist: " + srcLabel);
        }
        if (!nodes.contains(dstLabel)) {
            throw new IllegalArgumentException("Destination node does not exist: " + dstLabel);
        }
        GraphEdge edge = new GraphEdge(srcLabel, dstLabel);
        if (edges.contains(edge)) {
            throw new IllegalArgumentException("Duplicate edge: " + edge);
        }
        edges.add(edge);
    }

    public void removeNode(String label) {
        if (!nodes.contains(label)) {
            throw new IllegalArgumentException("Node does not exist: " + label);
        }
        edges.removeIf(e -> e.getSrc().equals(label) || e.getDst().equals(label));
        nodes.remove(label);
    }

    public void removeNodes(String[] labels) {
        if (labels == null) {
            throw new IllegalArgumentException("Labels array cannot be null");
        }
        for (String label : labels) {
            removeNode(label);
        }
    }

    public void removeEdge(String srcLabel, String dstLabel) {
        GraphEdge edge = new GraphEdge(srcLabel, dstLabel);
        if (!edges.contains(edge)) {
            throw new IllegalArgumentException(
                    "Edge does not exist: " + srcLabel + " " + GraphConstants.EDGE_ARROW + " " + dstLabel);
        }
        edges.remove(edge);
    }

    // ---------- search (Strategy + Template) ----------

    /**
     * Picks a {@link SearchStrategy} based on {@code algo} and delegates.
     * This is the Strategy Pattern's selection point.
     */
    public Path GraphSearch(String src, String dst, Algorithm algo) {
        SearchStrategy strategy = createStrategy(algo);
        return strategy.search(this, src, dst);
    }

    private SearchStrategy createStrategy(Algorithm algo) {
        if (algo == null) {
            throw new IllegalArgumentException("Algorithm cannot be null");
        }
        switch (algo) {
            case BFS:         return new BFSSearch();
            case DFS:         return new DFSSearch();
            case RANDOM_WALK: return new RandomWalkSearch();
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + algo);
        }
    }

    // ---------- accessors ----------

    public Set<String> getNodes() {
        return nodes;
    }

    public Set<GraphEdge> getEdges() {
        return edges;
    }

    // ---------- output ----------

    public void outputGraph(String filepath) throws IOException {
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write(toString());
        }
    }

    public void outputDOTGraph(String path) throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(GraphConstants.DIGRAPH_HEADER + "\n");
            for (String node : nodes) {
                writer.write(GraphConstants.INDENT + node + GraphConstants.STATEMENT_TERMINATOR + "\n");
            }
            for (GraphEdge edge : edges) {
                writer.write(GraphConstants.INDENT + edge.getSrc() + " "
                        + GraphConstants.EDGE_ARROW + " " + edge.getDst()
                        + GraphConstants.STATEMENT_TERMINATOR + "\n");
            }
            writer.write(GraphConstants.DIGRAPH_FOOTER + "\n");
        }
    }

    @Override
    public String toString() {
        return buildSummary();
    }

    // ---------- private formatting helpers (Refactor #4) ----------

    private String buildSummary() {
        return "Number of nodes: " + nodes.size() + "\n"
             + "Node labels: " + formatNodeList() + "\n"
             + "Number of edges: " + edges.size() + "\n"
             + "Edges: " + formatEdgeList() + "\n";
    }

    private String formatNodeList() {
        return nodes.stream().collect(Collectors.joining(", "));
    }

    private String formatEdgeList() {
        return edges.stream().map(GraphEdge::toString).collect(Collectors.joining(", "));
    }
}
