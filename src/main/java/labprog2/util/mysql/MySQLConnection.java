package labprog2.util.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    protected Connection connection;

    public MySQLConnection(String databaseName, String userName, String userPassword)
            throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = String.format("jdbc:mysql://127.0.0.1/%s?user=%s&password=%s", databaseName, userName,
                userPassword);

        connection = DriverManager.getConnection(url);

    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        connection.close();
    }

}
