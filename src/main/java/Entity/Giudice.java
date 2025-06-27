package Entity;

import PostgresDAO.PostgresVotoDAO;

public class Giudice extends Utente {

    public Giudice(Utente utente) {
        super(utente.getNome());
        this.setRegistrato(utente.getRegistrato());
    }

    public void sceltaVoto(Team team, int voto, HackaThon hackathon) {

        boolean stessoHackathon = team.getHackathon()
                .getTitoloIdentificativo()
                .equalsIgnoreCase(hackathon.getTitoloIdentificativo());

        if (!stessoHackathon) {
            System.out.println("⚠️ Il team '" + team.getNomeTeam()
                    + "' non appartiene all'hackathon selezionato.");
            return;
        }

        // se il giudice non ha ancora votato il team, registra voto in RAM …
        boolean nuovoVoto = team.assegnaVoto(this.getNome(), voto);

        if (nuovoVoto) {
            // … e persiste nel database
            new PostgresVotoDAO().salvaVoto(team.getNomeTeam(), this.getNome(), voto);
            System.out.println("Voto salvato: Team=" + team.getNomeTeam()
                    + ", Giudice=" + this.getNome() + ", Voto=" + voto);
        } else {
            System.out.println("⚠️ Il giudice " + this.getNome()
                    + " ha già votato il team " + team.getNomeTeam());
        }
    }
}
