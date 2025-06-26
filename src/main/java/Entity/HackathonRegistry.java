package Entity;

import java.util.HashMap;
import java.util.HashSet;

public class HackathonRegistry {
    private static HashSet<String> titoliHackathon = new HashSet<>();
    private static HashMap<String, HackaThon> hackathonMap = new HashMap<>();

    public static boolean registraHackathon(String titolo) {
        if (titolo == null) return false;
        return titoliHackathon.add(titolo.toLowerCase());
    }

    public static boolean esisteHackathon(String titolo) {
        return titoliHackathon.contains(titolo.toLowerCase());
    }

    public static void aggiungiHackathon(HackaThon hackathon) {
        if (hackathon != null) {
            String titolo = hackathon.getTitoloIdentificativo().toLowerCase();
            hackathonMap.put(titolo, hackathon);
        }
    }

}

