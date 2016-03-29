package net.batmat;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Graph {
    Map<String, Node> nodeMap = new LinkedHashMap<>();

    public Graph(Collection<Node> nodes) {
        nodeMap = nodes.stream().collect(
                Collectors.toMap(
                        Node::getName,
                        Function.identity()
                ));
    }

    public Graph() {
    }

    public Node getNode(String nodeName) {
        return nodeMap.get(nodeName);
    }

    public int size() {
        return nodeMap.size();
    }


    public void addDependency(String from, String to) {
        Node fromNode = getOrCreateNode(from);
        Node toNode = getOrCreateNode(to);
        fromNode.addDirectDescendant(toNode);
    }

    private Node getOrCreateNode(String nodeName) {
        Node node = getNode(nodeName);
        if(node==null) {
            node = new Node(nodeName);
        }
        nodeMap.put(nodeName, node);
        return node;
    }

    public Collection<Node> getNodes() {
        return nodeMap.values();
    }
}
