package Controllers;

import Entity.HackaThon;
import Entity.Team;
import Entity.Giudice;
import Models.HackathonModel;
import View.HomeView;
import View.ValutazioneTeamView;
import Models.TeamModel;
import java.util.List;


import javax.swing.*;

public class ValutazioneTeamController {
    private ValutazioneTeamView view;
    private HackaThon hackathon;
    private Giudice giudice;
    private List<Team> teamList; // <--- salvi i team per votazione


    public ValutazioneTeamController(String titoloHackathon, Giudice giudice) {
        this.giudice = giudice;
        this.hackathon = HackathonModel.getInstance().getHackathonByTitolo(titoloHackathon);

        view = new ValutazioneTeamView(titoloHackathon, giudice.getNome());
        popolaTeam();

        view.getBtnAssegnaVoto().addActionListener(e -> assegnaVoto());
        view.setVisible(true);

        view.getBtnPubblicaClassifica().addActionListener(e -> {
            List<Team> votati = teamList.stream()
                    .filter(Team::isVotato)
                    .sorted((a, b) -> Integer.compare(b.getMediaVoti(), a.getMediaVoti()))
                    .toList();

            if (votati.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Nessun team è stato votato.");
            } else {
                StringBuilder sb = new StringBuilder("🏆 Classifica Team:\n");
                int i = 1;
                for (Team team : votati) {
                    sb.append(i++).append(". ").append(team.getNomeTeam())
                            .append(" - Voto medio: ").append(team.getMediaVoti()).append("\n");
                }
                JOptionPane.showMessageDialog(view, sb.toString());
            }
        });



        view.getBtnTornaHome().addActionListener(e -> {
            view.dispose(); // chiude la finestra di valutazione
            new HomeView(); // apre la HomeView
        });


    }

    private void popolaTeam() {
        view.getComboTeam().removeAllItems();

        // Legge i team dal database usando TeamModel
        TeamModel teamModel = new TeamModel();  // usa new, non getInstance
        List<Team> teamList = teamModel.trovaPerHackathon(hackathon.getTitoloIdentificativo());

        // Salva i team in memoria per uso dopo (valutazione)
        this.teamList = teamList;

        for (Team t : teamList) {
            view.getComboTeam().addItem(t.getNomeTeam());
        }
    }

    private void assegnaVoto() {
        String nomeTeam = (String) view.getComboTeam().getSelectedItem();
        int voto = (int) view.getSpinnerVoto().getValue();

        for (Team team : teamList) {
            if (team.getNomeTeam().equalsIgnoreCase(nomeTeam)) {
                giudice.sceltaVoto(team, voto, hackathon); // 👈 usa hackathon reale!
                JOptionPane.showMessageDialog(view, "Voto assegnato con successo!");
                return;
            }
        }

        JOptionPane.showMessageDialog(view, "Errore: team non trovato.");
    }


}
