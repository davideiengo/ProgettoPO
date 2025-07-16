package Models;

import Entity.Team;
import dao.TeamDAO;
import PostgresDAO.PostgresTeamDAO;
import java.util.List;

/**
 * La classe TeamModel gestisce le operazioni relative ai team all'interno del sistema.
 * Utilizza un'implementazione del DAO per interagire con la base di dati e ottenere informazioni sui team.
 */
public class TeamModel {
    private TeamDAO teamDAO = new PostgresTeamDAO();


    /**
     * Trova una lista di team associati a un determinato hackathon.
     *
     * @param titoloHackathon Il titolo identificativo dell'hackathon per cui si vogliono recuperare i team.
     * @return Una lista di oggetti Team associati all'hackathon specificato. Se non ci sono team,
     *         verrà restituita una lista vuota.
     */
    public List<Team> trovaPerHackathon(String titoloHackathon) {
        return teamDAO.trovaTeamPerHackathon(titoloHackathon);
    }
}

