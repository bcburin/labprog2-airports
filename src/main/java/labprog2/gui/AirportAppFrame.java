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

    private final AirportNetwork airportNetwork;

    private final MySQLAirportDbConnection airportConnection;

    private final List<String> airportStates;

    private final Airport[] airports;

    public AirportAppFrame(Airport[] airports, MySQLAirportDbConnection airportConnection)  {
        this.airports = airports;
        this.airportNetwork = new AirportNetwork(this.airports);
        this.airportConnection = airportConnection;
        this.airportStates = new LinkedList<>();

        for(Airport airport : airports) airportStates.add(airport.getAddress().getStateCode());

        setupPanel();

        setAirportStateComboBoxes();

        searchButton.addActionListener(actionEvent -> handleSearchButtonClick());

        fromStateComboBox.addActionListener(actionEvent -> handleFromStateChange());

        toStateComboBox.addActionListener(actionEvent -> handleToStateChange());

    }

    private void setupPanel() {
        setContentPane(mainPanel);
        setTitle("Airport App");
        pack();
        setVisible(true);
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        responseLabel.setVisible(false);
    }

    private void setAirportStateComboBoxes() {
        fromStateComboBox.removeAllItems();
        toStateComboBox.removeAllItems();

        fromStateComboBox.addItem("-");
        toStateComboBox.addItem("-");

        for (String state : this.airportStates) {
            fromStateComboBox.addItem(state);
            toStateComboBox.addItem(state);
        }

        updateFromAirportComboBox(this.airports);
        updateToAirportComboBox(this.airports);
    }

    private void updateFromAirportComboBox(Airport[] airports) {
        fromAirportComboBox.removeAllItems();
        for (Airport airport : airports) {
            fromAirportComboBox.addItem(airport);
        }
    }

    private void updateToAirportComboBox(Airport[] airports) {
        toAirportComboBox.removeAllItems();
        for (Airport airport : airports) {
            toAirportComboBox.addItem(airport);
        }
    }

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

        String responseString = pathAirports.stream().map(String::valueOf).collect(Collectors.joining(" => "));

        responseLabel.setText(responseString);

        responseLabel.setVisible(true);

        UserSearch userSearch = new UserSearch(srcAirport, desAirport, new Timestamp(System.currentTimeMillis()));

        try {
            this.airportConnection.insertUserSearchData(userSearch);
        } catch (SQLException e) {
            System.out.println("Unable to save user search data.");
        }
    }

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

        updateFromAirportComboBox(selectedAirports.toArray(new Airport[0]));
    }

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

        updateToAirportComboBox(selectedAirports.toArray(new Airport[0]));
    }

}
