package View;

import javax.swing.*;
import java.awt.*;
/**
 * Vista per la valutazione dei team da parte del giudice.
 * Consente al giudice di selezionare un team e assegnare un voto tra 1 e 10.
 * Inoltre, consente di pubblicare la classifica e tornare alla home.
 */
public class ValutazioneTeamView extends JFrame {
    private JComboBox<String> comboTeam;
    private JSpinner spinnerVoto;
    private JButton btnAssegnaVoto;
    private JButton btnPubblicaClassifica;
    private JButton btnTornaHome;


    /**
     * Costruttore che inizializza la vista di valutazione del team.
     * Configura l'interfaccia utente con il titolo, la selezione del team, il voto e i bottoni per interagire con la logica.
     *
     * @param titoloHackathon Il titolo dell'hackathon a cui si riferisce la valutazione.
     * @param nomeGiudice Il nome del giudice che sta effettuando la valutazione.
     */
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

    /**
     * Restituisce la combobox per la selezione del team.
     *
     * @return JComboBox contenente i team da selezionare.
     */
    public JComboBox<String> getComboTeam() {
        return comboTeam;
    }

    /**
     * Restituisce lo spinner per la selezione del voto.
     *
     * @return JSpinner per selezionare il voto (compreso tra 1 e 10).
     */
    public JSpinner getSpinnerVoto() {
        return spinnerVoto;
    }

    /**
     * Restituisce il bottone per assegnare il voto al team selezionato.
     *
     * @return JButton per assegnare il voto.
     */
    public JButton getBtnAssegnaVoto() {
        return btnAssegnaVoto;
    }

    /**
     * Restituisce il bottone per pubblicare la classifica dei team.
     *
     * @return JButton per pubblicare la classifica.
     */
    public JButton getBtnPubblicaClassifica() {
        return btnPubblicaClassifica;
    }

    /**
     * Restituisce il bottone per tornare alla home.
     *
     * @return JButton per tornare alla schermata principale.
     */
    public JButton getBtnTornaHome() {
        return btnTornaHome;
    }

}

