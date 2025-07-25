package View;

import Controllers.SelezioneGiudiceController;

import javax.swing.*;
import java.awt.*;
/**
 * La vista per l'assegnazione di un utente come giudice per un hackathon.
 * Permette di selezionare un hackathon, un utente registrato e impostare una password per il giudice.
 */
public class SelezioneGiudiceView extends JFrame {
    private JComboBox<String> comboHackathon;
    private JComboBox<String> comboUtenti;
    private JTextField txtPassword;
    private JButton btnConferma;
    private SelezioneGiudiceController controller;


    /**
     * Costruttore della vista Selezione Giudice. Imposta la UI e i listener
     * necessari per la selezione del giudice.
     */
    public SelezioneGiudiceView() {
        setTitle("Selezione Giudice");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Pannello principale con layout verticale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(250, 250, 250));
        setContentPane(mainPanel);

        // Titolo finestra
        JLabel lblTitolo = new JLabel("Assegna Utente come Giudice", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitolo);
        mainPanel.add(Box.createVerticalStrut(20));

        // Combo Hackathon
        addLabeledField(mainPanel, "Seleziona Hackathon:", comboHackathon = new JComboBox<>());

        // Combo Utente
        addLabeledField(mainPanel, "Seleziona Utente Registrato:", comboUtenti = new JComboBox<>());

        // Campo Password
        addLabeledField(mainPanel, "Imposta Password Giudice:", txtPassword = new JTextField());


        btnConferma = new JButton("Conferma");
        btnConferma.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnConferma.setBackground(new Color(200, 220, 255));
        btnConferma.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btnConferma.setFocusPainted(false);
        btnConferma.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConferma.setMaximumSize(new Dimension(180, 35));
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(btnConferma);

        // Controller che gestisce la logica
        controller = new SelezioneGiudiceController(this);

        // Quando clicco su conferma viene assegnato il giudice
        btnConferma.addActionListener(e -> controller.confermaGiudice());

        setVisible(true);
    }

    /**
     * Aggiunge un campo di input (combo box o text field) con la relativa etichetta
     * al pannello.
     *
     * @param panel     Il pannello a cui aggiungere l'elemento.
     * @param labelText Il testo dell'etichetta.
     * @param field     Il componente da aggiungere (ComboBox o TextField).
     */
    private void addLabeledField(JPanel panel, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        field.setMaximumSize(new Dimension(300, 30));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(field);
        panel.add(Box.createVerticalStrut(15));
    }

    /**
     * Restituisce la combo box degli hackathon.
     *
     * @return La combo box contenente gli hackathon.
     */
    public JComboBox<String> getComboHackathon() {
        return comboHackathon;
    }

    /**
     * Restituisce la combo box degli utenti.
     *
     * @return La combo box contenente gli utenti registrati.
     */
    public JComboBox<String> getComboUtenti() {
        return comboUtenti;
    }

    /**
     * Restituisce il campo di testo per la password del giudice.
     *
     * @return Il campo di testo per la password.
     */
    public JTextField getTxtPassword() {
        return txtPassword;
    }



}

