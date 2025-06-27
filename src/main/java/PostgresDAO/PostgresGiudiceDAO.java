package PostgresDAO;

import dao.GiudiceDAO;
import database.DBConnection;
import Entity.Giudice;
import Entity.HackaThon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresGiudiceDAO implements GiudiceDAO {

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

