package net.batmat;


import org.junit.Test;

import java.io.File;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class GraphTest {

    @Test
    public void simpleDependencies() throws Exception {
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        A.addDirectDescendant(B);
        B.addDirectDescendant(C);

        assertTrue(A.hasDescendant(B));
        assertTrue(B.hasDescendant(C));
        assertTrue(A.hasDescendant(C));
    }

    @Test
    public void tree() throws Exception {
        GraphLoader loader = new FileGraphLoader(new File("list"));

        Graph graph = loader.get();
        Node actcom = graph.getNode("ACTCOM");
        actcom.tree();
    }

    @Test
    public void simpleCycle() throws Exception {
        Graph graph = new Graph();
        graph.addDependency("A", "B");
        graph.addDependency("B", "A");

        Node a = graph.getNode("A");
        Node b = graph.getNode("B");

        assertTrue(a.hasDescendant(b));

        CircularDependencyFinder finder = new CircularDependencyFinder(graph);

        assertThat(
                finder.getCircularDependenciesForNode(a)
                        .stream()
                        .map(circularDependency -> circularDependency.toString())
                        .collect(Collectors.toList())
        ).contains("A->B->A");
    }

    @Test
    public void findCycles() throws Exception {
        Graph graph = new Graph();
        graph.addDependency("A", "B");
        graph.addDependency("B", "C");
        graph.addDependency("A", "D");
        graph.addDependency("D", "A");
        graph.addDependency("C", "F");
        graph.addDependency("F", "G");
        graph.addDependency("G", "H");
        graph.addDependency("G", "C");
        graph.addDependency("Z", "Y");
        graph.addDependency("B", "A");


        Node nodeA = graph.getNode("A");
        Node b = graph.getNode("B");
        Node nodeC = graph.getNode("C");
        Node nodeZ = graph.getNode("Z");

        assertTrue(nodeA.hasDescendant(b));
        assertTrue(nodeA.hasDescendant(nodeC));

        CircularDependencyFinder cycleFinder = new CircularDependencyFinder(graph);

        assertThat(
                cycleFinder.getCircularDependenciesForNode(nodeA)
                        .stream()
                        .map(circularDependency -> circularDependency.toString())
                        .collect(Collectors.toList())
        ).contains("A->D->A", "A->B->A");
        assertThat(
                cycleFinder.getCircularDependenciesForNode(nodeC)
                        .stream()
                        .map(circularDependency -> circularDependency.toString())
                        .collect(Collectors.toList())
        ).contains("C->F->G->C");

        assertThat(cycleFinder.getCircularDependenciesForNode(nodeZ)).isEmpty();
    }

    @Test
    public void simpleGraphLoad() throws Exception {

        GraphLoader loader = new FileGraphLoader(new File("list2"));
        Graph graph = loader.get();

        Node actcom = graph.getNode("ACTCOM");

        assertTrue(actcom.hasDescendant(new Node("COMPO")));
        assertTrue(actcom.hasDescendant(new Node("PPGEN")));
    }

    @Test
    public void graphLoad() throws Exception {

        GraphLoader loader = new FileGraphLoader(new File("list"));
        Graph graph = loader.get();

        Node actcom = graph.getNode("ACTCOM");

        assertTrue(actcom.hasDescendant(new Node("COMPO")));
        assertTrue(actcom.hasDescendant(new Node("PPGEN")));
    }
}
