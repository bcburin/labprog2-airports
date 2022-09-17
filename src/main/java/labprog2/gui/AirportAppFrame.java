package labprog2.gui;

import labprog2.model.Airport;
import labprog2.model.AirportNetwork;
import labprog2.model.UserSearch;
import labprog2.util.graph.Path;
import labprog2.util.graph.exceptions.NodeNotPresentException;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AirportAppFrame extends JFrame {

    private JButton searchButton;
    private JPanel mainPanel;
    private JComboBox<Airport> fromAirportComboBox;
    private JComboBox<Airport> toAirportComboBox;
    private JComboBox<String> toStateComboBox;
    private JComboBox<String> fromStateComboBox;
    private JLabel responseLabel;
    private JComboBox<Airport> getInformationComboBox;
    private JButton getInformationButton;

    private final AirportNetwork airportNetwork;

    private final Connection connection;

    private final List<Airport> airports;

    /**
     * Gets airport data from database. Setups frame and adds handlers to components. Informs user in case of
     * failure.
     *
     * @param connection active connection to the database.
     */
    public AirportAppFrame(Connection connection) {
        this.connection = connection;

        try {
            // Get airport data from database and create network
            this.airports = Airport.getAllAirportDataDb(this.connection);
            this.airportNetwork = new AirportNetwork(this.airports);
        } catch (SQLException e) {
            // Inform user of failure in getting data
            JOptionPane.showMessageDialog(this, "Unable to get airport data");
            throw new RuntimeException(e);
        }

        setupPanel();

        setAirportStateComboBoxes();

        searchButton.addActionListener(actionEvent -> handleSearchButtonClick());

        fromStateComboBox.addActionListener(actionEvent -> handleFromStateChange());

        toStateComboBox.addActionListener(actionEvent -> handleToStateChange());

        getInformationButton.addActionListener(actionEvent -> handleGetInformationButtonClick());
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
        // Components regarding query results should not be visible at first
        responseLabel.setVisible(false);
        getInformationButton.setVisible(false);
        getInformationComboBox.setVisible(false);
    }

    /**
     * Initializes the state filter combo boxes. Retrieves all states from the collection of airports and populates
     * the combo box items with them. Also adds the '-' option to disable state filters.
     */
    private void setAirportStateComboBoxes() {
        Set<String> airportStates = new HashSet<>();

        for (Airport airport : this.airports) airportStates.add(airport.getAddress().getStateCode());

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
     * Shows a string containing all airports of the path to the user. Adds these airports to the
     * getInformationComboBox. Makes all components related to the display of the result visible.
     *
     * @param pathAirports list of airports in the result path of the queried route.
     */
    private void setupResultComponents(List<Airport> pathAirports) {
        // Creates string displaying sequentially all airports in the path, separated by the string " => "
        String responseString = pathAirports.stream().map(String::valueOf).collect(Collectors.joining(" => "));

        responseLabel.setText(responseString);

        // Add path airports to getInformationComboBox
        for (Airport airport : pathAirports) {
            getInformationComboBox.addItem(airport);
        }

        // Make components visible if they are not already (initial condition)
        responseLabel.setVisible(true);
        getInformationButton.setVisible(true);
        getInformationComboBox.setVisible(true);
    }

    /**
     * Handles the event of the user clicking the "search" button. First, the origin and the destiny airports are
     * retrieved. Then, the shortest path between them is found. A string containing all the airports in the path,
     * separated by " => ", is created and shown to the user through the response label.
     * Finally, the user search is stored in a database.
     */
    private void handleSearchButtonClick() {
        Airport srcAirport = (Airport) fromAirportComboBox.getSelectedItem();
        Airport desAirport = (Airport) toAirportComboBox.getSelectedItem();

        assert srcAirport != null;
        assert desAirport != null;

        Path<Airport> pathAirports;

        try {
            pathAirports = this.airportNetwork.getShortestNonDirectPath(srcAirport, desAirport);
        } catch (NodeNotPresentException e) {
            throw new RuntimeException(e);
        }

        setupResultComponents(pathAirports.getNodes());

        // Create user search object
        UserSearch userSearch = new UserSearch(
                srcAirport,
                desAirport,
                new Timestamp(System.currentTimeMillis()),
                pathAirports.getNodes().stream().map(String::valueOf).collect(Collectors.joining(" ")));

        try {
            // Save user search data
            userSearch.insertDb(this.connection);
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

        if (selectedState.equals("-")) {
            updateFromAirportComboBox(this.airports);
            return;
        }

        List<Airport> selectedAirports = new LinkedList<>();

        for (Airport airport : this.airports) {
            if (airport.getAddress().getStateCode().equals(selectedState)) {
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

        if (selectedState.equals("-")) {
            updateToAirportComboBox(this.airports);
            return;
        }

        List<Airport> selectedAirports = new LinkedList<>();

        for (Airport airport : this.airports) {
            if (airport.getAddress().getStateCode().equals(selectedState)) {
                selectedAirports.add(airport);
            }
        }

        updateToAirportComboBox(selectedAirports);
    }

    /**
     * Method called whenever the user clicks the getInformationButton. Opens a modal that displays the available
     * information for the selected airport in getInformationComboBox.
     */
    private void handleGetInformationButtonClick() {
        // Get selected airport
        Airport selectedAirport = (Airport) getInformationComboBox.getSelectedItem();

        assert  selectedAirport != null;

        AirportInformationModal infoModal = new AirportInformationModal(selectedAirport);

        infoModal.pack();

        infoModal.setVisible(true);
    }

}
