package labprog2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import labprog2.model.Airport;
import labprog2.App;

public class MySQLAirportDbConnectionTest {

    @Test
    public void testGetAirportDataByID() throws ClassNotFoundException, SQLException {
        Connection connection = App.connectToDb();

        Airport airportCwb = Airport.getAirportDataDb(connection, 1613);
        Airport airportFor = Airport.getAirportDataDb(connection, 2363);
        Airport airportGig = Airport.getAirportDataDb(connection, 2563);
        Airport airportGru = Airport.getAirportDataDb(connection, 2695);

        assertEquals("CWB", airportCwb.getIata());
        assertEquals("FOR", airportFor.getIata());
        assertEquals("GIG", airportGig.getIata());
        assertEquals("GRU", airportGru.getIata());
    }

    @Test
    public void testGetAirportDataByIata() throws ClassNotFoundException, SQLException {
        Connection connection = App.connectToDb();

        Airport airportCwb = Airport.getAirportDataDb(connection, "CWB");
        Airport airportFor = Airport.getAirportDataDb(connection, "FOR");
        Airport airportGig = Airport.getAirportDataDb(connection, "GIG");
        Airport airportGru = Airport.getAirportDataDb(connection, "GRU");

        assertEquals("CWB", airportCwb.getIata());
        assertEquals("FOR", airportFor.getIata());
        assertEquals("GIG", airportGig.getIata());
        assertEquals("GRU", airportGru.getIata());
    }

    @Test
    public void testGetAllAirportData() throws ClassNotFoundException, SQLException {
        Connection connection = App.connectToDb();

        List<Airport> airports = Airport.getAllAirportDataDb(connection);

        assertEquals("CWB", airports.get(10).getIata());
        assertEquals("FOR", airports.get(13).getIata());
        assertEquals("GIG", airports.get(14).getIata());
        assertEquals("GRU", airports.get(15).getIata());

    }

}
