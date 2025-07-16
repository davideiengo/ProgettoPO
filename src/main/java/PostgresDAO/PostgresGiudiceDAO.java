package PostgresDAO;

import dao.GiudiceDAO;
import database.DBConnection;
import Entity.Giudice;
import Entity.HackaThon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Implementazione della classe {@code GiudiceDAO} per l'interazione con il database PostgreSQL.
 * Gestisce l'aggiunta dei giudici, la verifica della loro password e il recupero dei giudici per un hackathon.
 */
public class PostgresGiudiceDAO implements GiudiceDAO {

    /**
     * Aggiunge un nuovo giudice al database per un hackathon specifico.
     *
     * @param giudice Il giudice da aggiungere.
     * @param hackathon L'hackathon a cui il giudice è associato.
     * @param password La password del giudice.
     */
    @Override
    public void aggiungiGiudice(Giudice giudice, HackaThon hackathon, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO giudice (nome, password, hackathon_titolo) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, giudice.getNome());
            stmt.setString(2, password);
            stmt.setString(3, hackathon.getTitoloIdentificativo());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica la password di un giudice nel database.
     *
     * @param giudice Il giudice di cui verificare la password.
     * @param password La password inserita dall'utente.
     * @return {@code true} se la password è corretta, altrimenti {@code false}.
     */
    @Override
    public boolean verificaPassword(Giudice giudice, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT password FROM giudice WHERE nome = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, giudice.getNome());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return password.equals(rs.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Recupera la lista dei giudici associati a un hackathon specifico.
     *
     * @param titoloHackathon Il titolo dell'hackathon per cui recuperare i giudici.
     * @return Una lista di giudici associati all'hackathon.
     */
    @Override
    public List<Giudice> getGiudiciPerHackathon(String titoloHackathon) {
        List<Giudice> lista = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT nome FROM giudice WHERE hackathon_titolo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, titoloHackathon);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Giudice g = new Giudice(new Entity.Utente(rs.getString("nome")));
                g.setRegistrato(true);
                lista.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}

