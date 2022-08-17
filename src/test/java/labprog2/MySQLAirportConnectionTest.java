package labprog2;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import labprog2.model.Airport;
import labprog2.util.mysql.MySQLAirportConnection;

public class MySQLAirportConnectionTest {

    @Test
    public void testGetAirportDataByID() throws ClassNotFoundException, SQLException {
        MySQLAirportConnection airportConnection = new MySQLAirportConnection();

        Airport airportCwb = airportConnection.getAirportData(1613);
        Airport airportFor = airportConnection.getAirportData(2363);
        Airport airportGig = airportConnection.getAirportData(2563);
        Airport airportGru = airportConnection.getAirportData(2695);

        assertTrue(airportCwb.getIata().equals("CWB"));
        assertTrue(airportFor.getIata().equals("FOR"));
        assertTrue(airportGig.getIata().equals("GIG"));
        assertTrue(airportGru.getIata().equals("GRU"));
    }

    @Test
    public void testGetAirportDataByIata() throws ClassNotFoundException, SQLException {
        MySQLAirportConnection airportConnection = new MySQLAirportConnection();

        Airport airportCwb = airportConnection.getAirportData("CWB");
        Airport airportFor = airportConnection.getAirportData("FOR");
        Airport airportGig = airportConnection.getAirportData("GIG");
        Airport airportGru = airportConnection.getAirportData("GRU");

        assertTrue(airportCwb.getIata().equals("CWB"));
        assertTrue(airportFor.getIata().equals("FOR"));
        assertTrue(airportGig.getIata().equals("GIG"));
        assertTrue(airportGru.getIata().equals("GRU"));
    }

    @Test
    public void testGetAllAirportData() throws ClassNotFoundException, SQLException {
        MySQLAirportConnection airportConnection = new MySQLAirportConnection();

        Airport[] airports = airportConnection.getAllAirportData();

        assertTrue(airports[10].getIata().equals("CWB"));
        assertTrue(airports[13].getIata().equals("FOR"));
        assertTrue(airports[14].getIata().equals("GIG"));
        assertTrue(airports[15].getIata().equals("GRU"));

    }

}
