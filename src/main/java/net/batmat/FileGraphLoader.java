package net.batmat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class FileGraphLoader implements GraphLoader {
    final Graph graph;
    private Map<String, Node> nodeMap = new LinkedHashMap<>();

    public FileGraphLoader(File list) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(list));

        String line = null;
        while (null != (line = reader.readLine())) {
            String[] split = line.split("->");
            if (split.length != 2) {
                System.err.println("Ignoring line " + line);
                continue;
            }
            String parent = split[0];
            String descendant = split[1];
            Node parentNode = getNode(parent);
            Node descendantNode = getNode(descendant);
            parentNode.addDirectDescendant(descendantNode);
        }

        graph = createGraph();
    }

    private Graph createGraph() {
        return new Graph(nodeMap.values()); // beuark! NOPE! DUPE!
    }

    private Node getNode(String nodeName) {
        Node found = nodeMap.get(nodeName);
        if (found == null) {
            found = new Node(nodeName);
        }
        nodeMap.put(nodeName, found);
        return found;
    }

    @Override
    public Graph get() {
        return graph;
    }
}
