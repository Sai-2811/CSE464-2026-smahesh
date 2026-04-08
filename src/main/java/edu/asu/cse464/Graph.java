package edu.asu.cse464;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Arrays;
import java.util.ArrayList;

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
        nodes.remove(label);
        edges.removeIf(edge -> edge.getSrc().equals(label) || edge.getDst().equals(label));
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
            throw new IllegalArgumentException("Edge does not exist: " + edge);
        }
        edges.remove(edge);
    }

    public Path GraphSearch(Node src, Node dst) {
        if (!nodes.contains(src.getLabel()) || !nodes.contains(dst.getLabel())) return null;
        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        queue.add(Arrays.asList(src.getLabel()));
        visited.add(src.getLabel());
        
        while (!queue.isEmpty()) {
            List<String> path = queue.poll();
            String current = path.get(path.size() - 1);
            
            if (current.equals(dst.getLabel())) {
                return new Path(path);
            }
            
            for (GraphEdge edge : edges) {
                if (edge.getSrc().equals(current) && !visited.contains(edge.getDst())) {
                    visited.add(edge.getDst());
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(edge.getDst());
                    queue.add(newPath);
                }
            }
        }
        return null;
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

    public void outputDOTGraph(String path) throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write("digraph G {\n");
            for (String node : nodes) {
                writer.write("    " + node + ";\n");
            }
            for (GraphEdge edge : edges) {
                writer.write("    " + edge.getSrc() + " -> " + edge.getDst() + ";\n");
            }
            writer.write("}\n");
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
