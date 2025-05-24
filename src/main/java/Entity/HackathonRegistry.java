package Entity;

import java.util.HashSet;

public class HackathonRegistry {
    private static HashSet<String> titoliHackathon = new HashSet<>();

    public static boolean registraHackathon(String titolo) {
        return titoliHackathon.add(titolo.toLowerCase()); // aggiunge solo se non esiste
    }

    public static boolean esisteHackathon(String titolo) {
        return titoliHackathon.contains(titolo.toLowerCase());
    }
}
