package net.batmat;


import org.junit.Test;

import java.io.File;

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
