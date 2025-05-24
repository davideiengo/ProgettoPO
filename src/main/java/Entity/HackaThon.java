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
    private ArrayList<Giudice> giudici = new ArrayList<>();

    public HackaThon(int dimensioneMaxTeam, Organizzatore organizzatore, String titoloIdentificativo) {
        if (titoloIdentificativo == null || titoloIdentificativo.trim().isEmpty()) {
            throw new IllegalArgumentException("Il titolo dell'hackathon non può essere vuoto o nullo.");
        }

        this.titoloIdentificativo = titoloIdentificativo;

        if (HackathonRegistry.esisteHackathon(this.titoloIdentificativo)) {
            throw new IllegalArgumentException("Esiste già un hackathon con questo nome!");
        }

        HackathonRegistry.registraHackathon(this.titoloIdentificativo);

        this.dimensioneMaxTeam = dimensioneMaxTeam;
        this.organizzatore = organizzatore;
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
        if (team.getUtenti().isEmpty()) {
            System.out.println("Errore: impossibile registrare un team vuoto.");
            return;
        }

        if (team.getDimMassimaTeam() <= this.dimensioneMaxTeam && tuttiRegistrati) {
            teamsRegistrati.add(team);
            TeamRegistry.registraTeam(team.getNomeTeam());
        } else {
            System.out.println("Il team non è stato registrato: Un utente potrebbe non essere registrato, oppure il team è troppo grande.");
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
