package Entity;

import java.util.HashSet;

public class TeamRegistry {
    private static HashSet<String> nomiTeam = new HashSet<>();

    public static boolean registraTeam(String nome) {
        return nomiTeam.add(nome.toLowerCase()); // aggiunge solo se non esiste già
    }

    public static boolean esisteTeam(String nome) {
        return nomiTeam.contains(nome.toLowerCase());
    }
}
