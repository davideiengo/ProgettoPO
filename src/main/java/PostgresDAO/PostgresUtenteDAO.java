package PostgresDAO;

import dao.UtenteDAO;
import database.DBConnection;
import Entity.Utente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione della classe {@code UtenteDAO} per l'interazione con il database PostgreSQL.
 * Gestisce le operazioni CRUD per gli utenti, inclusi salvataggio, ricerca, associazione a hackathon e verifica.
 */
public class PostgresUtenteDAO implements UtenteDAO {

    /**
     * Salva un utente nel database.
     *
     * @param utente L'utente da salvare.
     */
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

    /**
     * Recupera un utente dal database dato il suo nome.
     *
     * @param nome Il nome dell'utente da cercare.
     * @return L'utente trovato, o {@code null} se non esiste.
     */
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

    /**
     * Salva l'associazione tra un utente e un hackathon nel database.
     *
     * @param utenteNome Il nome dell'utente.
     * @param hackathonTitolo Il titolo dell'hackathon.
     */
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

    /**
     * Recupera una lista di utenti associati a un hackathon dato il titolo dell'hackathon.
     *
     * @param titoloHackathon Il titolo dell'hackathon.
     * @return Una lista di utenti registrati a quel hackathon.
     */
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

    /**
     * Verifica se un utente è un giudice.
     *
     * @param nomeUtente Il nome dell'utente da verificare.
     * @return {@code true} se l'utente è un giudice, {@code false} altrimenti.
     */
    public boolean isGiudice(String nomeUtente) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT 1 FROM giudice WHERE nome = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomeUtente);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // se esiste, è giudice
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Verifica se un utente ha già un team associato in un hackathon specifico.
     *
     * @param nomeUtente Il nome dell'utente da verificare.
     * @param titoloHackathon Il titolo dell'hackathon.
     * @return {@code true} se l'utente è già assegnato a un team nell'hackathon, {@code false} altrimenti.
     */
    public boolean utenteHaGiaUnTeamNellHackathon(String nomeUtente, String titoloHackathon) {

        try (Connection conn = DBConnection.getConnection()) {
            String sql = """
            SELECT 1
            FROM team_membri tm
            JOIN team t ON tm.team_nome = t.nome
            WHERE tm.utente_nome = ? AND t.hackathon_titolo = ?
        """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomeUtente);
            stmt.setString(2, titoloHackathon);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true = già assegnato a un team
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



}

