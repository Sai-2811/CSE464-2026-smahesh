package edu.asu.cse464;

public class Main {
    public static void main(String[] args) {
        try {
            Graph graph = GraphParser.parseGraph("src/main/resources/input.dot");

            System.out.println("=== Parsed Graph ===");
            System.out.println(graph);

            graph.addNode("x");
            graph.addNodes(new String[]{"y", "z"});
            graph.addEdge("x", "y");
            graph.addEdge("y", "z");

            System.out.println("=== Updated Graph ===");
            System.out.println(graph);

            graph.outputDOTGraph("output/output.dot");
            GraphExporter.outputGraphics(graph, "output/output.png", "png");

            System.out.println("DOT and PNG exported successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
