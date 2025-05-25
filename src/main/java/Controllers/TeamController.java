package Controllers;

import Models.HackathonModel;
import Models.TeamModel;
import View.HomeView;
import View.TeamCreationView;

import Entity.HackaThon;
import Entity.Team;
import View.TeamMembersSelectionView;

import javax.swing.*;

public class TeamController {

    private TeamCreationView view;
    private TeamModel model;
    private HackaThon selectedHackathon;
    private Team team;

    public TeamController(TeamCreationView view) {
        this.view = view;
        this.model = new TeamModel();
        popolaComboBox();
    }

    private void popolaComboBox() {
        for (HackaThon h : HackathonModel.getInstance().getTutti()) {
            view.getComboHackathon().addItem(h.getTitoloIdentificativo());
        }
    }

    public void apriSceltaMembri() {
        String titolo = (String) view.getComboHackathon().getSelectedItem();
        selectedHackathon = HackathonModel.getInstance().getHackathonByTitolo(titolo);

        if (selectedHackathon != null) {
            String nomeTeam = view.getTxtNomeTeam().getText();

            // Controllo duplicati
            if (selectedHackathon.getTeams().stream().anyMatch(t -> t.getNomeTeam().equalsIgnoreCase(nomeTeam))) {
                JOptionPane.showMessageDialog(view, "Errore: Esiste già un team con questo nome nell’hackathon selezionato.");
                return; // Blocca la creazione
            }

            // SOLO CREA il team, NON LO REGISTRA né lo aggiunge
            team = model.creaTeam(nomeTeam, selectedHackathon);

            // Apre la finestra per selezionare membri
            new TeamMembersSelectionView(team, selectedHackathon);
        }
    }

    public void creaTeam() {
        if (team != null) {
            if (team.getUtenti().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Errore: Non puoi registrare un team vuoto.");
                return;
            }

            if (team.getUtenti().size() > team.getDimMassimaTeam()) {
                JOptionPane.showMessageDialog(view, "Registrazione fallita: Il numero di membri del team supera il limite massimo consentito.");
            } else {
                // Aggiunta del team solo dopo i controlli
                selectedHackathon.aggiungiTeam(team);
                selectedHackathon.registraTeam(team);
                JOptionPane.showMessageDialog(view, "Team registrato correttamente!");
            }
        }
    }

    public void tornaAllaHome() {
        view.setVisible(false);
        new HomeView();
    }
}
