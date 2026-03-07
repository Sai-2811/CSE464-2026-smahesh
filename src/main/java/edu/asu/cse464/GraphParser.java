package edu.asu.cse464;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GraphParser {

    public static Graph parseGraph(String filepath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filepath));
        Graph graph = new Graph();

        for (String rawLine : lines) {
            String line = rawLine.trim();

            if (line.isEmpty() || line.startsWith("//") || line.equals("digraph G {") || line.equals("{") || line.equals("}")) {
                continue;
            }

            line = line.replace(";", "").trim();

            if (line.contains("->")) {
                String[] parts = line.split("->");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid DOT edge line: " + rawLine);
                }

                String src = parts[0].trim();
                String dst = parts[1].trim();

                if (!graph.getNodes().contains(src)) {
                    graph.getNodes().add(src);
                }
                if (!graph.getNodes().contains(dst)) {
                    graph.getNodes().add(dst);
                }

                if (!graph.getEdges().contains(new GraphEdge(src, dst))) {
                    graph.getEdges().add(new GraphEdge(src, dst));
                }
            } else {
                if (!graph.getNodes().contains(line)) {
                    graph.getNodes().add(line);
                }
            }
        }

        return graph;
    }
}
