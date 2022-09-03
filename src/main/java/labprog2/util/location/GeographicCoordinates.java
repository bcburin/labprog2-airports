package labprog2.util.location;

/**
 * Models geographical coordinates in the form of latitude and longitude.
 */
public class GeographicCoordinates {
    public static final double EARTH_RADIUS = 6_371.00; // Value in km

    private double radius;

    private final double latitude, longitude;

    /**
     * Creates set of geographical coordinates with the supplied latitude and longitude values. Initializes the earth
     * radius to 6371 km.
     *
     * @param latitude latitude value
     * @param longitude longitude value
     */
    public GeographicCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = EARTH_RADIUS;
    }

    /**
     * Creates set of geographical coordinates with the supplied latitude and longitude values. Initializes the earth
     * radius the given value
     *
     * @param latitude latitude value
     * @param longitude longitude value
     * @param radius planet's radius value
     */
    public GeographicCoordinates(double latitude, double longitude, double radius) {
        this(latitude, longitude);
        this.radius = radius;
    }

    /**
     *
     * @return latitude
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     *
     * @return longitide
     */
    public double getLongitude() {
        return this.longitude;
    }

    /**
     *
     * @param other other set of geographical coordinates
     * @return distance between coordinates in km
     */
    public double distanceTo(GeographicCoordinates other) {
        double latitudeDiff = Math.toRadians(other.latitude - this.latitude);
        double longitudeDiff = Math.toRadians(other.longitude - this.longitude);

        double lat1 = Math.toRadians(this.latitude);
        double lat2 = Math.toRadians(other.latitude);

        double angle = Math.sqrt(
                Math.pow(Math.sin(latitudeDiff / 2), 2)
                        + Math.pow(Math.sin(longitudeDiff / 2), 2) * Math.cos(lat1) * Math.cos(lat2));

        return 2 * radius * Math.asin(angle);
    }

}
