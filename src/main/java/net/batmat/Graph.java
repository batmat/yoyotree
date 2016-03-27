package net.batmat;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Graph {
    Map<String, Node> nodeMap = new LinkedHashMap<>();

    public Graph(Collection<Node> nodes) {
        System.out.println("Received : "+nodes.size());
        nodeMap = nodes.stream().collect(
                Collectors.toMap(
                        Node::getName,
                        Function.identity()
                ));
    }

    public Node getNode(String nodeName) {
        return nodeMap.get(nodeName);
    }

    public int size() {
        return nodeMap.size();
    }
}
