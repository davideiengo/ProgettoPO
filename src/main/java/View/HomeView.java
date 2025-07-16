package View;

import Controllers.HomeController;
import Entity.HackaThon;
import Entity.Team;
import Models.HackathonModel;
import Models.TeamModel;
import PostgresDAO.PostgresVotoDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * La vista principale dell'applicazione che gestisce l'interfaccia utente per le operazioni
 * di gestione degli hackathon, registrazione degli utenti, creazione dei team e visualizzazione delle classifiche.
 */
public class HomeView extends JFrame {
    private JPanel homeView;
    private JButton btnRegistraUtente;
    private JButton btnLoginOrganizzatore;
    private JButton btnGestioneTeam;
    private JButton btnAreaGiudice;
    private JComboBox<String> comboHackathonClassifica;
    private JButton btnVisualizzaClassifica;
    private HomeController controller;

    /**
     * Costruttore della vista principale, che imposta l'interfaccia utente, i layout,
     * i pulsanti e i listener per le azioni dell'utente.
     */
    public HomeView() {
        controller = new HomeController(this);
        setTitle("Hackathon Manager - Home");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 480);
        setLocationRelativeTo(null);

        // === Barra menu ===
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem menuHome = new JMenuItem("Torna alla Home");
        JMenuItem menuEsci = new JMenuItem("Esci");
        JMenu menuAiuto = new JMenu("Aiuto");
        JMenuItem menuGuida = new JMenuItem("Guida utilizzo");
        JMenuItem menuInfo = new JMenuItem("Info progetto");

        menuFile.add(menuHome);
        menuFile.addSeparator();
        menuFile.add(menuEsci);
        menuAiuto.add(menuInfo);
        menuAiuto.add(menuGuida);
        menuBar.add(menuFile);
        menuBar.add(menuAiuto);
        setJMenuBar(menuBar);


        homeView.setLayout(new BorderLayout(20, 20));
        homeView.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        homeView.setBackground(new Color(250, 250, 250));

        // Titolo
        JLabel title = new JLabel("Benvenuto nel Gestore Hackathon", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(30, 30, 30));
        homeView.add(title, BorderLayout.NORTH);

        // Pannello centrale coi bottoni
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        centerPanel.setOpaque(false); // mantiene il background trasparente

        // Stile
        styleButton(btnRegistraUtente);
        styleButton(btnLoginOrganizzatore);
        styleButton(btnGestioneTeam);
        styleButton(btnAreaGiudice);
        styleButton(btnVisualizzaClassifica);

        // ComboBox
        comboHackathonClassifica.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboHackathonClassifica.setMaximumRowCount(5);

        centerPanel.add(btnRegistraUtente);
        centerPanel.add(btnLoginOrganizzatore);
        centerPanel.add(btnGestioneTeam);
        centerPanel.add(btnAreaGiudice);
        centerPanel.add(comboHackathonClassifica);
        centerPanel.add(btnVisualizzaClassifica);

        homeView.add(centerPanel, BorderLayout.CENTER);
        add(homeView);

        setVisible(true);

        initListeners();
        caricaHackathonPerClassifica();

        // Menu azioni
        menuHome.addActionListener(e -> JOptionPane.showMessageDialog(this, "Sei già nella Home."));
        menuEsci.addActionListener(e -> System.exit(0));
        menuInfo.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Progetto PO - Gestione Hackathon\nRealizzato da Antonio Giaccio e Davide Iengo\nAnno Accademico 2024/25",
                "Info", JOptionPane.INFORMATION_MESSAGE));

        menuGuida.addActionListener(e -> {
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            textArea.setText("""
        ✨ BENVENUTO NEL GESTORE HACKATHON ✨

        Questa guida ti accompagnerà passo dopo passo per utilizzare correttamente l'applicazione:

        1️⃣ Vai nella sezione "Organizzatore"
           → Inserisci il nome e crea un nuovo hackathon.

        2️⃣ Vai su "Registra Utente"
           → Inserisci i dati dell’utente e seleziona l’hackathon appena creato.

        3️⃣ Torna in "Organizzatore"
           → Clicca su "Scelta Giudice" per selezionare un utente registrato come giudice.

        4️⃣ Entra in "Area Giudice"
           → Scegli l’hackathon, seleziona il giudice e inserisci la password scelta in precedenza.

        5️⃣ Seleziona un team, assegna il voto e clicca su "Pubblica Classifica".

        6️⃣ Torna nella Home e clicca su "Visualizza Classifica" per vedere i risultati finali.

        ℹ️ Assicurati che ogni passaggio venga eseguito correttamente per evitare errori!
        """);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 350));

            JOptionPane.showMessageDialog(this, scrollPane, "Guida all'utilizzo", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    /**
     * Stile personalizzato per i pulsanti.
     *
     * @param btn Il pulsante da stilizzare.
     */
    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(200, 220, 255));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    /**
     * Inizializza tutti i listener per i pulsanti e le azioni associate.
     */
    private void initListeners() {
        btnLoginOrganizzatore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.apriOrganizzatore();
            }
        });

        btnRegistraUtente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.apriRegistrazioneUtente();
            }
        });

        btnGestioneTeam.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new TeamCreationView(); // Apro la schermata di gestione team
            }
        });

        btnAreaGiudice.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.apriLoginGiudice();
            }
        });

        // Quando clicco su visualizza classifica
        btnVisualizzaClassifica.addActionListener(e -> {
            String titolo = (String) comboHackathonClassifica.getSelectedItem();
            if (titolo != null) {
                HackaThon h = HackathonModel.getInstance().getHackathonByTitolo(titolo);
                if (h != null) {
                    TeamModel teamModel = new TeamModel();
                    List<Team> teamList = teamModel.trovaPerHackathon(titolo);

                    PostgresVotoDAO votoDAO = new PostgresVotoDAO();

                    boolean almenoUnVoto = false;

                    // Carica i voti nel team
                    for (Team team : teamList) {
                        List<Integer> voti = votoDAO.getVotiPerTeam(team.getNomeTeam());
                        for (int i = 0; i < voti.size(); i++) {
                            team.assegnaVoto("db" + i, voti.get(i));
                        }
                        if (!voti.isEmpty()) {
                            almenoUnVoto = true;
                        }
                    }

                    if (almenoUnVoto) {
                        // Mostra classifica
                        teamList.sort((a, b) -> Double.compare(b.getMediaVoti(), a.getMediaVoti()));
                        StringBuilder sb = new StringBuilder("🏆 Classifica Team:\n");
                        int pos = 1;
                        for (Team team : teamList) {
                            if (team.isVotato()) {
                                sb.append(pos++).append(". ")
                                        .append(team.getNomeTeam())
                                        .append(" - Voto medio: ")
                                        .append(String.format("%.2f", team.getMediaVoti()))
                                        .append("\n");
                            }
                        }
                        JOptionPane.showMessageDialog(this, sb.toString());
                    } else {
                        JOptionPane.showMessageDialog(this, "La classifica per questa hackathon non è ancora stata pubblicata.");
                    }
                }
            }
        });
    }

    /**
     * Carica tutti gli hackathon disponibili nella combobox per la visualizzazione della classifica.
     */
    private void caricaHackathonPerClassifica() {
        comboHackathonClassifica.removeAllItems();
        for (HackaThon h : HackathonModel.getInstance().getTutti()) {
            comboHackathonClassifica.addItem(h.getTitoloIdentificativo());
        }
    }
}
