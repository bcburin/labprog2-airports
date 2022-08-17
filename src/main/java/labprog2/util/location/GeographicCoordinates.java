package labprog2.util.location;

public class GeographicCoordinates {
    public static final double EARTH_RADIUS = 6_371.00; // Value in km

    private double radius;

    private double latitude, longitude;

    public GeographicCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = EARTH_RADIUS;
    }

    public GeographicCoordinates(double latitude, double longitude, double radius) {
        this(latitude, longitude);
        this.radius = radius;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

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
