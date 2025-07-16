package PostgresDAO;

import dao.HackathonDAO;
import database.DBConnection;
import Entity.HackaThon;
import Entity.Organizzatore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Implementazione della classe {@code HackathonDAO} per l'interazione con il database PostgreSQL.
 * Gestisce le operazioni CRUD per gli hackathon, inclusi salvataggio, recupero, aggiornamento e ricerca.
 */
public class PostgresHackathonDAO implements HackathonDAO {

    /**
     * Salva un hackathon nel database.
     *
     * @param hackathon L'hackathon da salvare.
     */
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

    /**
     * Recupera un hackathon dal database dato il titolo.
     *
     * @param titolo Il titolo dell'hackathon da cercare.
     * @return L'hackathon trovato, o {@code null} se non esiste.
     */
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

    /**
     * Recupera tutti gli hackathon presenti nel database.
     *
     * @return Una lista di hackathon.
     */
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

    /**
     * Aggiorna un hackathon esistente nel database.
     *
     * @param hackathon L'hackathon con i nuovi dati.
     */
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

