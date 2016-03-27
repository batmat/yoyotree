package net.batmat;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Node {
    private String name;

    private Set<Node> descendants = new HashSet<Node>();

    public Node(String name) {
        this.name = name;
    }
    public boolean hasDescendant(Node lookedUp) {
        return hasDescendant0(lookedUp, new LinkedHashSet<>());
    }

    private boolean hasDescendant0(Node lookedUp, Set<Node> visited) {
        if(visited.contains(this)) {
            return false;
        }
        visited.add(this);
        if(descendants.contains(lookedUp))
        {
            return true;
        }
        for(Node descendant : descendants) {
            if(descendant.hasDescendant0(lookedUp, visited)){
                return true;
            }
        }
        return false;
    }

    public void addDirectDescendant(Node descendant) {
        descendants.add(descendant);
    }

    public String getName() {
        return name;
    }

    public String tree() {
        return tree0(0, new LinkedHashSet<>());
    }

    private String tree0(int shift, Set<Node> visited) {
        if(visited.contains(this))
        {
            return ""+new String(indent(shift))+name+" *** STOPPED LOOP\n";
        }
        visited.add(this);
        final StringBuilder builder = new StringBuilder();
        builder.append(indent(shift)).append(name).append('\n');
        for(Node descendant : descendants){
            builder.append(descendant.tree0(shift + 1, visited));
        }
        return builder.toString();
    }

    private char[] indent(int shift) {
        StringBuilder builder = new StringBuilder();
        for(int i=1;i<=shift;++i) {
            builder.append(' ');
        }
        builder.append(" |_");
        return builder.toString().toCharArray();
    }

    @Override
    public boolean equals(Object other) {
        return this.name.equals(((Node)other).getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
