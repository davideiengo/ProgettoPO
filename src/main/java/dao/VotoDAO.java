package dao;

import java.util.List;

public interface VotoDAO {
    void salvaVoto(String teamNome, String giudiceNome, int voto);
    List<Integer> getVotiPerTeam(String teamNome);
}

