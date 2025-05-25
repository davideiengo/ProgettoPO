package Controllers;

import Entity.HackaThon;
import Entity.Team;
import Entity.Giudice;
import Models.HackathonModel;
import View.HomeView;
import View.ValutazioneTeamView;

import javax.swing.*;

public class ValutazioneTeamController {
    private ValutazioneTeamView view;
    private HackaThon hackathon;
    private Giudice giudice;

    public ValutazioneTeamController(String titoloHackathon, Giudice giudice) {
        this.giudice = giudice;
        this.hackathon = HackathonModel.getInstance().getHackathonByTitolo(titoloHackathon);

        view = new ValutazioneTeamView(titoloHackathon, giudice.getNome());
        popolaTeam();

        view.getBtnAssegnaVoto().addActionListener(e -> assegnaVoto());
        view.setVisible(true);

        view.getBtnPubblicaClassifica().addActionListener(e -> {
            hackathon.pubblicaClassifica();
            JOptionPane.showMessageDialog(view, "Classifica pubblicata con successo!");
        });

        view.getBtnTornaHome().addActionListener(e -> {
            view.dispose(); // chiude la finestra di valutazione
            new HomeView(); // apre la HomeView
        });


    }

    private void popolaTeam() {
        for (Team team : hackathon.getTeamsRegistrati()) {
            view.getComboTeam().addItem(team.getNomeTeam());
        }
    }

    private void assegnaVoto() {
        String nomeTeam = (String) view.getComboTeam().getSelectedItem();
        int voto = (int) view.getSpinnerVoto().getValue();

        for (Team team : hackathon.getTeamsRegistrati()) {
            if (team.getNomeTeam().equalsIgnoreCase(nomeTeam)) {
                giudice.sceltaVoto(team, voto, hackathon);
                JOptionPane.showMessageDialog(view, "Voto assegnato con successo!");
                return;
            }
        }

        JOptionPane.showMessageDialog(view, "Team non trovato.");
    }
}
