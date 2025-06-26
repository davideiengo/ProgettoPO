package Controllers;

import Entity.TeamRegistry;
import Models.HackathonModel;
import Models.TeamModel;
import PostgresDAO.PostgresTeamDAO;
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
                return;
            }

            team = new Team(selectedHackathon, nomeTeam);

            // Apre la finestra per selezionare membri
            new TeamMembersSelectionView(team, selectedHackathon);
        }
    }

    public void creaTeam() {
        if (team != null) {
            // Verifica che il team abbia almeno un membro
            if (team.getUtenti().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Errore: Non puoi registrare un team vuoto.");
                return;
            }

            // Verifica che non superi la dimensione massima
            if (team.getUtenti().size() > team.getDimMassimaTeam()) {
                JOptionPane.showMessageDialog(view, "Registrazione fallita: Il numero di membri del team supera il limite massimo consentito.");
            } else {
                // Controllo finale per evitare duplicati
                if (TeamRegistry.esisteTeam(team.getNomeTeam())) {
                    JOptionPane.showMessageDialog(view, "Errore: Esiste già un team con questo nome.");
                    return;
                }

                // Aggiunge il team all'hackathon
                selectedHackathon.aggiungiTeam(team);
                selectedHackathon.registraTeam(team);

                // SALVA nel database
                new PostgresTeamDAO().salvaTeam(team);

                JOptionPane.showMessageDialog(view, "Team registrato correttamente!");
            }
        }
    }

    public void tornaAllaHome() {
        view.setVisible(false);
        new HomeView();
    }
}
