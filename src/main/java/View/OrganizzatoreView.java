package View;

import Controllers.OrganizzatoreController;
import Entity.HackaThon;
import Models.HackathonModel;
import Models.OrganizzatoreModel;

import javax.swing.*;
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
    private OrganizzatoreController controller;

    public OrganizzatoreView() {
        setContentPane(testSolo);
        setSize(500, 500);
        setVisible(true);
        setName("Organizzatore");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        controller = new OrganizzatoreController(this, new OrganizzatoreModel());

        btnCreaHackathon.addActionListener(e -> {
            String nome = txtNomeOrganizzatore.getText();
            String titolo = txtTitoloHackathon.getText();
            int dimensione = Integer.parseInt(txtDimMaxTeam.getText());

            HackaThon h = controller.creaOrganizzatoreEHackathon(nome, titolo, dimensione);
            HackathonModel.getInstance().aggiungiHackathon(h);

            JOptionPane.showMessageDialog(this, "Hackathon '" + h.getTitoloIdentificativo() + "' creato!");
        });

        btnApriRegistrazioni.addActionListener(e -> { // ✅ nuovo listener
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
                super.mouseClicked(e);
                controller.ritornaHome();
            }
        });
    }

    public String getInserisciNomeTextField() {
        return txtNomeOrganizzatore.getText();
    }
}
