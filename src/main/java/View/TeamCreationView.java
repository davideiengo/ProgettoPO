package View;

import Controllers.TeamController;

import javax.swing.*;

public class TeamCreationView extends JFrame {
    private JPanel mainPanel;
    private JTextField txtNomeTeam;
    private JComboBox<String> comboHackathon;
    private JButton btnScegliMembri;
    private JButton btnCreaTeam;
    private JButton btnIndietro;

    private TeamController controller;

    public TeamCreationView() {
        setTitle("Creazione Team");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setContentPane(mainPanel);
        setVisible(true);

        controller = new TeamController(this);

        btnScegliMembri.addActionListener(e -> controller.apriSceltaMembri());
        btnCreaTeam.addActionListener(e -> controller.creaTeam());
        btnIndietro.addActionListener(e -> controller.tornaAllaHome());
    }

    public JTextField getTxtNomeTeam() {
        return txtNomeTeam;
    }

    public JComboBox<String> getComboHackathon() {
        return comboHackathon;
    }

    public void setController(TeamController controller) {
        this.controller = controller;
    }
}
