package Entity;
import java.util.ArrayList;

public class Team {
    private int votoFinale = -1; //Default -1, perchè ancora nessun giudice ha votato
    private int dimMassimaTeam;
    private String nomeTeam;
    private ArrayList<Utente> membri;
    private HackaThon hackaThon;


    public Team(HackaThon hackaThon, String nomeTeam) {
        if (hackaThon.getListaTeam().stream().anyMatch(t -> t.getNomeTeam().equalsIgnoreCase(nomeTeam))) {
            throw new IllegalArgumentException("Esiste già un team con questo nome in questa HackaThon!");
        }
        this.dimMassimaTeam = hackaThon.getDimensioneMaxTeam();
        membri = new ArrayList<>(this.dimMassimaTeam);
        this.nomeTeam = nomeTeam;
        hackaThon.aggiungiTeam(this);
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

    public boolean aggiungiUtente(Utente utente) {
        if (!(utente instanceof Giudice) && membri.size() < dimMassimaTeam && utente.getRegistrato()) {
            this.membri.add(utente);
            System.out.println("Il membro: " + utente.getNome() + " è stato aggiunto al team");
            return true;
        } else {
            System.out.println("Il team potrebbe essere al completo, stai provando ad aggiungere un giudice al tuo team, l'utente che hai provato ad inserire non è registrato a questa hackathon");
            return false;
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



