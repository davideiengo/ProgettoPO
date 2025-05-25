package View;

import javax.swing.*;
import java.awt.*;

public class ValutazioneTeamView extends JFrame {
    private JComboBox<String> comboTeam;
    private JSpinner spinnerVoto;
    private JButton btnAssegnaVoto;
    private JButton btnPubblicaClassifica;
    private JButton btnTornaHome;

    public ValutazioneTeamView(String titoloHackathon, String nomeGiudice) {
        setTitle("Valutazione Team - Giudice: " + nomeGiudice);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Layout principale verticale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Bottone per pubblicare classifica
        btnPubblicaClassifica = new JButton("Pubblica Classifica");
        JPanel panelPubblica = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelPubblica.add(btnPubblicaClassifica);
        mainPanel.add(panelPubblica);

        // Selezione team
        JPanel panelTeam = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTeam.add(new JLabel("Seleziona Team:"));
        comboTeam = new JComboBox<>();
        panelTeam.add(comboTeam);
        mainPanel.add(panelTeam);

        // Selezione voto
        JPanel panelVoto = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelVoto.add(new JLabel("Voto (1-10):"));
        spinnerVoto = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        panelVoto.add(spinnerVoto);
        mainPanel.add(panelVoto);

        // Bottone assegna voto
        btnAssegnaVoto = new JButton("Assegna Voto");
        JPanel panelAssegna = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelAssegna.add(btnAssegnaVoto);
        mainPanel.add(panelAssegna);

        // Bottone torna alla home
        btnTornaHome = new JButton("Torna alla Home");
        JPanel panelHome = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelHome.add(btnTornaHome);
        mainPanel.add(panelHome);

        add(mainPanel);
        setVisible(true);
    }


    public JComboBox<String> getComboTeam() {
        return comboTeam;
    }

    public JSpinner getSpinnerVoto() {
        return spinnerVoto;
    }

    public JButton getBtnAssegnaVoto() {
        return btnAssegnaVoto;
    }

    public JButton getBtnPubblicaClassifica() {
        return btnPubblicaClassifica;
    }

    public JButton getBtnTornaHome() {
        return btnTornaHome;
    }

}

