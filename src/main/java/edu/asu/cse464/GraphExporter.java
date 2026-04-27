package edu.asu.cse464;

import java.io.IOException;

/**
 * Exports a {@link Graph} to graphics by invoking the system {@code dot}
 * command. Refactor #3 split process construction and execution into
 * small, focused helpers.
 */
public class GraphExporter {

    public static void outputGraphics(Graph graph, String path, String format)
            throws IOException, InterruptedException {

        validateFormat(format);

        String dotPath = derivedDotPath(path, format);
        graph.outputDOTGraph(dotPath);

        runGraphviz(dotPath, path, format);
    }

    private static void validateFormat(String format) {
        if (!GraphConstants.PNG_FORMAT.equalsIgnoreCase(format)) {
            throw new IllegalArgumentException("Only png format is supported");
        }
    }

    private static String derivedDotPath(String outputPath, String format) {
        return outputPath.replace("." + format, GraphConstants.DOT_EXTENSION);
    }

    private static void runGraphviz(String dotPath, String outputPath, String format)
            throws IOException, InterruptedException {

        ProcessBuilder pb = buildGraphvizProcess(dotPath, outputPath, format);
        pb.inheritIO();
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Graphviz failed with exit code: " + exitCode);
        }
    }

    private static ProcessBuilder buildGraphvizProcess(String dotPath, String outputPath, String format) {
        return new ProcessBuilder(
                GraphConstants.GRAPHVIZ_COMMAND,
                GraphConstants.FORMAT_FLAG_PREFIX + format,
                dotPath,
                GraphConstants.OUTPUT_FLAG,
                outputPath
        );
    }
}
