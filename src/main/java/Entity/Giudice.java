package Entity;

public class Giudice extends Utente {
    public Giudice(Utente utente) {
        super(utente.getNome());
        this.setRegistrato(utente.getRegistrato());
    }

    public void creaProblema(HackaThon hackaThon) {
        if (hackaThon.getAppartiene()) {
            System.out.println("Ecco il problema per l'hackaThon: " + hackaThon.getTitoloIdentificativo());
        } else {
            System.out.println("Soltanto l'utente Giudice può pubblicare il problema, controlla se esso appartiene all'hackathon dove vuoi pubblicare il problema");
        }
    }

    public void sceltaVoto(Team team, int voto, HackaThon hackaThon) {
        if (hackaThon.getAppartiene() && hackaThon.getTeamsRegistrati().contains(team)) {
            team.setVotoFinale(voto);
        } else {
            System.out.println("VOTO NON ASSEGNATO, RICORDA CHE: Soltanto gli utenti selezionati come giudici posso assegnare voti unicamente a team registrati");
        }
    }
}

