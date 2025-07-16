package Models;

import dao.UtenteDAO;
import PostgresDAO.PostgresUtenteDAO;
import Entity.Utente;
import java.util.List;
/**
 * La classe UtenteModel gestisce le operazioni relative agli utenti all'interno del sistema.
 * È un'implementazione del pattern Singleton che fornisce metodi per salvare, cercare e verificare informazioni sugli utenti.
 */
public class UtenteModel {
    private static UtenteModel instance;
    private UtenteDAO utenteDAO = new PostgresUtenteDAO();

    // Costruttore privato per implementare il pattern Singleton
    private UtenteModel() {}

    /**
     * Restituisce l'istanza unica di <code>UtenteModel</code>. Se non esiste, la crea.
     *
     * @return L'istanza unica di <code>UtenteModel</code>.
     */
    public static UtenteModel getInstance() {
        if (instance == null) {
            instance = new UtenteModel();
        }
        return instance;
    }

    /**
     * Salva un oggetto Utente nel sistema.
     *
     * @param utente L'utente da salvare nel database.
     */
    public void salva(Utente utente) {
        utenteDAO.salvaUtente(utente);
    }

    /**
     * Trova un utente in base al suo nome.
     *
     * @param nome Il nome dell'utente da cercare.
     * @return L'oggetto Utente corrispondente al nome, o <code>null</code> se l'utente non viene trovato.
     */
    public Utente trova(String nome) {
        return utenteDAO.trovaPerNome(nome);
    }

    /**
     * Trova tutti gli utenti associati a un determinato hackathon.
     *
     * @param titoloHackathon Il titolo identificativo dell'hackathon per cui si vogliono recuperare gli utenti.
     * @return Una lista di oggetti <code>Utente</code> associati all'hackathon specificato. Se non ci sono utenti,
     *         verrà restituita una lista vuota.
     */
    public List<Utente> trovaPerHackathon(String titoloHackathon) {
        return utenteDAO.trovaUtentiPerHackathon(titoloHackathon);
    }

    /**
     * Verifica se un utente è un giudice per l'hackathon.
     *
     * @param nomeUtente Il nome dell'utente da verificare.
     * @return <code>true</code> se l'utente è un giudice, <code>false</code> altrimenti.
     */
    public boolean isGiudice(String nomeUtente) {
        return ((PostgresUtenteDAO) utenteDAO).isGiudice(nomeUtente);
    }


    /**
     * Verifica se un utente ha già un team associato a un determinato hackathon.
     *
     * @param nomeUtente Il nome dell'utente da verificare.
     * @param titoloHackathon Il titolo identificativo dell'hackathon.
     * @return true se l'utente ha già un team per l'hackathon, false altrimenti.
     */
    public boolean haGiaUnTeamNellHackathon(String nomeUtente, String titoloHackathon) {
        return ((PostgresUtenteDAO) utenteDAO).utenteHaGiaUnTeamNellHackathon(nomeUtente, titoloHackathon);
    }




}

