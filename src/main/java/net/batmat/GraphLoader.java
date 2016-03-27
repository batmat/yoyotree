package net.batmat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GraphLoader {

    Graph graph = new Graph();

    public GraphLoader(File path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));

        String line = null;
        while (null != (line = reader.readLine())) {
            String[] split = line.split("->");
            if (split.length != 2) {
                System.err.println("ignored line: " + line);
                continue;
            }
            String currentNodeName = split[0];
            String descendant = split[1];

            Node currentNode = getNode(currentNodeName);
            Node descendantNode = getNode(descendant);

            currentNode.addDirectDescendant(descendantNode);
            graph.addNode(currentNode);
            graph.addNode(descendantNode);
        }
    }

    private Node getNode(String nodeName) {
        Node node = graph.getNode(nodeName);
        if (node == null) {
            node = new Node(nodeName);
        }
        return node;
    }

    public Graph get() {
        System.out.println(graph.nodes.size());
        return graph;
    }
}
