package labprog2.model;

import labprog2.util.graph.Edge;
import labprog2.util.graph.Graph;
import labprog2.util.graph.MatrixGraph;
import labprog2.util.graph.Path;
import labprog2.util.graph.exceptions.EdgeNotPresentException;
import labprog2.util.graph.exceptions.FullGraphException;
import labprog2.util.graph.exceptions.NodeAlreadyPresentException;
import labprog2.util.graph.exceptions.NodeNotPresentException;

public class AirportNetwork {

    private final Airport[] airports;

    private final Graph network;

    public AirportNetwork(Airport[] airports) {
        this.airports = airports;
        network = new MatrixGraph(airports.length);
        for (Airport airport : airports) {
            try {
                network.addNode(airport);
            } catch (FullGraphException | NodeAlreadyPresentException e) {
                throw new RuntimeException(e);
            }
        }
        createNetwork();
    }

    private void createNetwork() {
        for (Airport srcAirport : airports) {
            for (Airport desAirport : airports) {
                if(! srcAirport.equals(desAirport) ) {
                    int distance = (int) srcAirport.getGeographicCoordinates().distanceTo(desAirport.getGeographicCoordinates());
                    Edge edge = new Edge(srcAirport, desAirport, distance);
                    try {
                        network.addEdge(edge);
                    } catch (NodeNotPresentException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public Path getShortestNonDirectPath(Airport srcAirport, Airport desAirport) throws EdgeNotPresentException, NodeNotPresentException {
        return this.network.getShortestNonDirectPath(srcAirport, desAirport);
    }


}
