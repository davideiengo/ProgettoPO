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

    public void sceltaVoto(Team team, int voto, HackaThon hackathon) {
        if (!hackathon.getTeams().contains(team)) return;
        boolean successo = team.assegnaVoto(this.getNome(), voto);
        if (!successo) {
            System.out.println("Questo giudice ha già votato questo team.");
        }
    }



}

