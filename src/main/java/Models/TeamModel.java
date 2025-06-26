package Models;

import Entity.HackaThon;
import Entity.Team;
import dao.TeamDAO;
import PostgresDAO.PostgresTeamDAO;
import java.util.List;

public class TeamModel {
    private TeamDAO teamDAO = new PostgresTeamDAO();

    public List<Team> trovaPerHackathon(String titoloHackathon) {
        return teamDAO.trovaTeamPerHackathon(titoloHackathon);
    }
}

