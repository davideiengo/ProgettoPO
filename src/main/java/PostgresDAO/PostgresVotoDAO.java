package PostgresDAO;

import dao.VotoDAO;
import database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresVotoDAO implements VotoDAO {

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

            // ⬇️ AGGIUNGI QUESTO:
            System.out.println("💾 Voto salvato nel DB → Team: " + teamNome + ", Giudice: " + giudiceNome + ", Voto: " + voto);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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

