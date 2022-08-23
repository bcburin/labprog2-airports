package labprog2.gui;

import labprog2.model.Airport;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AirportAppFrame extends JFrame {

    private JButton searchButton;
    private JPanel panelAirportApp;
    private JComboBox<Airport> fromAirportComboBox;
    private JComboBox<Airport> toAirportComboBox;
    private JComboBox comboBox1;
    private JComboBox comboBox2;

    public AirportAppFrame(Airport[] airports)  {
        setContentPane(panelAirportApp);
        setTitle("Airport App");
        pack();
        setVisible(true);
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (Airport airport : airports) {
            fromAirportComboBox.addItem(airport);
            toAirportComboBox.addItem(airport);
        }


        searchButton.addActionListener(actionEvent -> {

        });
    }

}
