package labprog2.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import labprog2.util.graph.Node;
import labprog2.util.location.Address;
import labprog2.util.location.GeographicCoordinates;

public class Airport implements Node {

    private static final String pathToCsv = "src/main/resources/airport_data.csv";

    private int id;
    private String iata;
    private String icao;
    private String name;
    private Address address;
    private String phone;
    private GeographicCoordinates geographicCoordinates;
    private int ucl;
    private String website;

    public Airport(String[] data) {
        this.id = Integer.parseInt(data[0]);
        this.iata = data[1];
        this.icao = data[2];
        this.name = data[3];
        this.address = new Address(Arrays.copyOfRange(data, 4, 13));
        this.phone = data[13];
        this.geographicCoordinates = new GeographicCoordinates(
                Double.parseDouble(data[14]), Double.parseDouble(data[15]));
        this.ucl = Integer.parseInt(data[16]);
        this.website = data[17];
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
        return this.iata == otherNode.iata;
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

    public static Airport[] readFromAirportCsv() throws IOException, CsvException {
        FileReader fileReader = new FileReader(pathToCsv);
        CSVReader reader = new CSVReader(fileReader);
        List<String[]> r = reader.readAll();
        Airport airports[] = new Airport[r.size() - 1];
        for (int i = 1; i != r.size(); ++i)
            airports[i - 1] = new Airport(r.get(i));
        reader.close();
        return airports;
    }

}
