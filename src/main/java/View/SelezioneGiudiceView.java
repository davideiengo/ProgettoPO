package View;

import Controllers.SelezioneGiudiceController;

import javax.swing.*;
import java.awt.*;

public class SelezioneGiudiceView extends JFrame {
    private JComboBox<String> comboHackathon;
    private JComboBox<String> comboUtenti;
    private JTextField txtPassword;
    private JButton btnConferma;
    private SelezioneGiudiceController controller;

    public SelezioneGiudiceView() {
        setTitle("Selezione Giudice");
        setSize(400, 300);
        setLayout(new GridLayout(4, 1));

        comboHackathon = new JComboBox<>();
        comboUtenti = new JComboBox<>();
        txtPassword = new JTextField();
        btnConferma = new JButton("Conferma");

        add(new JLabel("Seleziona Hackathon:"));
        add(comboHackathon);
        add(new JLabel("Seleziona Utente Registrato:"));
        add(comboUtenti);
        add(new JLabel("Imposta Password Giudice:"));
        add(txtPassword);
        add(btnConferma);

        controller = new SelezioneGiudiceController(this);

        btnConferma.addActionListener(e -> controller.confermaGiudice());

        setVisible(true);
    }

    public JComboBox<String> getComboHackathon() {
        return comboHackathon;
    }

    public JComboBox<String> getComboUtenti() {
        return comboUtenti;
    }

    public JTextField getTxtPassword() {
        return txtPassword;
    }
}

