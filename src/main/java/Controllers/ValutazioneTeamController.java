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
        for (Team team : lista) {
            List<Integer> voti = votoDAO.getVotiPerTeam(team.getNomeTeam());

            // Assegna i voti uno per uno (nomeGiudice fittizio)
            int i = 0;
            for (Integer voto : voti) {
                team.assegnaVoto("g" + i++, voto);
            }

            view.getComboTeam().addItem(team.getNomeTeam());
        }

        this.teamList = lista;
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
