package dao;

import Entity.Team;

import java.util.List;

public interface TeamDAO {
    void salvaTeam(Team team);
    Team trovaPerNome(String nomeTeam);
    List<Team> trovaTeamPerHackathon(String titoloHackathon);

}
