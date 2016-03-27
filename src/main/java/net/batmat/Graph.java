package net.batmat;

import java.util.*;

public class Graph {
    Map<String, Node> nodes = new LinkedHashMap<>();


    public Node getNode(String nodeName) {
        return nodes.get(nodeName);
    }

    public void addNode(Node node) {
        nodes.put(node.getName(), node);
    }

    public int size() {
        return nodes.size();
    }
}
