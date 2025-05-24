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
            boolean successo = team.assegnaVoto(this.getNome(), voto);
            if (successo) {
                System.out.println("Voto assegnato con successo.");
            }
        } else {
            System.out.println("VOTO NON ASSEGNATO. Controlla che il giudice appartenga all’hackathon e il team sia registrato.");
        }
    }

}

