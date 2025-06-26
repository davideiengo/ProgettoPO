package Models;

import Entity.HackaThon;
import Entity.Team;
import Entity.Utente;
import dao.TeamDAO;
import PostgresDAO.PostgresTeamDAO;


import java.util.ArrayList;
import java.util.List;

public class TeamModel {
    private TeamDAO teamDAO = new PostgresTeamDAO();

    public List<Team> trovaPerHackathon(String titoloHackathon) {
        return teamDAO.trovaTeamPerHackathon(titoloHackathon);
    }


    public Team creaTeam(String nomeTeam, HackaThon hackathon) {
        return new Team(hackathon, nomeTeam);
    }


}

