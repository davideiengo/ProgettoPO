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

/**
 * La classe {@code ValutazioneTeamController} gestisce le operazioni di valutazione dei team in un hackathon.
 * Permette ai giudici di assegnare voti ai team e pubblicare la classifica.
 */
public class ValutazioneTeamController {

    /** Vista per la valutazione dei team */
    private final ValutazioneTeamView view;

    /** Hackathon selezionato per la valutazione */
    private final HackaThon hackathon;

    /** Giudice che sta valutando i team */
    private final Giudice giudice;

    /** Lista dei team partecipanti all'hackathon */
    private List<Team> teamList;

    /**
     * Costruttore della classe {@code ValutazioneTeamController}.
     * Inizializza la vista, carica la lista dei team e imposta i listener per le azioni.
     *
     * @param titoloHackathon Il titolo dell'hackathon.
     * @param giudice Il giudice che sta valutando i team.
     */
    public ValutazioneTeamController(String titoloHackathon, Giudice giudice) {
        this.giudice   = giudice;
        this.hackathon = HackathonModel.getInstance()
                .getHackathonByTitolo(titoloHackathon);

        view = new ValutazioneTeamView(titoloHackathon, giudice.getNome());
        caricaTeam();
        initListeners();
        view.setVisible(true);
    }

    /**
     * Inizializza i listener per i bottoni nella vista.
     */
    private void initListeners() {
        view.getBtnAssegnaVoto     ().addActionListener(e -> assegnaVoto());
        view.getBtnPubblicaClassifica().addActionListener(e -> pubblicaClassifica());
        view.getBtnTornaHome       ().addActionListener(e -> {
            view.dispose();
            new HomeView();
        });
    }

    /**
     * Carica la lista dei team partecipanti all'hackathon e li visualizza nella vista.
     * Recupera anche i voti già assegnati in precedenza per ciascun team.
     */
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


    /**
     * Assegna un voto a un team selezionato.
     * Seleziona il team e il voto, quindi lo salva nel sistema.
     */
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

    /**
     * Pubblica la classifica dei team in base ai voti ricevuti.
     * Ordina i team in base alla media dei voti e visualizza la classifica.
     */
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
