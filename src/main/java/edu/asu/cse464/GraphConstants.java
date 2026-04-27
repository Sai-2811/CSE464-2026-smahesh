package edu.asu.cse464;

/**
 * Centralizes string literals used across DOT parsing and exporting.
 * Introduced as part of the Refactor #1 (Replace Magic Strings with Constants).
 */
public final class GraphConstants {

    private GraphConstants() {
        // utility class
    }

    public static final String DIGRAPH_HEADER = "digraph G {";
    public static final String DIGRAPH_FOOTER = "}";
    public static final String EDGE_ARROW = "->";
    public static final String INDENT = "    ";
    public static final String STATEMENT_TERMINATOR = ";";
    public static final String COMMENT_PREFIX = "//";
    public static final String OPEN_BRACE = "{";
    public static final String CLOSE_BRACE = "}";
    public static final String PNG_FORMAT = "png";
    public static final String DOT_EXTENSION = ".dot";
    public static final String GRAPHVIZ_COMMAND = "dot";
    public static final String FORMAT_FLAG_PREFIX = "-T";
    public static final String OUTPUT_FLAG = "-o";
}
