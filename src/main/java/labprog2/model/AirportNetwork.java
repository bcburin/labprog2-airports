package labprog2.model;

import labprog2.util.graph.Edge;
import labprog2.util.graph.Graph;
import labprog2.util.graph.MatrixGraph;
import labprog2.util.graph.Path;
import labprog2.util.graph.exceptions.FullGraphException;
import labprog2.util.graph.exceptions.NodeAlreadyPresentException;
import labprog2.util.graph.exceptions.NodeNotPresentException;

import java.util.List;

/**
 * Models a fully connected airport network.
 */
public class AirportNetwork {

    private final List<Airport> airports;

    private final Graph network;

    /**
     * Created a fully connected airport network
     *
     * @param airports airports that make up the network.
     */
    public AirportNetwork(List<Airport> airports) {
        this.airports = airports;
        // Initialize network as a graph
        network = new MatrixGraph(airports.size());
        // Add all airports as nodes in the graph
        for (Airport airport : airports) {
            try {
                network.addNode(airport);
            } catch (FullGraphException | NodeAlreadyPresentException e) {
                throw new RuntimeException(e);
            }
        }
        createNetwork();
    }

    /**
     * Creates airport network as a fully connected (complete) graph.
     */
    private void createNetwork() {
        // Iterate over all pairs of airports
        for (Airport srcAirport : airports) {
            for (Airport desAirport : airports) {
                // If the airports are not the same
                if(! srcAirport.equals(desAirport) ) {
                    // Calculate distance between the airports
                    int distance = (int) srcAirport.getGeographicCoordinates().distanceTo(desAirport.getGeographicCoordinates());
                    // Create an edge containing both airports and with the distance between them as weight
                    Edge edge = new Edge(srcAirport, desAirport, distance);
                    try {
                        // Add edge to graph
                        network.addEdge(edge);
                    } catch (NodeNotPresentException e) {
                        // This will never be reached, as are airports have already been included
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * Gets the shortest path between two airports that is not direct.
     *
     * @param srcAirport origin airport.
     * @param desAirport destiny airport
     * @return path between given airports.
     * @throws NodeNotPresentException thrown if either of the airports is not present in the network.
     */
    public Path getShortestNonDirectPath(Airport srcAirport, Airport desAirport) throws NodeNotPresentException {
        return this.network.getShortestNonDirectPath(srcAirport, desAirport);
    }


}
