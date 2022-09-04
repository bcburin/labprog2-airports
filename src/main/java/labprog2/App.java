package labprog2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import labprog2.gui.AirportAppFrame;

import javax.swing.*;

public class App {

    private static final String DATABASE_NAME = "airport_app";
    private static final String USER_NAME = "airportdb_user";
    private static final String PASSWORD = "$up3rS3cr3t";

    public static void main(String[] args) {

        try {
            // Get connection to database
            Connection connection = connectToDb();

            // Render application GUI
            AirportAppFrame appFrame = new AirportAppFrame(connection);

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Unable to connect to the database");
        }

    }

    public static Connection connectToDb() throws SQLException, ClassNotFoundException {
        // Connection string
        String url = String.format("jdbc:mysql://127.0.0.1/%s?user=%s&password=%s", DATABASE_NAME, USER_NAME, PASSWORD);

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(url);
    }
}
