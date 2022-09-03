package labprog2.model;

import java.sql.Timestamp;

/**
 * Models a user search.
 */
public class UserSearch {

    private final int srcAirportId;
    private final int desAirportId;
    private final Timestamp searchTimestamp;
    private final String searchResults;

    /**
     * Creates a new user search object.
     *
     * @param srcAirport origin airport.
     * @param desAirport destiny airport.
     * @param searchTimestamp timestamp of the moment the query was made.
     * @param searchResults result string of the query.
     */
    public UserSearch(Airport srcAirport, Airport desAirport, Timestamp searchTimestamp, String searchResults) {
        this.srcAirportId = srcAirport.getId();
        this.desAirportId = desAirport.getId();
        this.searchTimestamp = searchTimestamp;
        this.searchResults = searchResults;
    }

    public int getSrcAirportId() {
        return srcAirportId;
    }

    public int getDesAirportId() {
        return desAirportId;
    }

    public Timestamp getSearchTimestamp() {
        return searchTimestamp;
    }

    public String getSearchResults() { return searchResults; }
}
