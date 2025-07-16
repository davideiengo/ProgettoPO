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
/**
 * La classe {@code TeamController} gestisce la creazione e la registrazione di un team
 * all'interno di un hackathon.
 * Fornisce funzionalità per la selezione del team, la gestione dei membri e la validazione dei dati.
 */
public class TeamController {

    /** Vista per la creazione del team */
    private TeamCreationView view;

    /** Modello per la gestione del team */
    private TeamModel model;

    /** Hackathon selezionato per la registrazione del team */
    private HackaThon selectedHackathon;

    /** Team che viene creato */
    private Team team;

    /**
     * Costruttore della classe {@code TeamController}.
     * Inizializza la vista, il modello e popola la combo box degli hackathon.
     *
     * @param view La vista per la creazione del team.
     */
    public TeamController(TeamCreationView view) {
        this.view = view;
        this.model = new TeamModel();
        popolaComboBox();
    }

    /**
     * Popola la combo box con i titoli degli hackathon disponibili.
     */
    private void popolaComboBox() {
        for (HackaThon h : HackathonModel.getInstance().getTutti()) {
            view.getComboHackathon().addItem(h.getTitoloIdentificativo());
        }
    }

    /**
     * Apre la finestra per la selezione dei membri del team.
     * Controlla se il nome del team è già utilizzato e seleziona l'hackathon.
     */
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

    /**
     * Crea il team dopo aver validato che il team abbia almeno un membro e che non superi
     * il numero massimo di membri.
     * Inoltre, verifica che i membri non siano già assegnati ad altri team nell'hackathon
     * e che non siano giudici.
     */
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

            //  Team con nome duplicato
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

    /**
     * Torna alla schermata principale (Home).
     */
    public void tornaAllaHome() {
        view.setVisible(false);
        new HomeView();
    }
}
