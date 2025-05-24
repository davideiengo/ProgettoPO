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
    LoginGiudiceController loginGiudiceController = new LoginGiudiceController(this);


    public LoginGiudiceView() {
        setTitle("Login Giudice");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        btnTornaHome = new JButton("Torna alla Home");
        comboHackathon = new JComboBox<>();
        comboGiudici = new JComboBox<>();
        txtPassword = new JPasswordField();
        btnAccedi = new JButton("Accedi");

        add(new JLabel("Seleziona Hackathon:"));
        add(comboHackathon);
        add(new JLabel("Seleziona Giudice:"));
        add(comboGiudici);
        add(new JLabel("Password:"));
        add(txtPassword);
        add(new JLabel()); // spazio vuoto
        add(btnAccedi);
        add(new JLabel());
        add(btnTornaHome);

        setVisible(true);
        btnTornaHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginGiudiceController.tornaHome();
            }
        });

        comboHackathon.addActionListener(e -> {
            String hackathonSelezionato = (String) comboHackathon.getSelectedItem();
            if (hackathonSelezionato != null) {
                loginGiudiceController.caricaGiudiciPerHackathon(hackathonSelezionato);
            }
        });

        loginGiudiceController.caricaHackathon();

        btnAccedi.addActionListener(e -> loginGiudiceController.eseguiLogin());

    }

    public JButton getBtnTornaHome() {
        return btnTornaHome;
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

