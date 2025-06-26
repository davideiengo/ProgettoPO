package Controllers;

import Entity.HackaThon;
import Entity.Team;
import Entity.Giudice;
import Models.HackathonModel;
import Models.TeamModel;
import PostgresDAO.PostgresVotoDAO;
import View.HomeView;
import View.ValutazioneTeamView;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class ValutazioneTeamController {
    private ValutazioneTeamView view;
    private HackaThon hackathon;
    private Giudice giudice;
    private List<Team> teamList;

    public ValutazioneTeamController(String titoloHackathon, Giudice giudice) {
        this.giudice = giudice;
        this.hackathon = HackathonModel.getInstance().getHackathonByTitolo(titoloHackathon);

        this.view = new ValutazioneTeamView(titoloHackathon, giudice.getNome());
        popolaTeam();

        view.getBtnAssegnaVoto().addActionListener(e -> assegnaVoto());
        view.getBtnPubblicaClassifica().addActionListener(e -> pubblicaClassifica());
        view.getBtnTornaHome().addActionListener(e -> {
            view.dispose();
            new HomeView();
        });

        view.setVisible(true);
    }

    private void popolaTeam() {
        view.getComboTeam().removeAllItems();
        TeamModel teamModel = new TeamModel();
        List<Team> lista = teamModel.trovaPerHackathon(hackathon.getTitoloIdentificativo());

        PostgresVotoDAO votoDAO = new PostgresVotoDAO();
        System.out.println("▶️ Inizio caricamento team per hackathon: " + hackathon.getTitoloIdentificativo());

        for (Team team : lista) {
            System.out.println("🔄 Trovato team: " + team.getNomeTeam());

            // Collega il team all’hackathon
            team.setHackathon(hackathon);

            // Aggiungilo alla lista interna dell'hackathon
            hackathon.aggiungiTeam(team);
            System.out.println("✅ Team '" + team.getNomeTeam() + "' associato all'hackathon " + team.getHackathon().getTitoloIdentificativo());

            // Carica voti esistenti da DB
            Map<String, Integer> votiMap = votoDAO.getMappaVotiPerTeam(team.getNomeTeam());
            for (Map.Entry<String, Integer> entry : votiMap.entrySet()) {
                team.assegnaVoto(entry.getKey(), entry.getValue());
                System.out.println("📊 Voto caricato: " + entry.getValue() + " da giudice " + entry.getKey() + " per team " + team.getNomeTeam());
            }

            // Aggiungi il team alla combo box dell'interfaccia
            view.getComboTeam().addItem(team.getNomeTeam());
        }

        this.teamList = lista;

        System.out.println("✅ Caricamento completato. Team disponibili per la valutazione: " + teamList.size());
    }



    private void assegnaVoto() {
        String nomeTeam = (String) view.getComboTeam().getSelectedItem();
        int voto = (int) view.getSpinnerVoto().getValue();

        for (Team team : teamList) {
            if (team.getNomeTeam().equalsIgnoreCase(nomeTeam)) {
                giudice.sceltaVoto(team, voto, hackathon);
                JOptionPane.showMessageDialog(view, "Voto assegnato con successo!");
                return;
            }
        }

        JOptionPane.showMessageDialog(view, "Errore: team non trovato.");
    }

    private void pubblicaClassifica() {
        List<Team> votati = teamList.stream()
                .filter(Team::isVotato)
                .sorted((a, b) -> Double.compare(b.getMediaVoti(), a.getMediaVoti()))
                .toList();

        if (votati.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nessun team è stato votato.");
            return;
        }

        StringBuilder sb = new StringBuilder("🏆 Classifica Team:\n");
        int pos = 1;
        for (Team team : votati) {
            sb.append(pos++).append(". ").append(team.getNomeTeam())
                    .append(" - Voto medio: ").append(String.format("%.2f", team.getMediaVoti())).append("\n");
        }

        JOptionPane.showMessageDialog(view, sb.toString());
    }
}
