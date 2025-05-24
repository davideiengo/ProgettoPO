package Entity;

public class Giudice extends Utente {
    public Giudice(Utente utente) {
        super(utente.getNome());
        this.setRegistrato(utente.getRegistrato());
    }

    public void sceltaVoto(Team team, int voto, HackaThon hackathon) {
        if (!hackathon.getTeams().contains(team)) return;

        boolean successo = team.assegnaVoto(this.getNome(), voto);
        if (!successo) {
            System.out.println("⚠ Il giudice ha già votato questo team.");
        }
    }
}
