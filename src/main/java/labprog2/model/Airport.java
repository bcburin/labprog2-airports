package labprog2.model;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import labprog2.util.location.Address;
import labprog2.util.location.GeographicCoordinates;

/**
 * Models an airport object.
 * @see labprog2.util.graph.Graph
 */
public class Airport {

    private static final String pathToCsv = "src/main/resources/airport_data.csv";

    private int id;
    private final String iata;
    private String icao;
    private String name;
    private Address address;
    private String phone;
    private GeographicCoordinates geographicCoordinates;
    private int ucl;
    private String website;

    private static final String AIRPORTS_TABLE = "airports";

    private static final String SELECT_ALL_QUERY = String.format("SELECT * FROM %s", AIRPORTS_TABLE);

    private static final String SELECT_BY_ID_QUERY = String.format("SELECT * FROM %s WHERE id=?", AIRPORTS_TABLE);

    private static final String SELECT_BY_IATA_QUERY = String.format("SELECT * FROM %s WHERE iata=?", AIRPORTS_TABLE);

    private static final String INSERT_AIRPORT_QUERY = String
            .format("INSERT INTO %s VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", AIRPORTS_TABLE);

    /**
     * Creates an airport object from an array of strings containing exactly 19 elements, given in the following
     * order:
     * <ul>
     *     <li> 0: id </li>
     *     <li> 1: iata code </li>
     *     <li> 2: icao code </li>
     *     <li> 3: airport name </li>
     *     <li> 4 to 13: address data, see below for more information </li>
     *     <li> 14: phone number </li>
     *     <li> 15: latitude </li>
     *     <li> 16: longitude </li>
     *     <li> 17: time zone </li>
     *     <li> 18: website url </li>
     * </ul>
     *
     * @see labprog2.util.location.Address
     *
     * @param data array of string containing airport data
     */
    public Airport(String[] data) {
        this.id = Integer.parseInt(data[0]);
        this.iata = data[1];
        this.icao = data[2];
        this.name = data[3];
        this.address = new Address(Arrays.copyOfRange(data, 4, 14));
        this.phone = data[14];
        this.geographicCoordinates = new GeographicCoordinates(
                Double.parseDouble(data[15]), Double.parseDouble(data[16]));
        this.ucl = Integer.parseInt(data[17]);
        this.website = data[18];
    }

    public Airport(String iata) {
        this.iata = iata;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof Airport))
            return false;
        Airport otherNode = (Airport) other;
        return Objects.equals(this.iata, otherNode.iata);
    }

    @Override
    public int hashCode() {
        return iata.hashCode();
    }

    @Override
    public String toString() {
        return iata;
    }

    public int getId() {
        return this.id;
    }

    public String getIata() {
        return this.iata;
    }

    public String getIcao() {
        return this.icao;
    }

    public String getName() {
        return this.name;
    }

    public Address getAddress() {
        return this.address;
    }

    public String getPhone() {
        return this.phone;
    }

    public GeographicCoordinates getGeographicCoordinates() {
        return this.geographicCoordinates;
    }

    public int getUcl() {
        return this.ucl;
    }

    public String getWebsite() {
        return this.website;
    }

    /**
     * <p>Reads all airport data within a csv file and returns an array of airports.</p> <br>
     *
     * <em>Deprecated: airports are now retrieved from a MySQL database</em>
     *
     * @return array containing
     * @throws IOException failure opening csv file
     * @throws CsvException failure reading csv file
     */
    public static Airport[] readFromAirportCsv() throws IOException, CsvException {
        FileReader fileReader = new FileReader(pathToCsv);
        CSVReader reader = new CSVReader(fileReader);
        List<String[]> r = reader.readAll();
        Airport[] airports = new Airport[r.size() - 1];
        for (int i = 1; i != r.size(); ++i)
            airports[i - 1] = new Airport(r.get(i));
        reader.close();
        return airports;
    }

    /**
     * Insert airport data to the database.
     *
     * @param connection active connection to the database.
     * @throws SQLException thrown if an error occurred while creating the insert statement.
     */
    public void insertDb(Connection connection) throws SQLException {
        PreparedStatement insertStatement = connection.prepareStatement(INSERT_AIRPORT_QUERY);

        insertStatement.setInt(1, getId());
        insertStatement.setString(2, getIata());
        insertStatement.setString(3, getIcao());
        insertStatement.setString(4, getName());
        insertStatement.setString(5, getAddress().getLocation());
        insertStatement.setString(6, getAddress().getStreetNumber());
        insertStatement.setString(7, getAddress().getStreet());
        insertStatement.setString(8, getAddress().getCity());
        insertStatement.setString(9, getAddress().getCounty());
        insertStatement.setString(10, getAddress().getState());
        insertStatement.setString(11, getAddress().getStateCode());
        insertStatement.setString(12, getAddress().getCountryISO());
        insertStatement.setString(13, getAddress().getCountry());
        insertStatement.setString(14, getAddress().getPostalCode());
        insertStatement.setString(15, getPhone());
        insertStatement.setDouble(16, getGeographicCoordinates().getLatitude());
        insertStatement.setDouble(17, getGeographicCoordinates().getLongitude());
        insertStatement.setInt(18, getUcl());
        insertStatement.setString(19, getWebsite());
        insertStatement.executeUpdate();
    }

    /**
     * Retrieves data about all airports stored in the database.
     *
     * @param connection active connection to the database.
     * @return list containing all the airports in the database.
     * @throws SQLException thrown if an error occurred while creating the insert statement.
     */
    public static List<Airport> getAllAirportDataDb(Connection connection) throws SQLException {

        ResultSet airportSet = connection.createStatement().executeQuery(SELECT_ALL_QUERY);

        List<Airport> airportList = new LinkedList<>();

        while (airportSet.next()) {
            Airport airport = packAirportQueryResults(airportSet);
            airportList.add(airport);
        }

        return airportList;
    }

    /**
     * Retrieves data about an airport in the database by its id.
     *
     * @param connection active connection to the database
     * @param id id of the airport for querying
     * @return queried airport
     * @throws SQLException thrown if an error occurred while creating the insert statement.
     */
    public static Airport getAirportDataDb(Connection connection, int id) throws SQLException {
        PreparedStatement selectStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
        selectStatement.setInt(1, id);

        ResultSet airportSet = selectStatement.executeQuery();
        airportSet.next();

        return packAirportQueryResults(airportSet);
    }

    /**
     * Retrieves data about an airport in the database by its iata code.
     *
     * @param connection active connection to the database
     * @param iata iata of the airport for querying
     * @return queried airport
     * @throws SQLException thrown if an error occurred while creating the insert statement.
     */
    public static Airport getAirportDataDb(Connection connection, String iata) throws SQLException {
        PreparedStatement selectStatement = connection.prepareStatement(SELECT_BY_IATA_QUERY);
        selectStatement.setString(1, iata);

        ResultSet airportSet = selectStatement.executeQuery();
        airportSet.next();

        return packAirportQueryResults(airportSet);
    }

    /**
     * Parses the result set of a query on the airport table into an airport object.
     *
     * @param airportSet result set of a select query in the airport table
     * @return Airport corresponding to the read data.
     * @throws SQLException thrown if an error occurred while reading the data
     */
    private static Airport packAirportQueryResults(ResultSet airportSet) throws SQLException {
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

        String[] airportStrings = { id, iata, icao, name, location, streetNumber, street, city, county, state,
                state_code, countryIso, country, postalCode, phone, latitude, longitude, uct, website };

        return new Airport(airportStrings);
    }

}
