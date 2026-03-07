package edu.asu.cse464;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph {
    private final Set<String> nodes = new LinkedHashSet<>();
    private final Set<GraphEdge> edges = new LinkedHashSet<>();

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

    public Set<String> getNodes() {
        return nodes;
    }

    public Set<GraphEdge> getEdges() {
        return edges;
    }

    public void outputGraph(String filepath) throws IOException {
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write(toString());
        }
    }

    @Override
    public String toString() {
        String nodeList = nodes.stream().collect(Collectors.joining(", "));
        String edgeList = edges.stream()
                .map(GraphEdge::toString)
                .collect(Collectors.joining(", "));

        return "Number of nodes: " + nodes.size() + "\n" +
               "Node labels: " + nodeList + "\n" +
               "Number of edges: " + edges.size() + "\n" +
               "Edges: " + edgeList + "\n";
    }
}
