package labprog2.util.mysql;

import labprog2.model.Airport;
import labprog2.model.UserSearch;

import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;

import java.sql.SQLException;

public class MySQLAirportDbConnection extends MySQLConnection {

    private static final String DATABASE_NAME = "airport_app";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "password";
    private static final String AIRPORTS_TABLE = "airports";
    private static final String USER_SEARCHES_TABLE = "searches";

    private static final String INSERT_AIRPORT_QUERY = String
            .format("INSERT INTO %s VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", AIRPORTS_TABLE);

    private static final String SELECT_ALL_QUERY = String.format("SELECT * FROM %s", AIRPORTS_TABLE);

    private static final String SELECT_BY_ID_QUERY = String.format("SELECT * FROM %s WHERE id=?", AIRPORTS_TABLE);

    private static final String SELECT_BY_IATA_QUERY = String.format("SELECT * FROM %s WHERE iata=?", AIRPORTS_TABLE);

    private static final String INSERT_USER_SEARCH_QUERY = String.format(
            "INSERT INTO %s (src_airport_id, des_airport_id, search_time) VALUES (?, ?, ?)", USER_SEARCHES_TABLE);

    public MySQLAirportDbConnection() throws ClassNotFoundException, SQLException {
        super(DATABASE_NAME, USER_NAME, PASSWORD);
    }

    public void insertAirportData(Airport[] airports) throws SQLException {
        for (Airport airport : airports)
            insertAirportData(airport);
    }

    public void insertAirportData(Airport airport) throws SQLException {
        PreparedStatement insertStatement = connection.prepareStatement(INSERT_AIRPORT_QUERY);
        insertStatement.setInt(1, airport.getId());
        insertStatement.setString(2, airport.getIata());
        insertStatement.setString(3, airport.getIcao());
        insertStatement.setString(4, airport.getName());
        insertStatement.setString(5, airport.getAddress().getLocation());
        insertStatement.setString(6, airport.getAddress().getStreetNumber());
        insertStatement.setString(7, airport.getAddress().getStreet());
        insertStatement.setString(8, airport.getAddress().getCity());
        insertStatement.setString(9, airport.getAddress().getCounty());
        insertStatement.setString(10, airport.getAddress().getState());
        insertStatement.setString(11, airport.getAddress().getStateCode());
        insertStatement.setString(12, airport.getAddress().getCountryISO());
        insertStatement.setString(13, airport.getAddress().getCountry());
        insertStatement.setString(14, airport.getAddress().getPostalCode());
        insertStatement.setString(15, airport.getPhone());
        insertStatement.setDouble(16, airport.getGeographicCoordinates().getLatitude());
        insertStatement.setDouble(17, airport.getGeographicCoordinates().getLongitude());
        insertStatement.setInt(18, airport.getUcl());
        insertStatement.setString(19, airport.getWebsite());
        insertStatement.executeUpdate();
    }

    private String[] packAirportQueryResults(ResultSet airportSet) throws SQLException {

        String id = airportSet.getString(1);
        String iata = airportSet.getString(2);
        String icao = airportSet.getString(3);
        String name = airportSet.getString(4);
        String location = airportSet.getString(5);
        String streetNumber = airportSet.getString(6);
        String street = airportSet.getString(7);
        String city = airportSet.getString(8);
        String county = airportSet.getString(9);
        String state = airportSet.getString(10);
        String state_code = airportSet.getString(11);
        String countryIso = airportSet.getString(12);
        String country = airportSet.getString(13);
        String postalCode = airportSet.getString(14);
        String phone = airportSet.getString(15);
        String latitude = airportSet.getString(16);
        String longitude = airportSet.getString(17);
        String uct = airportSet.getString(18);
        String website = airportSet.getString(19);

        return new String[] { id, iata, icao, name, location, streetNumber, street, city, county, state, state_code,
                countryIso, country, postalCode, phone, latitude, longitude, uct, website };
    }

    public Airport[] getAllAirportData() throws SQLException {

        ResultSet airportSet = connection.createStatement().executeQuery(SELECT_ALL_QUERY);

        List<Airport> airportList = new LinkedList<Airport>();

        while (airportSet.next()) {
            String[] airportDataArray = packAirportQueryResults(airportSet);
            Airport airport = new Airport(airportDataArray);
            airportList.add(airport);
        }

        return airportList.toArray(new Airport[0]);
    }

    public Airport getAirportData(int id) throws SQLException {
        PreparedStatement selectStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
        selectStatement.setInt(1, id);

        ResultSet airportSet = selectStatement.executeQuery();
        airportSet.next();
        String[] airportDataArray = packAirportQueryResults(airportSet);

        return new Airport(airportDataArray);
    }

    public Airport getAirportData(String iata) throws SQLException {
        PreparedStatement selectStatement = connection.prepareStatement(SELECT_BY_IATA_QUERY);
        selectStatement.setString(1, iata);

        ResultSet airportSet = selectStatement.executeQuery();
        airportSet.next();
        String[] airportDataArray = packAirportQueryResults(airportSet);

        return new Airport(airportDataArray);
    }

    public void insertUserSearchData(UserSearch userSearch) throws SQLException {
        PreparedStatement insertStatement = connection.prepareStatement(INSERT_USER_SEARCH_QUERY);
        insertStatement.setInt(1, userSearch.getSrcAirportId());
        insertStatement.setInt(2, userSearch.getDesAirportId());
        insertStatement.setTimestamp(3, userSearch.getSearchTimestamp());
        insertStatement.executeUpdate();
    }

}
