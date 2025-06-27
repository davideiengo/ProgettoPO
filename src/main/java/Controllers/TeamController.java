package Controllers;

import Entity.TeamRegistry;
import Entity.Utente;
import Models.HackathonModel;
import Models.TeamModel;
import Models.UtenteModel;
import PostgresDAO.PostgresTeamDAO;
import View.HomeView;
import View.TeamCreationView;

import Entity.HackaThon;
import Entity.Team;
import View.TeamMembersSelectionView;

import javax.swing.*;
import java.util.List;

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
            // Controlla che il team abbia almeno un membro
            if (team.getUtenti().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Errore: Non puoi registrare un team vuoto.");
                return;
            }

            // Controlla che non superi il numero massimo
            if (team.getUtenti().size() > team.getDimMassimaTeam()) {
                JOptionPane.showMessageDialog(view, "Errore: Il numero di membri supera il limite massimo del team.");
                return;
            }

            // Blocco giudici
            for (var utente : team.getUtenti()) {
                if (UtenteModel.getInstance().isGiudice(utente.getNome())) {
                    JOptionPane.showMessageDialog(view,
                            "Errore: \"" + utente.getNome() + "\" è un giudice e non può essere membro di un team.");
                    return;
                }
            }

            // Blocco utenti già assegnati a un team nello stesso hackathon
            for (var utente : team.getUtenti()) {
                boolean giàInTeam = UtenteModel.getInstance()
                        .haGiaUnTeamNellHackathon(utente.getNome(), selectedHackathon.getTitoloIdentificativo());

                if (giàInTeam) {
                    JOptionPane.showMessageDialog(view,
                            "Errore: \"" + utente.getNome() + "\" è già membro di un altro team in questo hackathon.");
                    return;
                }
            }

            //  Team con nome duplicato (in memoria)
            if (TeamRegistry.esisteTeam(team.getNomeTeam())) {
                JOptionPane.showMessageDialog(view, "Errore: Esiste già un team con questo nome.");
                return;
            }

            // Tutto ok: registrazione
            selectedHackathon.aggiungiTeam(team);
            selectedHackathon.registraTeam(team);
            new PostgresTeamDAO().salvaTeam(team);

            JOptionPane.showMessageDialog(view, "✅ Team registrato con successo!");
            view.dispose();
            new HomeView();
        } else {
            JOptionPane.showMessageDialog(view, "Errore: il team non è stato creato.");
        }
    }

    public void tornaAllaHome() {
        view.setVisible(false);
        new HomeView();
    }
}
