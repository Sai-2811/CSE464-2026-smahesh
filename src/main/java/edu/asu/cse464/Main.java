package edu.asu.cse464;

/**
 * End-to-end demo of all features:
 *   - parsing a DOT graph
 *   - add / remove APIs
 *   - GraphSearch with BFS, DFS, and RANDOM_WALK strategies
 */
public class Main {

    public static void main(String[] args) {
        try {
            Graph graph = GraphParser.parseGraph("src/main/resources/input.dot");

            System.out.println("=== Parsed Graph ===");
            System.out.println(graph);

            System.out.println("=== BFS Search a -> c ===");
            Path bfsPath = graph.GraphSearch("a", "c", Algorithm.BFS);
            System.out.println("Result: " + bfsPath);
            System.out.println("Labels: " + (bfsPath == null ? "null" : bfsPath.toLabelString()));

            System.out.println("\n=== DFS Search a -> c ===");
            Path dfsPath = graph.GraphSearch("a", "c", Algorithm.DFS);
            System.out.println("Result: " + dfsPath);
            System.out.println("Labels: " + (dfsPath == null ? "null" : dfsPath.toLabelString()));

            System.out.println("\n=== Random Walk Search a -> c (run 1) ===");
            System.out.println("random testing");
            Path rw1 = graph.GraphSearch("a", "c", Algorithm.RANDOM_WALK);
            System.out.println(rw1);

            System.out.println("\n=== Random Walk Search a -> c (run 2) ===");
            System.out.println("random testing");
            Path rw2 = graph.GraphSearch("a", "c", Algorithm.RANDOM_WALK);
            System.out.println(rw2);

            System.out.println("\n=== Random Walk Search a -> c (run 3) ===");
            System.out.println("random testing");
            Path rw3 = graph.GraphSearch("a", "c", Algorithm.RANDOM_WALK);
            System.out.println(rw3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
