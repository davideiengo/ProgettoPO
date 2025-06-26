package Models;

import Entity.HackaThon;
import Entity.Team;
import Entity.Utente;
import dao.TeamDAO;
import PostgresDAO.PostgresTeamDAO;


import java.util.ArrayList;

public class TeamModel {
    private TeamDAO teamDAO = new PostgresTeamDAO();


    public Team creaTeam(String nomeTeam, HackaThon hackathon) {
        return new Team(hackathon, nomeTeam);
    }


}

