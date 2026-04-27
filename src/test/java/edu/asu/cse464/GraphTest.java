package edu.asu.cse464;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    // ========== Part 1 Tests ==========

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

    // ========== Part 2: Remove APIs ==========

    @Test
    public void testRemoveNodeAndEdges() {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"a", "b", "c"});
        graph.addEdge("a", "b");
        graph.addEdge("b", "c");

        graph.removeNode("b");

        assertFalse(graph.getNodes().contains("b"));
        assertEquals(2, graph.getNodes().size());
        assertEquals(0, graph.getEdges().size());
    }

    @Test
    public void testRemoveNodeNotExist() {
        Graph graph = new Graph();
        graph.addNode("a");
        assertThrows(IllegalArgumentException.class, () -> graph.removeNode("z"));
    }

    @Test
    public void testRemoveNodes() {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"a", "b", "c", "d"});
        graph.addEdge("a", "b");
        graph.addEdge("c", "d");

        graph.removeNodes(new String[]{"a", "c"});

        assertEquals(2, graph.getNodes().size());
        assertTrue(graph.getNodes().contains("b"));
        assertTrue(graph.getNodes().contains("d"));
        assertEquals(0, graph.getEdges().size());
    }

    @Test
    public void testRemoveNodesNotExist() {
        Graph graph = new Graph();
        graph.addNode("a");
        assertThrows(IllegalArgumentException.class,
                () -> graph.removeNodes(new String[]{"a", "x"}));
    }

    @Test
    public void testRemoveEdge() {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"a", "b", "c"});
        graph.addEdge("a", "b");
        graph.addEdge("b", "c");

        graph.removeEdge("a", "b");

        assertEquals(1, graph.getEdges().size());
        assertFalse(graph.getEdges().contains(new GraphEdge("a", "b")));
        assertTrue(graph.getEdges().contains(new GraphEdge("b", "c")));
    }

    @Test
    public void testRemoveEdgeNotExist() {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"a", "b"});
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge("a", "b"));
    }

    // ========== Part 2/3: Graph Search via Strategy ==========

    @Test
    public void testBFSPathExists() throws Exception {
        Graph graph = GraphParser.parseGraph("src/test/resources/test1.dot");
        Path path = graph.GraphSearch("a", "d", Algorithm.BFS);
        assertNotNull(path);
        List<Node> nodes = path.getNodes();
        assertEquals("a", nodes.get(0).getLabel());
        assertEquals("d", nodes.get(nodes.size() - 1).getLabel());
    }

    @Test
    public void testDFSPathExists() throws Exception {
        Graph graph = GraphParser.parseGraph("src/test/resources/test1.dot");
        Path path = graph.GraphSearch("a", "d", Algorithm.DFS);
        assertNotNull(path);
        List<Node> nodes = path.getNodes();
        assertEquals("a", nodes.get(0).getLabel());
        assertEquals("d", nodes.get(nodes.size() - 1).getLabel());
    }

    @Test
    public void testBFSPathNotExists() {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"a", "b"});
        Path path = graph.GraphSearch("a", "b", Algorithm.BFS);
        assertNull(path);
    }

    @Test
    public void testDFSPathNotExists() {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"a", "b"});
        Path path = graph.GraphSearch("a", "b", Algorithm.DFS);
        assertNull(path);
    }

    @Test
    public void testSearchSameNode() {
        Graph graph = new Graph();
        graph.addNode("a");
        Path path = graph.GraphSearch("a", "a", Algorithm.BFS);
        assertNotNull(path);
        assertEquals(1, path.getNodes().size());
    }

    @Test
    public void testSearchInvalidNode() {
        Graph graph = new Graph();
        graph.addNode("a");
        assertThrows(IllegalArgumentException.class,
                () -> graph.GraphSearch("a", "z", Algorithm.BFS));
    }

    // ========== Part 3: Template / Strategy / Random Walk ==========

    @Test
    public void testBFSStrategyDirectly() throws Exception {
        Graph graph = GraphParser.parseGraph("src/main/resources/input.dot");
        SearchStrategy bfs = new BFSSearch();
        Path path = bfs.search(graph, "a", "h");
        assertNotNull(path);
        assertEquals("a", path.getNodes().get(0).getLabel());
        assertEquals("h", path.getLast().getLabel());
    }

    @Test
    public void testDFSStrategyDirectly() throws Exception {
        Graph graph = GraphParser.parseGraph("src/main/resources/input.dot");
        SearchStrategy dfs = new DFSSearch();
        Path path = dfs.search(graph, "a", "h");
        assertNotNull(path);
        assertEquals("a", path.getNodes().get(0).getLabel());
        assertEquals("h", path.getLast().getLabel());
    }

    @Test
    public void testRandomWalkFindsPath() throws Exception {
        Graph graph = GraphParser.parseGraph("src/main/resources/input.dot");
        // Seed for deterministic test
        SearchStrategy rw = new RandomWalkSearch(new Random(42));
        Path path = rw.search(graph, "a", "c");
        assertNotNull(path);
        assertEquals("a", path.getNodes().get(0).getLabel());
        assertEquals("c", path.getLast().getLabel());
    }

    @Test
    public void testRandomWalkViaEnum() throws Exception {
        Graph graph = GraphParser.parseGraph("src/main/resources/input.dot");
        Path path = graph.GraphSearch("a", "c", Algorithm.RANDOM_WALK);
        assertNotNull(path);
        assertEquals("a", path.getNodes().get(0).getLabel());
        assertEquals("c", path.getLast().getLabel());
    }

    @Test
    public void testNodeEqualsAndHash() {
        Node n1 = new Node("a");
        Node n2 = new Node("a");
        Node n3 = new Node("b");
        assertEquals(n1, n2);
        assertEquals(n1.hashCode(), n2.hashCode());
        assertNotEquals(n1, n3);
    }

    @Test
    public void testPathToStringFormat() {
        Path path = new Path();
        path.addNode(new Node("a"));
        path.addNode(new Node("b"));
        assertEquals("Path{nodes=[Node{a}, Node{b}]}", path.toString());
        assertEquals("a -> b", path.toLabelString());
    }
}
