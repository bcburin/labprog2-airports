package labprog2;

import static org.junit.Assert.assertEquals;

import com.opencsv.exceptions.CsvException;
import labprog2.model.Airport;
import labprog2.model.AirportNetwork;
import labprog2.util.graph.exceptions.EdgeNotPresentException;
import labprog2.util.graph.exceptions.NodeNotPresentException;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class AirportNetworkTest {

    @Test
    public void testGetShortestPath() throws IOException, CsvException, EdgeNotPresentException, NodeNotPresentException {
        Airport[] airports = Airport.readFromAirportCsv();

        AirportNetwork airportNetwork = new AirportNetwork(Arrays.asList(airports));

        airportNetwork.getShortestNonDirectPath(airports[0], airports[1]);
    }

}
