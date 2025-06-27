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
            String sql = "INSERT INTO hackathon (titolo, dimensione_max_team, nome_organizzatore, registrazioni_aperte) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, hackathon.getTitoloIdentificativo());
            stmt.setInt(2, hackathon.getDimensioneMaxTeam());
            stmt.setString(3, hackathon.getOrganizzatore().getOrganizzatore());
            stmt.setBoolean(4, hackathon.getAperturaIscrizioni());
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
                boolean registrazioniAperte = rs.getBoolean("registrazioni_aperte");

                Organizzatore org = new Organizzatore(nomeOrg);
                hackathon = HackaThon.ricostruisciDaDB(dim, org, titolo);

                if (registrazioniAperte) {
                    hackathon.permettiIscrizioni(org);
                }
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
                boolean registrazioniAperte = rs.getBoolean("registrazioni_aperte");

                Organizzatore org = new Organizzatore(nomeOrg);
                HackaThon h = HackaThon.ricostruisciDaDB(dim, org, titolo);

                if (registrazioniAperte) {
                    h.permettiIscrizioni(org);
                }

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
            String sql = "UPDATE hackathon SET dimensione_max_team = ?, nome_organizzatore = ?, registrazioni_aperte = ? WHERE titolo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, hackathon.getDimensioneMaxTeam());
            stmt.setString(2, hackathon.getOrganizzatore().getOrganizzatore());
            stmt.setBoolean(3, hackathon.getAperturaIscrizioni()); // salva il campo giusto!
            stmt.setString(4, hackathon.getTitoloIdentificativo());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

