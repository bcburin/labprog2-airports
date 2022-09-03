package labprog2.gui;

import labprog2.model.Airport;
import labprog2.model.AirportNetwork;
import labprog2.model.UserSearch;
import labprog2.util.graph.Node;
import labprog2.util.graph.exceptions.EdgeNotPresentException;
import labprog2.util.graph.exceptions.NodeNotPresentException;
import labprog2.util.mysql.MySQLAirportDbConnection;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AirportAppFrame extends JFrame {

    private JButton searchButton;
    private JPanel mainPanel;
    private JComboBox<Airport> fromAirportComboBox;
    private JComboBox<Airport> toAirportComboBox;
    private JComboBox<String> toStateComboBox;
    private JComboBox<String> fromStateComboBox;
    private JLabel responseLabel;

    private AirportNetwork airportNetwork;

    private final MySQLAirportDbConnection airportConnection;

    private List<Airport> airports;

    /**
     * Gets airport data from database. Setups frame and adds handlers to components. Informs user in case of
     * failure.
     *
     * @param airportConnection active connection to the database.
     */
    public AirportAppFrame(MySQLAirportDbConnection airportConnection) {
        this.airportConnection = airportConnection;

        try {
            // Get airport data from database and create network
            this.airports = airportConnection.getAllAirportData();
            this.airportNetwork = new AirportNetwork(this.airports);
        } catch (SQLException e) {
            // Inform user of failure in getting data
            JOptionPane.showMessageDialog(this, "Unable to get airport data");
        }

        setupPanel();

        setAirportStateComboBoxes();

        searchButton.addActionListener(actionEvent -> handleSearchButtonClick());

        fromStateComboBox.addActionListener(actionEvent -> handleFromStateChange());

        toStateComboBox.addActionListener(actionEvent -> handleToStateChange());
    }

    /**
     * Applies the initial setup of the main panel, setting its size, the default close operation and making it
     * visible. It also makes the response label (that shows the output route) not visible, at first.
     */
    private void setupPanel() {
        setContentPane(mainPanel);
        setTitle("Airport App");
        pack();
        setVisible(true);
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        responseLabel.setVisible(false);
    }

    /**
     * Initializes the state filter combo boxes. Retrieves all states from the collection of airports and populates
     * the combo box items with them. Also adds the '-' option to disable state filters.
     */
    private void setAirportStateComboBoxes() {
        List<String> airportStates = new LinkedList<>();

        for(Airport airport : this.airports) airportStates.add(airport.getAddress().getStateCode());

        fromStateComboBox.removeAllItems();
        toStateComboBox.removeAllItems();

        fromStateComboBox.addItem("-");
        toStateComboBox.addItem("-");

        for (String state : airportStates) {
            fromStateComboBox.addItem(state);
            toStateComboBox.addItem(state);
        }

        updateFromAirportComboBox(this.airports);
        updateToAirportComboBox(this.airports);
    }

    /**
     * Replaces content of the fromAirportComboBox with the given airport data. If empty, data is simply added.
     *
     * @param airports airports to be added to the combo box.
     */
    private void updateFromAirportComboBox(List<Airport> airports) {
        fromAirportComboBox.removeAllItems();
        for (Airport airport : airports) {
            fromAirportComboBox.addItem(airport);
        }
    }

    /**
     * Replaces content of the toAirportComboBox with the given airport data. If empty, data is simply added.
     *
     * @param airports airports to be added to the combo box.
     */
    private void updateToAirportComboBox(List<Airport> airports) {
        toAirportComboBox.removeAllItems();
        for (Airport airport : airports) {
            toAirportComboBox.addItem(airport);
        }
    }

    /**
     * Handles the event of the user clicking the "search" button. First, the origin and the destiny airports are
     * retrieved. Then, the shortest path between them is found. A string containing all the airports in the path,
     * separated by " => ", is created and shown to the user through the response label, which is set to visible.
     * Finally, the user search is stored in a database.
     */
    private void handleSearchButtonClick() {
        Airport srcAirport = (Airport) fromAirportComboBox.getSelectedItem();
        Airport desAirport = (Airport) toAirportComboBox.getSelectedItem();

        assert srcAirport != null;
        assert desAirport != null;

        List<Node> pathAirports;

        try {
            pathAirports = this.airportNetwork.getShortestNonDirectPath(srcAirport, desAirport).getNodes();
        } catch (EdgeNotPresentException | NodeNotPresentException e) {
            throw new RuntimeException(e);
        }

        // Creates string displaying sequentially all airports in the path, separated by the string " => "
        String responseString = pathAirports.stream().map(String::valueOf).collect(Collectors.joining(" => "));

        responseLabel.setText(responseString);

        // Make response label visible if it's not already (initial condition)
        responseLabel.setVisible(true);

        UserSearch userSearch = new UserSearch(
                srcAirport, desAirport, new Timestamp(System.currentTimeMillis()), responseString);

        try {
            // Save user search data
            this.airportConnection.insertUserSearchData(userSearch);
        } catch (SQLException e) {
            System.out.println("Unable to save user search data.");
        }
    }

    /**
     * Method called whenever the user changes the selected state in the fromStateComboBox. Filters the airports shown
     * in fromAirportComboBox according to the state where they are located.
     */
    private void handleFromStateChange() {
        String selectedState = (String) fromStateComboBox.getSelectedItem();

        assert selectedState != null;

        if(selectedState.equals("-")) {
            updateFromAirportComboBox(this.airports);
        }

        List<Airport> selectedAirports = new LinkedList<>();

        for (Airport airport : this.airports) {
            if(airport.getAddress().getStateCode().equals(selectedState)) {
                selectedAirports.add(airport);
            }
        }

        updateFromAirportComboBox(selectedAirports);
    }

    /**
     * Method called whenever the user changes the selected state in the toStateComboBox. Filters the airports shown
     * in toAirportComboBox according to the state where they are located.
     */
    private void handleToStateChange() {
        String selectedState = (String) toStateComboBox.getSelectedItem();

        assert selectedState != null;

        if(selectedState.equals("-")) {
            updateToAirportComboBox(this.airports);
        }

        List<Airport> selectedAirports = new LinkedList<>();

        for (Airport airport : this.airports) {
            if(airport.getAddress().getStateCode().equals(selectedState)) {
                selectedAirports.add(airport);
            }
        }

        updateToAirportComboBox(selectedAirports);
    }

}
