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
        setTitle("Registrazione Utente");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        panelMain.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panelMain.setBackground(new Color(245, 245, 245));

        // Titolo
        JLabel titolo = new JLabel("Registrazione Nuovo Utente");
        titolo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMain.add(titolo);
        panelMain.add(Box.createVerticalStrut(20));

        // Campi
        styleLabeledField("Nome Utente:", txtNomeUtente);
        styleLabeledField("Seleziona Hackathon:", comboHackathon);

        // Bottoni
        styleButton(btnRegistra);
        styleButton(btnTornaHome);

        setContentPane(panelMain);
        setVisible(true);

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
                controller.ritornaHome();
            }
        });
    }

    private void styleLabeledField(String label, JComponent field) {
        JLabel lbl = new JLabel(label);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelMain.add(lbl);
        field.setMaximumSize(new Dimension(300, 30));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMain.add(field);
        panelMain.add(Box.createVerticalStrut(10));
    }

    private void styleButton(JButton btn) {
        btn.setMaximumSize(new Dimension(200, 35));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(200, 220, 255));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMain.add(Box.createVerticalStrut(10));
        panelMain.add(btn);
    }


    public void setHackathonList(ArrayList<String> titoli) {
        comboHackathon.removeAllItems();
        for (String titolo : titoli) {
            comboHackathon.addItem(titolo);
        }
    }
}



