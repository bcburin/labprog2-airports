package labprog2.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import labprog2.util.graph.Node;
import labprog2.util.location.Address;
import labprog2.util.location.GeographicCoordinates;

/**
 * Models an airport object. Implements the node interface so it may be used as a graph element.
 * @see labprog2.util.graph.Node
 * @see labprog2.util.graph.Graph
 */
public class Airport implements Node {

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

}
