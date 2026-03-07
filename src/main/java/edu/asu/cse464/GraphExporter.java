package edu.asu.cse464;

import java.io.IOException;

public class GraphExporter {

    public static void outputGraphics(Graph graph, String path, String format) throws IOException, InterruptedException {
        if (!format.equalsIgnoreCase("png")) {
            throw new IllegalArgumentException("Only png format is supported");
        }

        String dotPath = path.replace("." + format, ".dot");
        graph.outputDOTGraph(dotPath);

        ProcessBuilder pb = new ProcessBuilder(
                "dot",
                "-T" + format,
                dotPath,
                "-o",
                path
        );

        pb.inheritIO();
        Process process = pb.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("Graphviz failed with exit code: " + exitCode);
        }
    }
}
