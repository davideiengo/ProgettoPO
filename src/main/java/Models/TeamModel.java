package Models;

import Entity.HackaThon;
import Entity.Team;
import Entity.Utente;

import java.util.ArrayList;

public class TeamModel {

    public Team creaTeam(String nomeTeam, HackaThon hackathon) {
        return new Team(hackathon, nomeTeam);
    }
}

