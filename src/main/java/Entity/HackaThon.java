package Entity;

import java.util.ArrayList;

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


    public HackaThon(int dimensioneMaxTeam, Organizzatore organizzatore, String titoloIdentificativo) {
        this(dimensioneMaxTeam, organizzatore, titoloIdentificativo, true);
    }

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

    public static HackaThon ricostruisciDaDB(int dimensioneMaxTeam, Organizzatore organizzatore, String titolo) {
        HackaThon h = new HackaThon(dimensioneMaxTeam, organizzatore, titolo, false);
        HackathonRegistry.aggiungiHackathon(h);
        return h;
    }

    public void aggiungiGiudice(Giudice g, String password) {
        if (g != null && password != null && !password.isEmpty() && !giudici.contains(g)) {
            giudici.add(g);
            g.setRegistrato(true);

        }

    }

    public ArrayList<Giudice> getGiudici() {
        return giudici;
    }

    public void permettiIscrizioni(Organizzatore organizzatore) {
        if (this.organizzatore == organizzatore) {
            this.statusRegistrazioni = true;
        } else {
            System.out.println("L'hackathon non è stata organizzata da: " + organizzatore.getOrganizzatore());
        }
    }

    public String getTitoloIdentificativo() {
        return this.titoloIdentificativo;
    }

    public boolean getAperturaIscrizioni() {
        return this.statusRegistrazioni;
    }

    public int getDimensioneMaxTeam() {
        return this.dimensioneMaxTeam;
    }

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

    public boolean registraUtente(Utente utente) {
        // Controlla se esiste già un utente con lo stesso nome
        if (esisteUtenteConNome(utente.getNome())) {
            System.out.println("Errore: nome utente già registrato.");
            return false; // registrazione fallita
        }
        utentiRegistrati.add(utente);
        return true; // registrazione avvenuta con successo
    }

    public boolean esisteUtenteConNome(String nomeUtente) {
        for (Utente u : utentiRegistrati) {
            if (u.getNome().equalsIgnoreCase(nomeUtente)) {
                return true; // trovato un utente con lo stesso nome
            }
        }
        return false; // nessun utente con quel nome
    }

    public void setAppartiene() {
        this.appartiene = true;
    }

    public Organizzatore getOrganizzatore() {
        return this.organizzatore;
    }

    public void aggiungiTeam(Team team) {
        listaTeam.add(team);
    }

    public ArrayList<Team> getListaTeam() {
        return listaTeam;
    }

    public ArrayList<Utente> getUtentiInTeam() {
        ArrayList<Utente> utentiInTeam = new ArrayList<>();
        for (Team team : teamsRegistrati) {
            utentiInTeam.addAll(team.getUtenti());
        }
        return utentiInTeam;
    }

    public ArrayList<Team> getTeams() {
        return teamsRegistrati;
    }

}
