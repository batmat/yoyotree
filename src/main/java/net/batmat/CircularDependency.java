package net.batmat;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CircularDependency {
    private List<Node> circle;

    public CircularDependency(List<Node> path) {
        this.circle = path;
    }

    @Override
    public String toString() {
        return circle.stream()
                .map(node -> node.getName())
                .collect(Collectors.joining("->"));
    }
}
