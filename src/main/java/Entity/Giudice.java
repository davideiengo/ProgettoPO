package Entity;

import PostgresDAO.PostgresVotoDAO;

public class Giudice extends Utente {

    public Giudice(Utente utente) {
        super(utente.getNome());
        this.setRegistrato(utente.getRegistrato());
    }

    public void sceltaVoto(Team team, int voto, HackaThon hackathon) {
        boolean esiste = hackathon.getTeams().stream()
                .anyMatch(t -> t.getNomeTeam().equalsIgnoreCase(team.getNomeTeam()));
        if (!esiste) {
            System.out.println("⚠️ Il team '" + team.getNomeTeam() + "' non appartiene all'hackathon.");
            return;
        }

        boolean successo = team.assegnaVoto(this.getNome(), voto);

        if (successo) {
            // ✅ Salva nel DB
            new PostgresVotoDAO().salvaVoto(team.getNomeTeam(), this.getNome(), voto);
            System.out.println("💾 Voto salvato per il team " + team.getNomeTeam());
        } else {
            System.out.println("⚠️ Il giudice " + this.getNome() + " ha già votato il team " + team.getNomeTeam());
        }
    }
}
