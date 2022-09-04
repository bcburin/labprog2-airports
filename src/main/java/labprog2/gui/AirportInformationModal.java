package labprog2.gui;

import labprog2.model.Airport;

import javax.swing.*;

public class AirportInformationModal extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel iataLabel;
    private JLabel icaoLabel;
    private JLabel nameLabel;
    private JLabel cityLabel;
    private JLabel stateLabel;
    private JLabel postalCodeLabel;
    private JLabel phoneLabel;
    private JLabel websiteLabel;

    /**
     * Creates a new modal that displays the available information for an airport.
     *
     * @param airport airport whose information will be shown.
     */
    public AirportInformationModal(Airport airport) {
        setupModal();

        iataLabel.setText(airport.getIata());
        icaoLabel.setText(airport.getIcao());
        nameLabel.setText(airport.getName());
        cityLabel.setText(airport.getAddress().getCounty());
        stateLabel.setText(airport.getAddress().getState());
        postalCodeLabel.setText(airport.getAddress().getPostalCode());
        phoneLabel.setText(airport.getPhone());
        websiteLabel.setText(airport.getWebsite());

        buttonOK.addActionListener(e -> onOK());
    }

    /**
     * Performs basic setup operations for the modal.
     */
    private void setupModal() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }

    /**
     * Disposes the current modal.
     */
    private void onOK() { dispose(); }

}
