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
        if (HackathonRegistry.esisteHackathon(titoloIdentificativo)) {
            throw new IllegalArgumentException("Esiste già un hackathon con questo nome!");
        }

        HackathonRegistry.registraHackathon(titoloIdentificativo);
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

        if (TeamRegistry.esisteTeam(team.getNomeTeam())) {
            System.out.println("Errore: esiste già un team con questo nome.");
            return;
        }
        if (team.getDimMassimaTeam() <= this.dimensioneMaxTeam && !team.getUtenti().isEmpty() && tuttiRegistrati) {
            teamsRegistrati.add(team);
            TeamRegistry.registraTeam(team.getNomeTeam());
        } else {
            System.out.println("Il team non è stato registrato: Questo può accadere perchè un utente non è registrato alla stessa hackathon dove si vuole registrare il team, il team è pieno oppure si sta cercando di registrare un team vuoto");
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


    public ArrayList<Utente> getUtentiRegistrati() {
        return utentiRegistrati;
    }

    public boolean esisteUtenteConNome(String nomeUtente) {
        for (Utente u : utentiRegistrati) {
            if (u.getNome().equalsIgnoreCase(nomeUtente)) {
                return true; // trovato un utente con lo stesso nome
            }
        }
        return false; // nessun utente con quel nome
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

    private ArrayList<Team> listaTeam = new ArrayList<>();

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

    public ArrayList<String> getNomiTeam() {
        ArrayList<String> nomi = new ArrayList<>();
        for (Team team : teamsRegistrati) {
            nomi.add(team.getNomeTeam());
        }
        return nomi;
    }


}
