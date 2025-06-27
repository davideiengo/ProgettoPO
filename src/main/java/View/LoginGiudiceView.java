package View;

import Controllers.LoginGiudiceController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginGiudiceView extends JFrame {
    private JComboBox<String> comboHackathon;
    private JComboBox<String> comboGiudici;
    private JPasswordField txtPassword;
    private JButton btnAccedi;
    private JButton btnTornaHome;
    private JPanel panel;

    // Controller inizializzato subito
    LoginGiudiceController loginGiudiceController = new LoginGiudiceController(this);

    public LoginGiudiceView() {
        setTitle("Login Giudice");
        setSize(450, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout generale e stile pannello
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(245, 245, 245));
        setContentPane(panel);

        // Titolo della finestra
        JLabel lblTitolo = new JLabel("Accesso Area Giudice");
        lblTitolo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitolo);
        panel.add(Box.createVerticalStrut(20));

        // Campo selezione hackathon
        addLabel("Seleziona Hackathon:");
        comboHackathon = new JComboBox<>();
        styleField(comboHackathon);

        // Campo selezione giudice
        addLabel("Seleziona Giudice:");
        comboGiudici = new JComboBox<>();
        styleField(comboGiudici);

        // Campo inserimento password
        addLabel("Password:");
        txtPassword = new JPasswordField();
        styleField(txtPassword);

        // Pulsante login
        btnAccedi = new JButton("Accedi");
        styleButton(btnAccedi);

        // Pulsante torna indietro
        btnTornaHome = new JButton("Torna alla Home");
        styleButton(btnTornaHome);

        // Listener per tornare alla home
        btnTornaHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginGiudiceController.tornaHome();
            }
        });

        // Quando seleziono un hackathon, carico i giudici associati
        comboHackathon.addActionListener(e -> {
            String hackathonSelezionato = (String) comboHackathon.getSelectedItem();
            if (hackathonSelezionato != null) {
                loginGiudiceController.caricaGiudiciPerHackathon(hackathonSelezionato);
            }
        });

        // Carico gli hackathon all'apertura della finestra
        loginGiudiceController.caricaHackathon();

        // Avvio login quando clicco su "Accedi"
        btnAccedi.addActionListener(e -> loginGiudiceController.eseguiLogin());

        setVisible(true);
    }

    // Metodo per aggiungere etichette
    private void addLabel(String testo) {
        JLabel label = new JLabel(testo);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
    }

    // Stile per campi di input
    private void styleField(JComponent comp) {
        comp.setMaximumSize(new Dimension(300, 30));
        comp.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(comp);
        panel.add(Box.createVerticalStrut(15));
    }

    // Stile per pulsanti
    private void styleButton(JButton btn) {
        btn.setMaximumSize(new Dimension(200, 35));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(200, 220, 255));
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btn);
    }

    public JComboBox<String> getComboHackathon() {
        return comboHackathon;
    }

    public JComboBox<String> getComboGiudici() {
        return comboGiudici;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JButton getBtnAccedi() {
        return btnAccedi;
    }
}
