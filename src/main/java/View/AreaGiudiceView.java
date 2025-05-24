package View;

import javax.swing.*;
import java.awt.*;

public class AreaGiudiceView extends JFrame {
    private JLabel lblTitolo;
    private JTextArea txtInfo;

    public AreaGiudiceView(String nomeGiudice, String titoloHackathon) {
        setTitle("Area Giudice");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        lblTitolo = new JLabel("Benvenuto, " + nomeGiudice + " - Hackathon: " + titoloHackathon, SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitolo, BorderLayout.NORTH);

        txtInfo = new JTextArea("In questa area potrai valutare i team iscritti all'hackathon.\nFunzionalità in sviluppo...");
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtInfo.setMargin(new Insets(10, 10, 10, 10));
        add(new JScrollPane(txtInfo), BorderLayout.CENTER);
    }
}

