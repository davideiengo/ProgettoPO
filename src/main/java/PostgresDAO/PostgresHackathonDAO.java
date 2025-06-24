package PostgresDAO;

import dao.HackathonDAO;
import database.DBConnection;
import Entity.HackaThon;
import Entity.Organizzatore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresHackathonDAO implements HackathonDAO {

    @Override
    public void salvaHackathon(HackaThon hackathon) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO hackathon (titolo, dimensione_max_team, nome_organizzatore) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, hackathon.getTitoloIdentificativo());
            stmt.setInt(2, hackathon.getDimensioneMaxTeam());
            stmt.setString(3, hackathon.getOrganizzatore().getOrganizzatore());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public HackaThon trovaPerTitolo(String titolo) {
        HackaThon hackathon = null;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM hackathon WHERE titolo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, titolo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int dim = rs.getInt("dimensione_max_team");
                String nomeOrg = rs.getString("nome_organizzatore");
                Organizzatore org = new Organizzatore(nomeOrg);
                hackathon = new HackaThon(dim, org, titolo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hackathon;
    }

    @Override
    public List<HackaThon> trovaTutti() {
        List<HackaThon> lista = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM hackathon";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String titolo = rs.getString("titolo");
                int dim = rs.getInt("dimensione_max_team");
                String nomeOrg = rs.getString("nome_organizzatore");
                Organizzatore org = new Organizzatore(nomeOrg);
                HackaThon h = new HackaThon(dim, org, titolo);
                lista.add(h);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void aggiorna(HackaThon hackathon) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE hackathon SET dimensione_max_team = ?, nome_organizzatore = ? WHERE titolo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, hackathon.getDimensioneMaxTeam());
            stmt.setString(2, hackathon.getOrganizzatore().getOrganizzatore());
            stmt.setString(3, hackathon.getTitoloIdentificativo());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void elimina(String titolo) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM hackathon WHERE titolo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, titolo);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

