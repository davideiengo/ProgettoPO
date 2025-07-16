package PostgresDAO;

import dao.VotoDAO;
import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Implementazione della classe {@code VotoDAO} per l'interazione con il database PostgreSQL.
 * Gestisce le operazioni relative ai voti dei team, inclusi il salvataggio e il recupero dei voti.
 */
public class PostgresVotoDAO implements VotoDAO {

    /**
     * Salva un voto dato da un giudice a un team nel database.
     * Se esiste già un voto per quel team da parte dello stesso giudice, il voto viene aggiornato.
     *
     * @param teamNome Il nome del team.
     * @param giudiceNome Il nome del giudice.
     * @param voto Il voto da assegnare.
     */
    @Override
    public void salvaVoto(String teamNome, String giudiceNome, int voto) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = """
            INSERT INTO voto (team_nome, giudice_nome, voto)
            VALUES (?, ?, ?)
            ON CONFLICT (team_nome, giudice_nome)
            DO UPDATE SET voto = EXCLUDED.voto
        """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, teamNome);
            stmt.setString(2, giudiceNome);
            stmt.setInt(3, voto);
            stmt.executeUpdate();

            System.out.println("Voto salvato nel DB → Team: " + teamNome + ", Giudice: " + giudiceNome + ", Voto: " + voto);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera tutti i voti assegnati a un team specifico.
     *
     * @param teamNome Il nome del team.
     * @return Una lista di voti assegnati al team.
     */
    @Override
    public List<Integer> getVotiPerTeam(String teamNome) {
        List<Integer> voti = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT voto FROM voto WHERE team_nome = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, teamNome);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                voti.add(rs.getInt("voto"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return voti;
    }


}

