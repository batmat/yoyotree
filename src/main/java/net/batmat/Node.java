package net.batmat;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class Node {
    private String name;

    private Set<Node> descendants = new LinkedHashSet<>();

    public Node(String name) {
        this.name = name;
    }

    public void addDirectDescendant(Node descendant) {
        descendants.add(descendant);
    }

    public boolean hasDescendant(Node lookedUp) {
        return hasDescendant0(lookedUp, new LinkedHashSet<>());
    }

    private boolean hasDescendant0(Node lookedUp, Set<Node> visited) {
        if (visited.contains(this)) {
            return false;
        }
        visited.add(this);

        Optional<Node> foundDescendant = descendants.stream()
                .filter(descendant -> descendant.equals(lookedUp))
                .findAny();
        if (foundDescendant.isPresent()) {
            return true;
        }

        Optional<Node> foundDescendantOfDescendant = descendants.stream()
                .filter(descendant -> descendant.hasDescendant0(lookedUp, visited))
                .findAny();
        if (foundDescendantOfDescendant.isPresent()) {
            return true;
        }

        return false;
    }

    public String getName() {
        return name;
    }

    public String tree() {
        return tree0(0, new LinkedHashSet<>());
    }

    public String tree0(int shift, Set<Node> visited) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent(shift)).append(name);
        if (visited.contains(this)) {
            builder.append("*** LOOP DETECTED\n");
            return builder.toString();
        }
        visited.add(this);
        builder.append('\n');
        for (Node descendant : descendants) {
            builder.append(indent(shift + 2)).append(descendant.tree0(shift + 1, visited));
        }
        return builder.toString();
    }

    private String indent(int shift) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < shift; ++i) {
            builder.append(' ');
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return name.equals(((Node) obj).getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
