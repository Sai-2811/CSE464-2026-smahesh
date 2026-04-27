package edu.asu.cse464;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Parses a DOT-format file into a {@link Graph}. Refactor #2 broke the
 * original monolithic loop into small, named helpers so each step has a
 * single responsibility.
 */
public class GraphParser {

    public static Graph parseGraph(String filepath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filepath));
        Graph graph = new Graph();

        for (String rawLine : lines) {
            String line = rawLine.trim();

            if (isSkippable(line)) {
                continue;
            }

            line = stripTerminator(line);

            if (isEdgeLine(line)) {
                handleEdgeLine(graph, line, rawLine);
            } else {
                handleNodeLine(graph, line);
            }
        }
        return graph;
    }

    private static boolean isSkippable(String line) {
        return line.isEmpty()
                || line.startsWith(GraphConstants.COMMENT_PREFIX)
                || line.equals(GraphConstants.DIGRAPH_HEADER)
                || line.equals(GraphConstants.OPEN_BRACE)
                || line.equals(GraphConstants.CLOSE_BRACE);
    }

    private static String stripTerminator(String line) {
        return line.replace(GraphConstants.STATEMENT_TERMINATOR, "").trim();
    }

    private static boolean isEdgeLine(String line) {
        return line.contains(GraphConstants.EDGE_ARROW);
    }

    private static void handleEdgeLine(Graph graph, String line, String rawLine) {
        String[] parts = line.split(GraphConstants.EDGE_ARROW);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid DOT edge line: " + rawLine);
        }
        String src = parts[0].trim();
        String dst = parts[1].trim();

        ensureNode(graph, src);
        ensureNode(graph, dst);

        GraphEdge edge = new GraphEdge(src, dst);
        if (!graph.getEdges().contains(edge)) {
            graph.getEdges().add(edge);
        }
    }

    private static void handleNodeLine(Graph graph, String line) {
        ensureNode(graph, line);
    }

    private static void ensureNode(Graph graph, String label) {
        if (!graph.getNodes().contains(label)) {
            graph.getNodes().add(label);
        }
    }
}
