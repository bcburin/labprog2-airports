package labprog2.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Models a user search.
 */
public class UserSearch {

    private final int srcAirportId;
    private final int desAirportId;
    private final Timestamp searchTimestamp;
    private final String searchResults;

    private static final String USER_SEARCHES_TABLE = "searches";

    private static final String INSERT_USER_SEARCH_QUERY = String.format(
            "INSERT INTO %s (src_airport_id, des_airport_id, search_time, result) VALUES (?, ?, ?, ?)", USER_SEARCHES_TABLE);

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

    /**
     * Inserts user search to the database.
     *
     * @param connection active connection to the database.
     * @throws SQLException thrown if an error occurred while creating the insert statement.
     */
    public void insertDb(Connection connection) throws SQLException {
        PreparedStatement insertStatement = connection.prepareStatement(INSERT_USER_SEARCH_QUERY);
        insertStatement.setInt(1, getSrcAirportId());
        insertStatement.setInt(2, getDesAirportId());
        insertStatement.setTimestamp(3, getSearchTimestamp());
        insertStatement.setString(4, getSearchResults());
        insertStatement.executeUpdate();
    }
}
