package net.batmat;

import java.util.*;

public class CircularDependencyFinder {

    Map<Node, List<CircularDependency>> circularDependencies = new LinkedHashMap<>();
    private Graph graph;

    public CircularDependencyFinder(Graph graph) {
        this.graph = graph;
        analyze(graph);
    }

    /**
     * Descends the whole tree
     *
     * @param graph
     */
    private void analyze(Graph graph) {

        for (Node node : graph.getNodes()) {

            List<CircularDependency> circles = getCircularDependencies(node, Collections.emptyList());
                this.circularDependencies.put(node, circles);
        }
    }

    private List<CircularDependency> getCircularDependencies(Node node, List<Node> path) {
        if (path.contains(node)) {
            path.add(node);
            ArrayList<CircularDependency> result = new ArrayList<>();
            result.add(new CircularDependency(path));
            return result;
        }
        List<Node> enrichedPath = new ArrayList<>(path);
        enrichedPath.add(node);
        List<CircularDependency> circles = new ArrayList<>();
        for (Node current : node.getDescendants()) {
            circles.addAll(getCircularDependencies(current, enrichedPath));
        }
        return circles;
    }

    public Collection<CircularDependency> getCircularDependenciesForNode(Node node) {
        return circularDependencies.get(node);
    }
}
