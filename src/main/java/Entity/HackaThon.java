package Entity;

import java.util.ArrayList;
/**
 * La classe <code>HackaThon</code> rappresenta un evento Hackathon, dove gli utenti possono registrarsi, formare team,
 * e partecipare a una competizione. L'hackathon è gestito da un organizzatore e può avere un numero massimo di team.
 * I giudici possono assegnare voti ai team.
 */
public class HackaThon {
    private String titoloIdentificativo;
    private Organizzatore organizzatore;
    private int dimensioneMaxTeam;
    private ArrayList<Utente> utentiRegistrati = new ArrayList<>();
    private ArrayList<Team> teamsRegistrati = new ArrayList<>();
    private boolean statusRegistrazioni = false;
    private boolean appartiene = false;
    private ArrayList<Giudice> giudici = new ArrayList<>();
    private ArrayList<Team> listaTeam = new ArrayList<>();


    /**
     * Costruttore per creare un hackathon con un titolo identificativo, un organizzatore e la dimensione massima del team.
     * Questo costruttore verifica che non esista già un hackathon con lo stesso nome.
     *
     * @param dimensioneMaxTeam La dimensione massima del team che può partecipare all'hackathon.
     * @param organizzatore L'organizzatore dell'hackathon.
     * @param titoloIdentificativo Il titolo identificativo dell'hackathon.
     * @throws IllegalArgumentException Se il titolo è nullo o vuoto, o se esiste già un hackathon con lo stesso nome.
     */
    public HackaThon(int dimensioneMaxTeam, Organizzatore organizzatore, String titoloIdentificativo) {
        this(dimensioneMaxTeam, organizzatore, titoloIdentificativo, true);
    }

    /**
     * Costruttore privato per la creazione di un hackathon senza verifica di duplicati, usato per la ricostruzione da DB.
     *
     * @param dimensioneMaxTeam La dimensione massima del team.
     * @param organizzatore L'organizzatore dell'hackathon.
     * @param titoloIdentificativo Il titolo identificativo dell'hackathon.
     * @param controllaRegistro Flag che determina se deve essere eseguita la verifica per evitare duplicati.
     * @throws IllegalArgumentException Se il titolo è nullo o vuoto, o se esiste già un hackathon con lo stesso nome.
     */
    private HackaThon(int dimensioneMaxTeam, Organizzatore organizzatore, String titoloIdentificativo, boolean controllaRegistro) {
        if (titoloIdentificativo == null || titoloIdentificativo.trim().isEmpty()) {
            throw new IllegalArgumentException("Il titolo dell'hackathon non può essere vuoto o nullo.");
        }

        this.titoloIdentificativo = titoloIdentificativo;

        if (controllaRegistro && HackathonRegistry.esisteHackathon(this.titoloIdentificativo)) {
            throw new IllegalArgumentException("Esiste già un hackathon con questo nome!");
        }

        if (controllaRegistro) {
            HackathonRegistry.registraHackathon(this.titoloIdentificativo);
        }

        this.dimensioneMaxTeam = dimensioneMaxTeam;
        this.organizzatore = organizzatore;
    }

    /**
     * Ricostruisce un hackathon da database, utilizzato per la gestione di hackathon già registrati.
     *
     * @param dimensioneMaxTeam La dimensione massima del team.
     * @param organizzatore L'organizzatore dell'hackathon.
     * @param titolo Il titolo dell'hackathon.
     * @return Un oggetto <code>HackaThon</code> ricostruito.
     */
    public static HackaThon ricostruisciDaDB(int dimensioneMaxTeam, Organizzatore organizzatore, String titolo) {
        HackaThon h = new HackaThon(dimensioneMaxTeam, organizzatore, titolo, false);
        HackathonRegistry.aggiungiHackathon(h);
        return h;
    }

    /**
     * Aggiunge un giudice all'hackathon.
     *
     * @param g Il giudice da aggiungere.
     * @param password La password associata al giudice (necessaria per l'autenticazione).
     */
    public void aggiungiGiudice(Giudice g, String password) {
        if (g != null && password != null && !password.isEmpty() && !giudici.contains(g)) {
            giudici.add(g);
            g.setRegistrato(true);

        }

    }

    /**
     * Restituisce la lista dei giudici registrati all'hackathon.
     *
     * @return Una lista di oggetti <code>Giudice</code> registrati per l'hackathon.
     */
    public ArrayList<Giudice> getGiudici() {
        return giudici;
    }


