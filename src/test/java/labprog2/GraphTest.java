package labprog2;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import labprog2.model.Airport;
import labprog2.util.graph.Edge;
import labprog2.util.graph.Graph;
import labprog2.util.graph.MatrixGraph;
import labprog2.util.graph.Node;
import labprog2.util.graph.exceptions.EdgeNotPresentException;
import labprog2.util.graph.exceptions.FullGraphException;
import labprog2.util.graph.exceptions.NodeAlreadyPresentException;
import labprog2.util.graph.exceptions.NodeNotPresentException;

public class GraphTest {

    @Test
    public void testGetShortestPath() {
        Node a = new Airport("A");
        Node b = new Airport("B");
        Node c = new Airport("C");
        Node d = new Airport("D");
        Node e = new Airport("E");

        Edge ab = new Edge(a, b, 1);
        Edge bc = new Edge(b, c, 1);
        Edge bd = new Edge(b, d, 10);
        Edge cd = new Edge(c, d, 1);
        Edge ce = new Edge(c, e, 10);
        Edge de = new Edge(d, e, 1);

        try {
            Graph graph = new MatrixGraph(new Node[] { a, b, c, d, e }, new Edge[] { ab, bc, bd, cd, ce, de });

            List<Node> nodes = graph.getShortestPath(a, e).getNodes();

            assertEquals(a, nodes.get(0));
            assertEquals(b, nodes.get(1));
            assertEquals(c, nodes.get(2));
            assertEquals(d, nodes.get(3));
            assertEquals(e, nodes.get(4));

        } catch (FullGraphException | NodeAlreadyPresentException | NodeNotPresentException e1) {
            e1.printStackTrace();
        }
    }

    @Test
    public void testGetShortestNonDirectPath() {
        Node a = new Airport("A");
        Node b = new Airport("B");
        Node c = new Airport("C");
        Node d = new Airport("D");
        Node e = new Airport("E");

        Edge ab = new Edge(a, b, 1);
        Edge ac = new Edge(a, c, 2);
        Edge bc = new Edge(b, c, 3);
        Edge cb = new Edge(c, b, 3);
        Edge bd = new Edge(b, d, 3);
        Edge cd = new Edge(c, d, 1);
        Edge de = new Edge(d, e, 5);

        try {
            Graph graph = new MatrixGraph(new Node[] { a, b, c, d, e }, new Edge[] { ab, ac, bc, cb, bd, cd, de });
            List<Node> nodes = graph.getShortestNonDirectPath(a, b).getNodes();
            assertEquals(a, nodes.get(0));
            assertEquals(c, nodes.get(1));
            assertEquals(b, nodes.get(2));
        } catch (FullGraphException | NodeAlreadyPresentException | NodeNotPresentException
                | EdgeNotPresentException e1) {
            e1.printStackTrace();
        }

    }

}