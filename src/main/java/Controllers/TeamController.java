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
            team = model.creaTeam(view.getTxtNomeTeam().getText(), selectedHackathon);
            new TeamMembersSelectionView(team, selectedHackathon);
        }
    }

    public void creaTeam() {
            if (team != null) {
                if (team.getUtenti().size() > team.getDimMassimaTeam()) {
                    JOptionPane.showMessageDialog(view, "Registrazione fallita: Il numero di membri del team supera il limite massimo consentito.");
                } else {
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
