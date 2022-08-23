package labprog2.gui;

import labprog2.model.Airport;
import labprog2.model.AirportNetwork;
import labprog2.util.graph.Node;
import labprog2.util.graph.exceptions.EdgeNotPresentException;
import labprog2.util.graph.exceptions.NodeNotPresentException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class AirportAppFrame extends JFrame {

    private JButton searchButton;
    private JPanel panelAirportApp;
    private JComboBox<Airport> fromAirportComboBox;
    private JComboBox<Airport> toAirportComboBox;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JLabel responseLabel;

    private AirportNetwork airportNetwork;

    public AirportAppFrame(Airport[] airports)  {
        this.airportNetwork = new AirportNetwork(airports);

        setContentPane(panelAirportApp);
        setTitle("Airport App");
        pack();
        setVisible(true);
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        responseLabel.setVisible(false);

        for (Airport airport : airports) {
            fromAirportComboBox.addItem(airport);
            toAirportComboBox.addItem(airport);
        }


        searchButton.addActionListener(actionEvent -> {
            Airport srcAirport = (Airport) fromAirportComboBox.getSelectedItem();
            Airport desAirport = (Airport) toAirportComboBox.getSelectedItem();

            List<Node> pathAirports;

            try {
                pathAirports = this.airportNetwork.getShortestNonDirectPath(srcAirport, desAirport).getNodes();
            } catch (EdgeNotPresentException | NodeNotPresentException e) {
                throw new RuntimeException(e);
            }

            for (Node airport: pathAirports) {
                System.out.println(airport);
            }

            String responseString = pathAirports.stream().map(String::valueOf).collect(Collectors.joining(" => "));

            responseLabel.setText(responseString);

            responseLabel.setVisible(true);

        });
    }

}
