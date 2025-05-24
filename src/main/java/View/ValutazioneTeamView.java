package View;

import Controllers.ValutazioneTeamController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ValutazioneTeamView extends JFrame {
    private JComboBox<String> comboTeam;
    private JSpinner spinnerVoto;
    private JButton btnAssegnaVoto;
    private JButton btnPubblicaClassifica;

    public ValutazioneTeamView(String titoloHackathon, String nomeGiudice) {
        setTitle("Valutazione Team - Giudice: " + nomeGiudice);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        comboTeam = new JComboBox<>();
        spinnerVoto = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        btnAssegnaVoto = new JButton("Assegna Voto");
        btnPubblicaClassifica = new JButton("Pubblica Classifica");

        add(btnPubblicaClassifica);
        add(new JLabel("Seleziona Team:"));
        add(comboTeam);
        add(new JLabel("Voto (1-10):"));
        add(spinnerVoto);
        add(btnAssegnaVoto);
        setLayout(new GridLayout(6, 1));
        add(btnPubblicaClassifica);
        add(new JLabel("Seleziona Team:"));
        add(comboTeam);
        add(new JLabel("Voto (1-10):"));
        add(spinnerVoto);
        add(btnAssegnaVoto);

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
}

