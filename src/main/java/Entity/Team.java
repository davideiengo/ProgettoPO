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
            return true;
        } else {
            return false;
        }
    }

    public boolean assegnaVoto(String nomeGiudice, int voto) {
        if (giudiciCheHannoVotato.contains(nomeGiudice)) {
            return false; // già votato
        }

        sommaVoti += voto;
        numeroVoti++;
        giudiciCheHannoVotato.add(nomeGiudice);
        return true;
    }

    public double getMediaVoti() {
        return numeroVoti == 0 ? -1 : (double) sommaVoti / numeroVoti;
    }


    public boolean isVotato() {
        return numeroVoti > 0;
    }

    @Override
    public String toString() {
        double media = getMediaVoti();
        return nomeTeam + " - Voto medio: " + (media == -1 ? "N.D." : media);
    }

    public HackaThon getHackathon() {
        return this.hackathon;
    }

}
