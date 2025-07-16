package View;

import Controllers.LoginGiudiceController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * La vista per il login dell'area giudice. Gestisce la selezione dell'hackathon,
 * la scelta del giudice e l'autenticazione tramite password.
 */
public class LoginGiudiceView extends JFrame {
    private JComboBox<String> comboHackathon;
    private JComboBox<String> comboGiudici;
    private JPasswordField txtPassword;
    private JButton btnAccedi;
    private JButton btnTornaHome;
    private JPanel panel;


    LoginGiudiceController loginGiudiceController = new LoginGiudiceController(this);

    /**
     * Costruttore della vista di login per l'area giudice. Imposta la UI e i listener
     * necessari per il corretto funzionamento.
     */
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

    /**
     * Aggiunge un'etichetta alla finestra.
     *
     * @param testo Il testo da visualizzare nell'etichetta.
     */
    private void addLabel(String testo) {
        JLabel label = new JLabel(testo);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
    }

    /**
     * Applica lo stile ad un componente di tipo campo di inserimento (es. ComboBox, TextField).
     *
     * @param comp Il componente da stilizzare.
     */
    private void styleField(JComponent comp) {
        comp.setMaximumSize(new Dimension(300, 30));
        comp.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(comp);
        panel.add(Box.createVerticalStrut(15));
    }

    /**
     * Applica lo stile ad un pulsante.
     *
     * @param btn Il pulsante da stilizzare.
     */
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

    /**
     * Restituisce la ComboBox contenente gli hackathon disponibili.
     *
     * @return La ComboBox degli hackathon.
     */
    public JComboBox<String> getComboHackathon() {
        return comboHackathon;
    }

    /**
     * Restituisce la ComboBox contenente i giudici disponibili.
     *
     * @return La ComboBox dei giudici.
     */
    public JComboBox<String> getComboGiudici() {
        return comboGiudici;
    }

    /**
     * Restituisce il campo password.
     *
     * @return Il campo password.
     */
    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    /**
     * Restituisce il pulsante "Accedi".
     *
     * @return Il pulsante "Accedi".
     */
    public JButton getBtnAccedi() {
        return btnAccedi;
    }
}
