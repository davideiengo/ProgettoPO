package Controllers;

import Entity.Giudice;
import Entity.HackaThon;
import Entity.Team;
import Models.HackathonModel;
import Models.TeamModel;
import PostgresDAO.PostgresVotoDAO;
import View.HomeView;
import View.ValutazioneTeamView;

import javax.swing.*;
import java.util.List;

public class ValutazioneTeamController {

    private final ValutazioneTeamView view;
    private final HackaThon hackathon;
    private final Giudice giudice;
    private List<Team> teamList;

    public ValutazioneTeamController(String titoloHackathon, Giudice giudice) {
        this.giudice   = giudice;
        this.hackathon = HackathonModel.getInstance()
                .getHackathonByTitolo(titoloHackathon);

        view = new ValutazioneTeamView(titoloHackathon, giudice.getNome());
        caricaTeam();
        initListeners();
        view.setVisible(true);
    }

    private void initListeners() {
        view.getBtnAssegnaVoto     ().addActionListener(e -> assegnaVoto());
        view.getBtnPubblicaClassifica().addActionListener(e -> pubblicaClassifica());
        view.getBtnTornaHome       ().addActionListener(e -> {
            view.dispose();
            new HomeView();
        });
    }

    /** Carica team + voti dal DB e li mostra nella combo-box */
    private void caricaTeam() {
        view.getComboTeam().removeAllItems();

        teamList = new TeamModel()
                .trovaPerHackathon(hackathon.getTitoloIdentificativo());

        PostgresVotoDAO votoDAO = new PostgresVotoDAO();

        for (Team team : teamList) {
            // recupera tutti i voti già presenti a DB
            int i = 0;
            for (int voto : votoDAO.getVotiPerTeam(team.getNomeTeam())) {
                team.assegnaVoto("db" + i++, voto); // giudice fittizio, serve solo a popolare la media
            }
            view.getComboTeam().addItem(team.getNomeTeam());
        }
    }

    /** Assegna un nuovo voto al team selezionato */
    private void assegnaVoto() {
        String nomeTeam = (String) view.getComboTeam().getSelectedItem();
        int voto        = (int)    view.getSpinnerVoto().getValue();

        for (Team t : teamList) {
            if (t.getNomeTeam().equalsIgnoreCase(nomeTeam)) {
                giudice.sceltaVoto(t, voto, hackathon);
                JOptionPane.showMessageDialog(view, "Voto registrato con successo!");
                return;
            }
        }
        JOptionPane.showMessageDialog(view, "Errore: team non trovato.");
    }

    /** Calcola e mostra la classifica */
    private void pubblicaClassifica() {
        var votati = teamList.stream()
                .filter(Team::isVotato)
                .sorted((a, b) ->
                        Double.compare(b.getMediaVoti(), a.getMediaVoti()))
                .toList();

        if (votati.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nessun team è stato votato.");
            return;
        }

        StringBuilder sb = new StringBuilder("🏆 Classifica Team:\n");
        int pos = 1;
        for (Team t : votati) {
            sb.append(pos++).append(". ")
                    .append(t.getNomeTeam())
                    .append(" – Voto medio: ")
                    .append(String.format("%.2f", t.getMediaVoti()))
                    .append('\n');
        }
        JOptionPane.showMessageDialog(view, sb.toString());
    }
}
