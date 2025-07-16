package Entity;

import PostgresDAO.PostgresVotoDAO;
/**
 * La classe <code>Giudice</code> rappresenta un giudice che ha la possibilità di assegnare un voto ai team partecipanti a un hackathon.
 * Estende la classe <code>Utente</code> e implementa la logica per la gestione dei voti assegnati ai team.
 */
public class Giudice extends Utente {

    /**
     * Costruttore della classe <code>Giudice</code>. Crea un oggetto giudice a partire da un utente esistente.
     *
     * @param utente L'oggetto <code>Utente</code> da cui creare il giudice, con il nome e lo stato di registrazione.
     */
    public Giudice(Utente utente) {
        super(utente.getNome());
        this.setRegistrato(utente.getRegistrato());
    }

    /**
     * Consente al giudice di assegnare un voto a un team durante un hackathon. Se il team appartiene all'hackathon
     * selezionato, il voto viene salvato sia in memoria (RAM) che nel database.
     * Se il giudice ha già votato per il team, il voto non viene registrato nuovamente.
     *
     * @param team Il team a cui il giudice assegna il voto.
     * @param voto Il voto da assegnare al team.
     * @param hackathon L'hackathon per cui viene registrato il voto.
     */
    public void sceltaVoto(Team team, int voto, HackaThon hackathon) {

        boolean stessoHackathon = team.getHackathon()
                .getTitoloIdentificativo()
                .equalsIgnoreCase(hackathon.getTitoloIdentificativo());

        if (!stessoHackathon) {
            System.out.println("⚠️ Il team '" + team.getNomeTeam()
                    + "' non appartiene all'hackathon selezionato.");
            return;
        }

        // se il giudice non ha ancora votato il team, registra voto in RAM …
        boolean nuovoVoto = team.assegnaVoto(this.getNome(), voto);

        if (nuovoVoto) {
            // … e persiste nel database
            new PostgresVotoDAO().salvaVoto(team.getNomeTeam(), this.getNome(), voto);
            System.out.println("Voto salvato: Team=" + team.getNomeTeam()
                    + ", Giudice=" + this.getNome() + ", Voto=" + voto);
        } else {
            System.out.println("⚠️ Il giudice " + this.getNome()
                    + " ha già votato il team " + team.getNomeTeam());
        }
    }
}
