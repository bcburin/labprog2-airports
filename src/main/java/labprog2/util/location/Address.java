package labprog2.util.location;

public class Address {
    private String location;
    private String streetNumber;
    private String street;
    private String city;
    private String county;
    private String state;
    private String countryISO;
    private String country;
    private String postalCode;

    public Address(String[] data) {
        this.location = data[0];
        this.streetNumber = data[1];
        this.street = data[2];
        this.city = data[3];
        this.county = data[4];
        this.state = data[5];
        this.countryISO = data[6];
        this.country = data[7];
        this.postalCode = data[8];
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

}
