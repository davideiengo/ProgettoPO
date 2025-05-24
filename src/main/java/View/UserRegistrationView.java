package View;

import Controllers.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UserRegistrationView extends JFrame {
    private JPanel panelMain;
    private JTextField txtNomeUtente;
    private JButton btnRegistra;
    private JComboBox<String> comboHackathon;
    private JButton btnTornaHome;
    private UserController controller;

    public UserRegistrationView() {
        panelMain = new JPanel(new GridLayout(4, 1, 10, 10));
        txtNomeUtente = new JTextField();
        btnRegistra = new JButton("Registrati");
        comboHackathon = new JComboBox<>();

        panelMain.add(new JLabel("Nome Utente:"));
        panelMain.add(txtNomeUtente);
        panelMain.add(new JLabel("Seleziona Hackathon:"));
        panelMain.add(comboHackathon);
        panelMain.add(btnRegistra);
        panelMain.add(btnTornaHome);

        setContentPane(panelMain);
        setTitle("Registrazione Utente");
        setSize(400, 200);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        controller = new UserController(this);
        controller.caricaHackathonDisponibili();

        btnRegistra.addActionListener(e -> {
                String nomeUtente = txtNomeUtente.getText();
        String titoloHackathon = (String) comboHackathon.getSelectedItem();
        controller.registraUtente(nomeUtente, titoloHackathon);
    });
        btnTornaHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                controller.ritornaHome();
            }
        });
    }

    public void setHackathonList(ArrayList<String> titoli) {
        comboHackathon.removeAllItems();
        for (String titolo : titoli) {
            comboHackathon.addItem(titolo);
        }
    }
}



