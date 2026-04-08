package edu.asu.cse464;

import java.util.List;
import java.util.stream.Collectors;

public class Path {
    private List<String> nodes;

    public Path(List<String> nodes) {
        this.nodes = nodes;
    }

    public List<String> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return nodes.stream().collect(Collectors.joining(" -> "));
    }
}
