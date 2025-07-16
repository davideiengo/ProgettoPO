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
/**
 * Implementazione della classe {@code TeamDAO} per l'interazione con il database PostgreSQL.
 * Gestisce le operazioni CRUD per i team, inclusi salvataggio, recupero, e ricerca.
 */
public class PostgresTeamDAO implements TeamDAO {


    /**
     * Salva un team nel database.
     *
     * @param team Il team da salvare.
     */
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


    /**
     * Recupera un team dal database dato il nome.
     *
     * @param nomeTeam Il nome del team da cercare.
     * @return Il team trovato, o {@code null} se non esiste.
     */
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

    /**
     * Recupera tutti i team appartenenti ad un hackathon dato il titolo.
     *
     * @param titoloHackathon Il titolo dell'hackathon.
     * @return Una lista di team che partecipano all'hackathon.
     */
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


