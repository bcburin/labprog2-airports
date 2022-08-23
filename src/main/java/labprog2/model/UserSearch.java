package labprog2.model;

import java.sql.Timestamp;

public class UserSearch {

    private final int srcAirportId;
    private final int desAirportId;
    private final String srcAirportIata;
    private final String desAirportIata;
    private final Timestamp searchTimestamp;

    public UserSearch(Airport srcAirport, Airport desAirport, Timestamp searchTimestamp) {
        this.srcAirportId = srcAirport.getId();
        this.desAirportId = desAirport.getId();
        this.srcAirportIata = srcAirport.getIata();
        this.desAirportIata = desAirport.getIata();
        this.searchTimestamp = searchTimestamp;
    }

    public int getSrcAirportId() {
        return srcAirportId;
    }

    public int getDesAirportId() {
        return desAirportId;
    }

    public String getSrcAirportIata() {
        return srcAirportIata;
    }

    public String getDesAirportIata() {
        return desAirportIata;
    }

    public Timestamp getSearchTimestamp() {
        return searchTimestamp;
    }

}
