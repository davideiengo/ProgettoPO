package Entity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HackaThon {
    private String titoloIdentificativo;
    private int dataInizio;
    private int dataFine;
    private Organizzatore organizzatore;
    private int numeroMassimoIscritti;
    private int dimensioneMaxTeam;
    private ArrayList<Utente> utentiRegistrati = new ArrayList<>();
    private ArrayList<Team> teamsRegistrati = new ArrayList<>();
    private boolean statusRegistrazioni = false;
    private boolean appartiene = false;


    public HackaThon(int dimensioneMaxTeam, Organizzatore organizzatore, String titoloIdentificativo) {
        this.dimensioneMaxTeam = dimensioneMaxTeam;
        this.organizzatore = organizzatore;
        this.titoloIdentificativo = titoloIdentificativo;
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

    public void setNumeroMassimoIscritti(int numeroMassimoIscritti) {
        this.numeroMassimoIscritti = numeroMassimoIscritti;
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
        if (team.getDimMassimaTeam() <= this.dimensioneMaxTeam && !team.getUtenti().isEmpty() && tuttiRegistrati) {
            teamsRegistrati.add(team);
        } else {
            System.out.println("Il team non è stato registrato: Questo può accadere perchè un utente non è registrato alla stessa hackathon dove si vuole registrare il team, il team è pieno oppure si sta cercando di registrare un team vuoto");
        }
    }


    public void registraUtente(Utente utente){
        utentiRegistrati.add(utente);
    }


    public ArrayList<Utente> getUtentiRegistrati() {
        return utentiRegistrati;
    }

    public void pubblicaClassifica() {
        boolean tuttiVotati = true;
        for (Team elem : this.teamsRegistrati) {
            if (elem.getVotoFinale() == -1) {
                tuttiVotati = false;
                break;
            }
        }

        if (tuttiVotati) {
            Collections.sort(teamsRegistrati, new Comparator<Team>() {
                @Override
                public int compare(Team o1, Team o2) {
                    return Integer.compare(o1.getVotoFinale(), o2.getVotoFinale());
                }
            });
            System.out.println("Classifica Team:");
            for (int i = 0; i < teamsRegistrati.size(); i++) {
                System.out.println((i + 1) + ". " + teamsRegistrati.get(i));
            }
        } else {
            System.out.println("Non tutti i team sono stati votati");
        }

    }

    public void setAppartiene() {
        this.appartiene = true;
    }

    public boolean getAppartiene() {
        return this.appartiene;
    }

    public ArrayList<Team> getTeamsRegistrati(){
        return this.teamsRegistrati;
    }
    public Organizzatore getOrganizzatore() {
        return this.organizzatore;
    }

}
