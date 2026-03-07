package edu.asu.cse464;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    @Test
    public void testParseGraph() throws Exception {
        Graph graph = GraphParser.parseGraph("src/test/resources/test1.dot");

        assertEquals(4, graph.getNodes().size());
        assertEquals(3, graph.getEdges().size());
        assertTrue(graph.getNodes().contains("a"));
        assertTrue(graph.getNodes().contains("b"));
    }

    @Test
    public void testAddNode() {
        Graph graph = new Graph();
        graph.addNode("a");
        assertTrue(graph.getNodes().contains("a"));
    }

    @Test
    public void testAddDuplicateNode() {
        Graph graph = new Graph();
        graph.addNode("a");
        assertThrows(IllegalArgumentException.class, () -> graph.addNode("a"));
    }

    @Test
    public void testAddNodes() {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"a", "b", "c"});
        assertEquals(3, graph.getNodes().size());
    }

    @Test
    public void testAddEdge() {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"a", "b"});
        graph.addEdge("a", "b");

        assertEquals(1, graph.getEdges().size());
        assertTrue(graph.getEdges().contains(new GraphEdge("a", "b")));
    }

    @Test
    public void testDuplicateEdge() {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"a", "b"});
        graph.addEdge("a", "b");

        assertThrows(IllegalArgumentException.class, () -> graph.addEdge("a", "b"));
    }

    @Test
    public void testToStringOutput() throws Exception {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"a", "b"});
        graph.addEdge("a", "b");

        String output = graph.toString();
        String expected = Files.readString(Paths.get("src/test/resources/expected.txt"));

        assertEquals(expected, output);
    }

    @Test
    public void testOutputDOTGraph() throws Exception {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"a", "b"});
        graph.addEdge("a", "b");

        graph.outputDOTGraph("src/test/resources/generated.dot");
        String actual = Files.readString(Paths.get("src/test/resources/generated.dot"));

        assertTrue(actual.contains("a -> b;"));
    }
}
