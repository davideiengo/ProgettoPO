package View;

import Controllers.HomeController;
import Entity.HackaThon;
import Entity.Team;
import Models.HackathonModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeView extends JFrame {
    private JPanel homeView;
    private JButton btnRegistraUtente;
    private JButton btnLoginOrganizzatore;
    private JButton btnGestioneTeam;
    private JButton btnAreaGiudice;
    private JComboBox<String> comboHackathonClassifica;
    private JButton btnVisualizzaClassifica;
    private HomeController controller;

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
        JMenuItem menuInfo = new JMenuItem("Info progetto");

        menuFile.add(menuHome);
        menuFile.addSeparator();
        menuFile.add(menuEsci);
        menuAiuto.add(menuInfo);
        menuBar.add(menuFile);
        menuBar.add(menuAiuto);
        setJMenuBar(menuBar);

        // === Layout e stile solo sul pannello già esistente (homeView) ===
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

        // Stile uniforme ai bottoni
        styleButton(btnRegistraUtente);
        styleButton(btnLoginOrganizzatore);
        styleButton(btnGestioneTeam);
        styleButton(btnAreaGiudice);
        styleButton(btnVisualizzaClassifica);

        // ComboBox stile coerente
        comboHackathonClassifica.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboHackathonClassifica.setMaximumRowCount(5);

        // Aggiungo i componenti
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
                "Progetto PO - Gestione Hackathon\nRealizzato da Antonio Giaccio e Davide Iovine\nAnno Accademico 2024/25",
                "Info", JOptionPane.INFORMATION_MESSAGE));
    }

    // Applico uno stile coerente a ogni bottone
    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(200, 220, 255));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }


    // Inizializzo tutti i listener dei pulsanti
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
                if (h != null && h.isClassificaPubblicata()) {
                    // Costruisco la classifica dei team con voto medio
                    StringBuilder sb = new StringBuilder("Classifica Team:\n");
                    int pos = 1;
                    for (Team team : h.getTeams()) {
                        sb.append(pos++).append(". ").append(team.getNomeTeam())
                                .append(" - Voto: ").append(team.getMediaVoti()).append("\n");
                    }
                    JOptionPane.showMessageDialog(this, sb.toString());
                } else {
                    JOptionPane.showMessageDialog(this, "La classifica per questa hackathon non è ancora stata pubblicata.");
                }
            }
        });
    }

    // Carico tutte le hackathon nella combo per la visualizzazione classifica
    private void caricaHackathonPerClassifica() {
        comboHackathonClassifica.removeAllItems();
        for (HackaThon h : HackathonModel.getInstance().getTutti()) {
            comboHackathonClassifica.addItem(h.getTitoloIdentificativo());
        }
    }
}
