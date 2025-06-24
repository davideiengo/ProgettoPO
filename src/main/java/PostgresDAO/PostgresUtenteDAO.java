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

    @Override
    public void aggiorna(Utente utente) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE utente SET registrato = ? WHERE nome = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, utente.getRegistrato());
            stmt.setString(2, utente.getNome());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void elimina(String nome) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM utente WHERE nome = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

