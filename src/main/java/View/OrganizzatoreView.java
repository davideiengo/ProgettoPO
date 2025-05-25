package View;

import Controllers.OrganizzatoreController;
import Entity.HackaThon;
import Models.HackathonModel;
import Models.OrganizzatoreModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OrganizzatoreView extends JFrame {
    private JPanel testSolo;
    private JTextField txtNomeOrganizzatore;
    private JButton btnCreaHackathon;
    private JTextField txtTitoloHackathon;
    private JTextField txtDimMaxTeam;
    private JButton returnHome;
    private JButton btnApriRegistrazioni;
    private JButton btnSelezionaGiudice;
    private OrganizzatoreController controller;

    public OrganizzatoreView() {
        setTitle("Area Organizzatore");
        setSize(550, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(testSolo);

        // Impostazioni grafiche
        testSolo.setLayout(new BoxLayout(testSolo, BoxLayout.Y_AXIS));
        testSolo.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        testSolo.setBackground(new Color(250, 250, 250));

        // Titolo
        JLabel lblTitolo = new JLabel("Gestione Hackathon");
        lblTitolo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitolo.setForeground(new Color(40, 40, 40));
        testSolo.add(lblTitolo);
        testSolo.add(Box.createVerticalStrut(25));

        // Campi input
        addLabeledField("Nome Organizzatore:", txtNomeOrganizzatore);
        addLabeledField("Titolo Hackathon:", txtTitoloHackathon);
        addLabeledField("Dimensione Max Team:", txtDimMaxTeam);

        testSolo.add(Box.createVerticalStrut(10));

        // Bottoni azioni
        styleButton(btnCreaHackathon, "Crea Hackathon");
        styleButton(btnApriRegistrazioni, "Apri Registrazioni");
        styleButton(btnSelezionaGiudice, "Seleziona Giudice");
        styleButton(returnHome, "Torna alla Home");

        setVisible(true);

        controller = new OrganizzatoreController(this, new OrganizzatoreModel());

        // Listener azioni
        btnCreaHackathon.addActionListener(e -> {
            String nome = txtNomeOrganizzatore.getText();
            String titolo = txtTitoloHackathon.getText();
            int dimensione = Integer.parseInt(txtDimMaxTeam.getText());

            HackaThon h = controller.creaOrganizzatoreEHackathon(nome, titolo, dimensione);
            if (h == null) {
                JOptionPane.showMessageDialog(this, "Errore: esiste già un hackathon con questo titolo!");
            } else {
                HackathonModel.getInstance().aggiungiHackathon(h);
                JOptionPane.showMessageDialog(this, "Hackathon '" + h.getTitoloIdentificativo() + "' creato!");
            }
        });

        btnApriRegistrazioni.addActionListener(e -> {
            String nome = txtNomeOrganizzatore.getText();
            String titolo = txtTitoloHackathon.getText();
            boolean successo = controller.apriRegistrazioni(titolo, nome);
            if (successo) {
                JOptionPane.showMessageDialog(this, "Registrazioni aperte per l’hackathon " + titolo + "!");
            } else {
                JOptionPane.showMessageDialog(this, "Errore: l’hackathon non esiste o non sei l’organizzatore");
            }
        });

        returnHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.ritornaHome();
            }
        });

        btnSelezionaGiudice.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.apriVistaSelezioneGiudice();
            }
        });
    }

    // Aggiunta di un campo con etichetta
    private void addLabeledField(String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        field.setMaximumSize(new Dimension(300, 30));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);

        testSolo.add(label);
        testSolo.add(Box.createVerticalStrut(5));
        testSolo.add(field);
        testSolo.add(Box.createVerticalStrut(15));
    }

    // Stile uniforme per i bottoni
    private void styleButton(JButton btn, String testo) {
        btn.setText(testo);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(200, 220, 255));
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 35));
        testSolo.add(Box.createVerticalStrut(10));
        testSolo.add(btn);
    }


    public String getInserisciNomeTextField() {
        return txtNomeOrganizzatore.getText();
    }
}
