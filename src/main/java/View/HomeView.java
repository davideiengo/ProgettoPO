package View;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Controllers.HomeController;
import Entity.HackaThon;
import Entity.Team;
import Models.HackathonModel;

public class HomeView extends JFrame{
    private JPanel homeView;
    private JButton btnRegistraUtente;
    private JButton btnLoginOrganizzatore;
    private JButton btnGestioneTeam;
    private JButton btnAreaGiudice;
    private JComboBox<String> comboHackathonClassifica;
    private JButton btnVisualizzaClassifica;
    HomeController controller = new HomeController(this);


    public HomeView() {
        setContentPane(homeView);
        setName("HomeView");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);


        btnLoginOrganizzatore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                controller.apriOrganizzatore();
            }
        });

        btnRegistraUtente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                controller.apriRegistrazioneUtente();
            }

        });


        btnGestioneTeam.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new TeamCreationView();
            }
        });
        btnAreaGiudice.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.apriLoginGiudice();
            }
        });

        btnVisualizzaClassifica.addActionListener(e -> {
            String titolo = (String) comboHackathonClassifica.getSelectedItem();
            if (titolo != null) {
                HackaThon h = HackathonModel.getInstance().getHackathonByTitolo(titolo);
                if (h != null && h.isClassificaPubblicata()) {
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


        caricaHackathonPerClassifica();

    }

    private void caricaHackathonPerClassifica() {
        comboHackathonClassifica.removeAllItems();
        for (HackaThon h : HackathonModel.getInstance().getTutti()) {
            comboHackathonClassifica.addItem(h.getTitoloIdentificativo());
        }
    }


    public static class Main {
        public static void main(String[] args) {
            new HomeView();
        }
    }
}


