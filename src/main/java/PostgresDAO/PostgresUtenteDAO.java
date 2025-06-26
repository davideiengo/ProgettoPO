package PostgresDAO;

import dao.UtenteDAO;
import database.DBConnection;
import Entity.Utente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresUtenteDAO implements UtenteDAO {

    @Override
    public void salvaUtente(Utente utente) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO utente (nome, registrato) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, utente.getNome());
            stmt.setBoolean(2, utente.getRegistrato());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Utente trovaPerNome(String nome) {
        Utente utente = null;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM utente WHERE nome = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                utente = new Utente(rs.getString("nome"));
                utente.setRegistrato(rs.getBoolean("registrato"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return utente;
    }

    @Override
    public List<Utente> trovaTutti() {
        List<Utente> utenti = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM utente";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Utente u = new Utente(rs.getString("nome"));
                u.setRegistrato(rs.getBoolean("registrato"));
                utenti.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return utenti;
    }

    public void salvaAssociazioneUtenteHackathon(String utenteNome, String hackathonTitolo) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO utente_hackathon (utente_nome, hackathon_titolo) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, utenteNome);
            stmt.setString(2, hackathonTitolo);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Utente> trovaUtentiPerHackathon(String titoloHackathon) {
        List<Utente> utenti = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = """
            SELECT u.nome, u.registrato
            FROM utente u
            JOIN utente_hackathon uh ON u.nome = uh.utente_nome
            WHERE uh.hackathon_titolo = ?
        """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, titoloHackathon);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Utente u = new Utente(rs.getString("nome"));
                u.setRegistrato(rs.getBoolean("registrato"));
                utenti.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return utenti;
    }

}

