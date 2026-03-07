package edu.asu.cse464;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph {
    private final Set<String> nodes = new LinkedHashSet<>();
    private final Set<GraphEdge> edges = new LinkedHashSet<>();

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
