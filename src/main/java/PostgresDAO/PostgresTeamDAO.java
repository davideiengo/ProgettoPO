package PostgresDAO;

import Entity.Organizzatore;
import dao.TeamDAO;
import database.DBConnection;
import Entity.Team;
import Entity.Utente;
import Entity.HackaThon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresTeamDAO implements TeamDAO {

    @Override
    public void salvaTeam(Team team) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO team (nome, dim_massima, hackathon_titolo) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, team.getNomeTeam());
            stmt.setInt(2, team.getDimMassimaTeam());
            stmt.setString(3, team.getHackathon().getTitoloIdentificativo());
            stmt.executeUpdate();

            // Inserimento membri team
            for (Utente u : team.getUtenti()) {
                String sqlMembro = "INSERT INTO team_membri (team_nome, utente_nome) VALUES (?, ?)";
                PreparedStatement stmtMembro = conn.prepareStatement(sqlMembro);
                stmtMembro.setString(1, team.getNomeTeam());
                stmtMembro.setString(2, u.getNome());
                stmtMembro.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Team trovaPerNome(String nomeTeam) {
        Team team = null;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM team WHERE nome = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomeTeam);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int dim = rs.getInt("dim_massima");
                String titolo = rs.getString("hackathon_titolo");
                HackaThon hackathon = HackaThon.ricostruisciDaDB(dim, new Organizzatore("placeholder"), titolo);


                team = new Team(hackathon, nomeTeam);

                // Recupero membri
                String sqlMembri = "SELECT utente_nome FROM team_membri WHERE team_nome = ?";
                PreparedStatement stmtMembri = conn.prepareStatement(sqlMembri);
                stmtMembri.setString(1, nomeTeam);
                ResultSet rsMembri = stmtMembri.executeQuery();

                while (rsMembri.next()) {
                    Utente u = new Utente(rsMembri.getString("utente_nome"));
                    u.setRegistrato(true);
                    team.aggiungiUtente(u);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return team;
    }

    @Override
    public List<Team> trovaTutti() {
        List<Team> lista = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT nome FROM team";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Team t = trovaPerNome(rs.getString("nome"));
                if (t != null) lista.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void aggiorna(Team team) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE team SET dim_massima = ? WHERE nome = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, team.getDimMassimaTeam());
            stmt.setString(2, team.getNomeTeam());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void elimina(String nomeTeam) {
        try (Connection conn = DBConnection.getConnection()) {
            String sqlMembri = "DELETE FROM team_membri WHERE team_nome = ?";
            PreparedStatement stmtMembri = conn.prepareStatement(sqlMembri);
            stmtMembri.setString(1, nomeTeam);
            stmtMembri.executeUpdate();

            String sql = "DELETE FROM team WHERE nome = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomeTeam);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Team> trovaTeamPerHackathon(String titoloHackathon) {
        List<Team> lista = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT nome FROM team WHERE hackathon_titolo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, titoloHackathon);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nomeTeam = rs.getString("nome");
                Team team = trovaPerNome(nomeTeam);
                if (team != null) {
                    lista.add(team);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

}