    /**
     * Permette l'iscrizione di nuovi team, ma solo se l'organizzatore è lo stesso dell'hackathon.
     *
     * @param organizzatore L'organizzatore che sta tentando di abilitare le iscrizioni.
     */
    public void permettiIscrizioni(Organizzatore organizzatore) {
        if (this.organizzatore == organizzatore) {
            this.statusRegistrazioni = true;
        } else {
            System.out.println("L'hackathon non è stata organizzata da: " + organizzatore.getOrganizzatore());
        }
    }

    /**
     * Restituisce il titolo identificativo dell'hackathon.
     *
     * @return Il titolo dell'hackathon.
     */
    public String getTitoloIdentificativo() {
        return this.titoloIdentificativo;
    }

    /**
     * Verifica se le iscrizioni sono aperte.
     *
     * @return <code>true</code> se le iscrizioni sono aperte, <code>false</code> altrimenti.
     */
    public boolean getAperturaIscrizioni() {
        return this.statusRegistrazioni;
    }

    /**
     * Restituisce la dimensione massima del team che può partecipare all'hackathon.
     *
     * @return La dimensione massima del team.
     */
    public int getDimensioneMaxTeam() {
        return this.dimensioneMaxTeam;
    }

    /**
     * Registra un team nell'hackathon.
     *
     * @param team Il team da registrare.
     */
    public void registraTeam(Team team) {
        boolean tuttiRegistrati = true;

        for (Utente utente : team.getUtenti()) {
            if (!utentiRegistrati.contains(utente)) {
                tuttiRegistrati = false;
                break;
            }
        }

        // Controlla se esiste già un team con lo stesso nome (in memoria)
        if (TeamRegistry.esisteTeam(team.getNomeTeam())) {
            System.out.println("Errore: esiste già un team con questo nome.");
            return;
        }

        // Team vuoto
        if (team.getUtenti().isEmpty()) {
            System.out.println("Errore: impossibile registrare un team vuoto.");
            return;
        }

        // Registra il team se rispetta i vincoli
        if (team.getDimMassimaTeam() <= this.dimensioneMaxTeam && tuttiRegistrati) {
            teamsRegistrati.add(team);
            TeamRegistry.registraTeam(team.getNomeTeam());
        }

    }

    /**
     * Registra un utente per partecipare all'hackathon.
     *
     * @param utente L'utente da registrare.
     * @return <code>true</code> se la registrazione è avvenuta con successo, <code>false</code> se l'utente è già registrato.
     */
    public boolean registraUtente(Utente utente) {
        // Controlla se esiste già un utente con lo stesso nome
        if (esisteUtenteConNome(utente.getNome())) {
            System.out.println("Errore: nome utente già registrato.");
            return false; // registrazione fallita
        }
        utentiRegistrati.add(utente);
        return true; // registrazione avvenuta con successo
    }

    /**
     * Verifica se esiste un utente con un dato nome.
     *
     * @param nomeUtente Il nome dell'utente da verificare.
     * @return <code>true</code> se l'utente esiste, <code>false</code> altrimenti.
     */
    public boolean esisteUtenteConNome(String nomeUtente) {
        for (Utente u : utentiRegistrati) {
            if (u.getNome().equalsIgnoreCase(nomeUtente)) {
                return true; // trovato un utente con lo stesso nome
            }
        }
        return false; // nessun utente con quel nome
    }

    /**
     * Restituisce l'organizzatore dell'hackathon.
     *
     * @return L'organizzatore dell'hackathon.
     */
    public Organizzatore getOrganizzatore() {
        return this.organizzatore;
    }

    /**
     * Aggiunge un team alla lista dei team dell'hackathon.
     *
     * @param team Il team da aggiungere.
     */
    public void aggiungiTeam(Team team) {
        listaTeam.add(team);
    }

    /**
     * Restituisce la lista dei team registrati all'hackathon.
     *
     * @return Una lista di oggetti <code>Team</code> registrati.
     */
    public ArrayList<Team> getListaTeam() {
        return listaTeam;
    }

    /**
     * Restituisce la lista degli utenti che sono stati registrati nei team dell'hackathon.
     *
     * @return Una lista di oggetti <code>Utente</code> appartenenti a tutti i team registrati.
     */
    public ArrayList<Utente> getUtentiInTeam() {
        ArrayList<Utente> utentiInTeam = new ArrayList<>();
        for (Team team : teamsRegistrati) {
            utentiInTeam.addAll(team.getUtenti());
        }
        return utentiInTeam;
    }

    /**
     * Restituisce i team registrati all'hackathon.
     *
     * @return Una lista di oggetti <code>Team</code> registrati.
     */
    public ArrayList<Team> getTeams() {
        return teamsRegistrati;
    }
}
