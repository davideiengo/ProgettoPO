package View;

import Controllers.TeamController;

import javax.swing.*;
import java.awt.*;

/**
 * Vista per la creazione di un nuovo team per un hackathon.
 * Consente di inserire il nome del team, selezionare un hackathon e scegliere i membri del team.
 */
public class TeamCreationView extends JFrame {
    private JPanel mainPanel;
    private JTextField txtNomeTeam;
    private JComboBox<String> comboHackathon;
    private JButton btnScegliMembri;
    private JButton btnCreaTeam;
    private JButton btnIndietro;

    private TeamController controller;

    /**
     * Costruttore della vista di creazione del team.
     * Imposta la UI e i listener per la gestione della creazione del team.
     */
    public TeamCreationView() {
        setTitle("Creazione Team");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 420);
        setLocationRelativeTo(null);
        setContentPane(mainPanel);

        // Impostazioni di stile
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(new Color(245, 245, 245)); // grigio chiaro

        // Titolo
        JLabel titolo = new JLabel("Crea un nuovo Team");
        titolo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titolo.setForeground(new Color(40, 40, 40));
        titolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titolo);
        mainPanel.add(Box.createVerticalStrut(25));

        styleComponent(txtNomeTeam);
        styleComponent(comboHackathon);
        styleButton(btnScegliMembri);
        styleButton(btnCreaTeam);
        styleButton(btnIndietro);

        setVisible(true);

        controller = new TeamController(this);
        btnScegliMembri.addActionListener(e -> controller.apriSceltaMembri());
        btnCreaTeam.addActionListener(e -> controller.creaTeam());
        btnIndietro.addActionListener(e -> controller.tornaAllaHome());
    }

    /**
     * Applica uno stile uniforme per i componenti.
     * Imposta la dimensione massima e l'allineamento al centro del pannello.
     *
     * @param comp Il componente da stilizzare.
     */
    private void styleComponent(JComponent comp) {
        comp.setMaximumSize(new Dimension(300, 30));
        comp.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(comp);
        mainPanel.add(Box.createVerticalStrut(10));
    }

    /**
     * Applica uno stile uniforme ai pulsanti.
     * Imposta la dimensione massima, il font, il colore di sfondo e il bordo.
     *
     * @param btn Il pulsante da stilizzare.
     */
    private void styleButton(JButton btn) {
        btn.setMaximumSize(new Dimension(200, 35));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(200, 220, 255));
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(btn);
        mainPanel.add(Box.createVerticalStrut(10));
    }

    /**
     * Restituisce il campo di testo per il nome del team.
     *
     * @return Il campo di testo per il nome del team.
     */
    public JTextField getTxtNomeTeam() {
        return txtNomeTeam;
    }

    /**
     * Restituisce la combo box contenente gli hackathon.
     *
     * @return La combo box contenente gli hackathon.
     */
    public JComboBox<String> getComboHackathon() {
        return comboHackathon;
    }

}
