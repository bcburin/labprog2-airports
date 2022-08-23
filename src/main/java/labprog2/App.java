package labprog2;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;

import com.opencsv.exceptions.CsvException;
import labprog2.gui.AirportAppFrame;
import labprog2.model.Airport;
import labprog2.util.mysql.MySQLAirportConnection;

public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, CsvException {

        MySQLAirportConnection airportConnection = new MySQLAirportConnection();

        Airport[] airports = airportConnection.getAllAirportData();

        AirportAppFrame appFrame = new AirportAppFrame(airports);

    }
}
