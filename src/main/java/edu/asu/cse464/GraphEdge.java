package edu.asu.cse464;

import java.util.Objects;

public class GraphEdge {
    private final String src;
    private final String dst;

    public GraphEdge(String src, String dst) {
        this.src = src;
        this.dst = dst;
    }

    public String getSrc() {
        return src;
    }

    public String getDst() {
        return dst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphEdge)) return false;
        GraphEdge edge = (GraphEdge) o;
        return Objects.equals(src, edge.src) && Objects.equals(dst, edge.dst);
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, dst);
    }

    @Override
    public String toString() {
        return src + " -> " + dst;
    }
}
