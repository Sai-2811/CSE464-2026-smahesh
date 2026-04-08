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
    public void testRemoveNode() {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"a", "b", "c"});
        graph.addEdge("a", "b");
        graph.addEdge("b", "c");
        graph.removeNode("b");
        assertFalse(graph.getNodes().contains("b"));
        assertEquals(0, graph.getEdges().size());
        assertThrows(IllegalArgumentException.class, () -> graph.removeNode("z"));
    }

    @Test
    public void testRemoveNodes() {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"a", "b", "c"});
        graph.removeNodes(new String[]{"a", "b"});
        assertEquals(1, graph.getNodes().size());
        assertTrue(graph.getNodes().contains("c"));
    }

    @Test
    public void testRemoveEdge() {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"a", "b"});
        graph.addEdge("a", "b");
        graph.removeEdge("a", "b");
        assertEquals(0, graph.getEdges().size());
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge("a", "b"));
    }

    @Test
    public void testGraphSearchDFS() {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"A", "B", "C", "D"});
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "D");
        graph.addEdge("D", "C");
        
        Path path = graph.GraphSearch(new Node("A"), new Node("C"));
        assertNotNull(path);
        assertTrue(path.getNodes().size() >= 3);
        
        Path noPath = graph.GraphSearch(new Node("C"), new Node("A"));
        assertNull(noPath);
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
