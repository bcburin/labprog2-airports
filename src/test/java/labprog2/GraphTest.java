package labprog2;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import labprog2.model.Airport;
import labprog2.util.graph.Edge;
import labprog2.util.graph.Graph;
import labprog2.util.graph.MatrixGraph;
import labprog2.util.graph.exceptions.NodeAlreadyPresentException;
import labprog2.util.graph.exceptions.NodeNotPresentException;

public class GraphTest {

    @Test
    public void testGetShortestPath() {
        Airport a = new Airport("A");
        Airport b = new Airport("B");
        Airport c = new Airport("C");
        Airport d = new Airport("D");
        Airport e = new Airport("E");

        Edge<Airport> ab = new Edge<>(a, b, 1);
        Edge<Airport> bc = new Edge<>(b, c, 1);
        Edge<Airport> bd = new Edge<>(b, d, 10);
        Edge<Airport> cd = new Edge<>(c, d, 1);
        Edge<Airport> ce = new Edge<>(c, e, 10);
        Edge<Airport> de = new Edge<>(d, e, 1);

        try {
            Graph<Airport> graph = new MatrixGraph<>(
                    Arrays.asList(a, b, c, d, e), Arrays.asList( ab, bc, bd, cd, ce, de ));

            List<Airport> nodes = graph.getShortestPath(a, e).getNodes();

            assertEquals(a, nodes.get(0));
            assertEquals(b, nodes.get(1));
            assertEquals(c, nodes.get(2));
            assertEquals(d, nodes.get(3));
            assertEquals(e, nodes.get(4));

        } catch ( NodeAlreadyPresentException | NodeNotPresentException e1) {
            e1.printStackTrace();
        }
    }

    @Test
    public void testGetShortestNonDirectPath() {
        Airport a = new Airport("A");
        Airport b = new Airport("B");
        Airport c = new Airport("C");
        Airport d = new Airport("D");
        Airport e = new Airport("E");

        Edge<Airport> ab = new Edge<>(a, b, 1);
        Edge<Airport> ac = new Edge<>(a, c, 2);
        Edge<Airport> bc = new Edge<>(b, c, 3);
        Edge<Airport> cb = new Edge<>(c, b, 3);
        Edge<Airport> bd = new Edge<>(b, d, 3);
        Edge<Airport> cd = new Edge<>(c, d, 1);
        Edge<Airport> de = new Edge<>(d, e, 5);

        try {
            Graph<Airport> graph = new MatrixGraph<>(Arrays.asList(a, b, c, d, e), Arrays.asList(ab, ac, bc, cb, bd, cd, de));
            List<Airport> nodes = graph.getShortestNonDirectPath(a, b).getNodes();
            assertEquals(a, nodes.get(0));
            assertEquals(c, nodes.get(1));
            assertEquals(b, nodes.get(2));
        } catch ( NodeAlreadyPresentException | NodeNotPresentException e1) {
            e1.printStackTrace();
        }

    }

}
