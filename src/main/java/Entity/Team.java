package Entity;

import java.util.ArrayList;
import java.util.HashSet;

public class Team {
    private String nomeTeam;
    private int dimMassimaTeam;
    private ArrayList<Utente> membri;
    private HackaThon hackathon;


    private int sommaVoti = 0;
    private int numeroVoti = 0;
    private HashSet<String> giudiciCheHannoVotato = new HashSet<>();

    public Team(HackaThon hackathon, String nomeTeam) {
        if (hackathon.getListaTeam().stream().anyMatch(t -> t.getNomeTeam().equalsIgnoreCase(nomeTeam))) {
            throw new IllegalArgumentException("Esiste già un team con questo nome in questa HackaThon!");
        }

        this.hackathon = hackathon;
        this.nomeTeam = nomeTeam;
        this.dimMassimaTeam = hackathon.getDimensioneMaxTeam();
        this.membri = new ArrayList<>(dimMassimaTeam);
    }

    public String getNomeTeam() {
        return nomeTeam;
    }

    public int getDimMassimaTeam() {
        return dimMassimaTeam;
    }

    public ArrayList<Utente> getUtenti() {
        return membri;
    }


    public boolean aggiungiUtente(Utente utente) {
        if (!(utente instanceof Giudice) && membri.size() < dimMassimaTeam && utente.getRegistrato()) {
            membri.add(utente);
            System.out.println("Il membro: " + utente.getNome() + " è stato aggiunto al team");
            return true;
        } else {
            System.out.println("Errore: il team è pieno, l'utente non è registrato, oppure è un giudice.");
            return false;
        }
    }

    // Aggiungi un voto da un giudice (solo se non ha già votato)
    public boolean assegnaVoto(String nomeGiudice, int voto) {
        if (giudiciCheHannoVotato.contains(nomeGiudice)) {
            System.out.println("Il giudice " + nomeGiudice + " ha già votato questo team.");
            return false;
        }

        sommaVoti += voto;
        numeroVoti++;
        giudiciCheHannoVotato.add(nomeGiudice);
        return true;
    }

    public int getMediaVoti() {
        return numeroVoti == 0 ? -1 : sommaVoti / numeroVoti;
    }

    public int getNumeroVoti() {
        return numeroVoti;
    }

    @Override
    public String toString() {
        int media = getMediaVoti();
        return nomeTeam + " - Voto medio: " + (media == -1 ? "N.D." : media);
    }
}




