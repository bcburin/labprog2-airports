package labprog2;

import com.opencsv.exceptions.CsvException;
import labprog2.model.Airport;
import labprog2.model.AirportNetwork;
import labprog2.util.graph.exceptions.NodeNotPresentException;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class AirportNetworkTest {

    @Test
    public void testGetShortestPath() throws IOException, CsvException, NodeNotPresentException {
        Airport[] airports = Airport.readFromAirportCsv();

        AirportNetwork airportNetwork = new AirportNetwork(Arrays.asList(airports));

        airportNetwork.getShortestNonDirectPath(airports[0], airports[1]);
    }

}
