package org.example;
import java.util.ArrayList;

public class Team {
    private int votoFinale = -1; //Default -1, perchè ancora nessun giudice ha votato
    private int dimMassimaTeam;
    private String nomeTeam;
    private ArrayList<Utente> membri;

    public Team(HackaThon hackaThon, String nomeTeam) {
        this.dimMassimaTeam = hackaThon.getDimensioneMaxTeam();
        membri = new ArrayList<>(this.dimMassimaTeam);
        this.nomeTeam = nomeTeam;
    }

    public int getDimMassimaTeam() {
        return dimMassimaTeam;
    }

    public String getNomeTeam() {
        return nomeTeam;
    }

    public ArrayList<Utente> getUtenti() {
        return membri;
    }

    public void aggiungiUtente(Utente utente) {
        if (!(utente instanceof Giudice) && membri.size() < dimMassimaTeam && utente.getRegistrato()) {
            this.membri.add(utente);
            System.out.println("Il membro: " + utente.getNome() + " è stato aggiunto al team");
        }
        else {
            System.out.println("Il team potrebbe essere al completo, stai provando ad aggiungere un giudice al tuo team, l'utente che hai provato ad inserire non è registrato a questa hackathon");
        }
    }

    public void setNomeTeam(String nomeTeam) {
        this.nomeTeam = nomeTeam;
        System.out.println("Il nome del team è: " + nomeTeam);
    }

    public void setVotoFinale(int votoFinale) {
        if (this.votoFinale == -1)
            this.votoFinale = votoFinale + this.votoFinale + 1;
        else{
            this.votoFinale = votoFinale + this.votoFinale;
        }
    }

    public int getVotoFinale() {
        return votoFinale;
    }

    @Override
    public String toString() {
        return nomeTeam + " - Voto Finale: " + votoFinale;
    }
}


