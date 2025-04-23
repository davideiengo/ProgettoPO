package org.example;
import java.util.ArrayList;

public class Team {
    private int votoFinale = -1; //Default -1, perchè ancora nessun giudice ha votato
    private int dimMassimaTeam;
    private String nomeTeam;
    private ArrayList<Utente> membri;

    public int getDimMassimaTeam() {
        return dimMassimaTeam;
    }

    public void setDimMassimaTeam(int dimMassimaTeam) {
        this.dimMassimaTeam = dimMassimaTeam;
    }

    public String getNomeTeam() {
        return nomeTeam;
    }

    public ArrayList<Utente> getMembri() {
        return membri;
    }

    public void setMembri(ArrayList<Utente> membri) {
        this.membri = membri;
    }

    public Team(int dimensioneMaxTeam, String nomeTeam) {
        this.dimMassimaTeam = dimensioneMaxTeam;
        membri = new ArrayList<>(this.dimMassimaTeam);
        this.nomeTeam = nomeTeam;
    }

    public void aggiungiUtente(Utente utente) {
        if (membri.size() < dimMassimaTeam && utente.getRegistrato()) {
            membri.add(utente);
            System.out.println("Il membro: " + utente.getNome() + " è stato aggiunto al team");
        } else {
            System.out.println("Il team è al completo o non è registrato");
        }
    }

    public void setNomeTeam(String nomeTeam) {
        this.nomeTeam = nomeTeam;
        System.out.println("Il nome del team è: " + nomeTeam);
    }

    public void setVotoFinale(int votoFinale) {
        this.votoFinale = votoFinale;
    }

    public int getVotoFinale() {
        return votoFinale;
    }

    @Override
    public String toString() {
        return nomeTeam + " - Voto Finale: " + votoFinale;
    }
}


