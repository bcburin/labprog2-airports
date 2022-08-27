package labprog2;

import java.sql.SQLException;

import labprog2.gui.AirportAppFrame;
//import labprog2.gui.AirportAppGUI;
import labprog2.model.Airport;
import labprog2.util.mysql.MySQLAirportDbConnection;

public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException{

        MySQLAirportDbConnection airportConnection = new MySQLAirportDbConnection();

        Airport[] airports = airportConnection.getAllAirportData();

        AirportAppFrame appFrame = new AirportAppFrame(airports, airportConnection);

//        AirportAppGUI gui = new AirportAppGUI();
    }
}
