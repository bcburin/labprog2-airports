package labprog2.util.location;

public class Address {
    private final String location;
    private final String streetNumber;
    private final String street;
    private final String city;
    private final String county;
    private final String state;
    private final String state_code;
    private final String countryISO;
    private final String country;
    private final String postalCode;

    public Address(String[] data) {
        this.location = data[0];
        this.streetNumber = data[1];
        this.street = data[2];
        this.city = data[3];
        this.county = data[4];
        this.state = data[5];
        this.state_code = data[6];
        this.countryISO = data[7];
        this.country = data[8];
        this.postalCode = data[9];
    }

    public String getLocation() {
        return this.location;
    }

    public String getStreetNumber() {
        return this.streetNumber;
    }

    public String getStreet() {
        return this.street;
    }

    public String getCity() {
        return this.city;
    }

    public String getCounty() {
        return this.county;
    }

    public String getState() {
        return this.state;
    }

    public String getCountryISO() {
        return this.countryISO;
    }

    public String getCountry() {
        return this.country;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public String getStateCode() { return state_code; }
}
