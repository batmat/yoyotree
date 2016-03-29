package net.batmat;


import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphTest {

    @Test
    public void testAddNode() throws Exception {
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        A.addDirectDescendant(B);
        B.addDirectDescendant(C);

        assertTrue(A.hasDescendant(B));
        assertTrue(B.hasDescendant(C));
        assertTrue(A.hasDescendant(C));

        System.out.println(A.tree());

    }

    @Test
    public void testTree() throws Exception {
        GraphLoader loader = new FileGraphLoader(new File("list"));

        Graph graph = loader.get();
        System.out.println(graph.size());
        Node actcom = graph.getNode("ACTCOM");
        actcom.tree();
    }

    @Test
    public void circleSimple() throws Exception {
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
    public void testLoad() throws Exception {
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

        CircularDependencyFinder finder = new CircularDependencyFinder(graph);

        assertThat(
                finder.getCircularDependenciesForNode(nodeA)
                        .stream()
                        .map(circularDependency -> circularDependency.toString())
                        .collect(Collectors.toList())
        ).contains("A->D->A", "A->B->A");
        assertThat(
                finder.getCircularDependenciesForNode(nodeC)
                        .stream()
                        .map(circularDependency -> circularDependency.toString())
                        .collect(Collectors.toList())
        ).contains("C->F->G->C");

        assertThat(finder.getCircularDependenciesForNode(nodeZ)).isEmpty();

    }

    @Test
    public void simple() throws Exception {

        GraphLoader loader = new FileGraphLoader(new File("list2"));
        Graph graph = loader.get();

        Node actcom = graph.getNode("ACTCOM");

        assertTrue(actcom.hasDescendant(new Node("COMPO")));
        assertTrue(actcom.hasDescendant(new Node("PPGEN")));
    }

    @Test
    public void testYoyo() throws Exception {

        GraphLoader loader = new FileGraphLoader(new File("list"));
        Graph graph = loader.get();

        Node actcom = graph.getNode("ACTCOM");

        assertTrue(actcom.hasDescendant(new Node("COMPO")));
        assertTrue(actcom.hasDescendant(new Node("PPGEN")));
    }
}
