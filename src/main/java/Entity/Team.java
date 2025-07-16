package Entity;

import java.util.ArrayList;
import java.util.HashSet;
/**
 * La classe {@code Team} rappresenta un team che partecipa a un {@code HackaThon}.
 * Ogni team ha un nome, una dimensione massima, una lista di membri e un sistema di votazione.
 * Il team può aggiungere utenti, ricevere voti e calcolare la media dei voti ricevuti.
 */
public class Team {
    private String nomeTeam;
    private int dimMassimaTeam;
    private ArrayList<Utente> membri;
    private HackaThon hackathon;
    private int sommaVoti = 0;
    private int numeroVoti = 0;
    private HashSet<String> giudiciCheHannoVotato = new HashSet<>();

    /**
     * Costruisce un nuovo {@code Team} per una specifica {@code HackaThon}.
     *
     * @param hackathon La {@code HackaThon} a cui il team partecipa.
     * @param nomeTeam Il nome del team.
     * @throws IllegalArgumentException Se esiste già un team con lo stesso nome nella stessa {@code HackaThon}.
     */
    public Team(HackaThon hackathon, String nomeTeam) {
        if (hackathon.getListaTeam().stream().anyMatch(t -> t.getNomeTeam().equalsIgnoreCase(nomeTeam))) {
            throw new IllegalArgumentException("Esiste già un team con questo nome in questa HackaThon!");
        }


        this.hackathon = hackathon;
        this.nomeTeam = nomeTeam;
        this.dimMassimaTeam = hackathon.getDimensioneMaxTeam();
        this.membri = new ArrayList<>(dimMassimaTeam);
    }

    /**
     * Imposta la {@code HackaThon} a cui il team partecipa.
     *
     * @param hackathon La {@code HackaThon} a cui il team partecipa.
     */
    public void setHackathon(HackaThon hackathon) {
        this.hackathon = hackathon;
    }

    /**
     * Restituisce il nome del team.
     *
     * @return Il nome del team.
     */
    public String getNomeTeam() {
        return nomeTeam;
    }

    /**
     * Restituisce la dimensione massima del team.
     *
     * @return La dimensione massima del team.
     */
    public int getDimMassimaTeam() {
        return dimMassimaTeam;
    }

    /**
     * Restituisce la lista degli utenti (membri) del team.
     *
     * @return La lista dei membri del team.
     */
    public ArrayList<Utente> getUtenti() {
        return membri;
    }

    /**
     * Aggiunge un utente al team, se possibile.
     * L'utente deve essere registrato e non essere un {@code Giudice}.
     *
     * @param utente L'utente da aggiungere.
     * @return {@code true} se l'utente è stato aggiunto con successo, {@code false} altrimenti.
     */
    public boolean aggiungiUtente(Utente utente) {
        if (!(utente instanceof Giudice) && membri.size() < dimMassimaTeam && utente.getRegistrato()) {
            membri.add(utente);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Assegna un voto al team da parte di un giudice.
     * Un giudice può votare solo una volta.
     *
     * @param nomeGiudice Il nome del giudice che assegna il voto.
     * @param voto Il voto assegnato (intero).
     * @return {@code true} se il voto è stato assegnato con successo, {@code false} se il giudice ha già votato.
     */
    public boolean assegnaVoto(String nomeGiudice, int voto) {
        if (giudiciCheHannoVotato.contains(nomeGiudice)) {
            return false; // già votato
        }

        sommaVoti += voto;
        numeroVoti++;
        giudiciCheHannoVotato.add(nomeGiudice);
        return true;
    }

    /**
     * Restituisce la media dei voti ricevuti dal team.
     * Se il team non ha ricevuto voti, restituisce -1.
     *
     * @return La media dei voti o -1 se il team non ha voti.
     */
    public double getMediaVoti() {
        return numeroVoti == 0 ? -1 : (double) sommaVoti / numeroVoti;
    }

    /**
     * Verifica se il team ha ricevuto almeno un voto.
     *
     * @return {@code true} se il team ha ricevuto voti, {@code false} altrimenti.
     */
    public boolean isVotato() {
        return numeroVoti > 0;
    }

    /**
     * Restituisce una rappresentazione in formato stringa del team, inclusa la media dei voti.
     *
     * @return Una stringa che rappresenta il team e il suo voto medio.
     */
    @Override
    public String toString() {
        double media = getMediaVoti();
        return nomeTeam + " - Voto medio: " + (media == -1 ? "N.D." : media);
    }

    /**
     * Restituisce la {@code HackaThon} a cui il team partecipa.
     *
     * @return La {@code HackaThon} associata al team.
     */
    public HackaThon getHackathon() {
        return this.hackathon;
    }

}
