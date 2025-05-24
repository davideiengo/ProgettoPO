package Entity;

import java.util.ArrayList;
import java.util.HashMap;

public class GiudiceRegistry {
    private static ArrayList<Giudice> listaGiudici = new ArrayList<>();
    private static HashMap<Giudice, String> passwordGiudici = new HashMap<>();

    public static void aggiungiGiudice(Giudice g, HackaThon h, String password) {
        if (!listaGiudici.contains(g)) {
            listaGiudici.add(g);
            passwordGiudici.put(g, password);
            h.setAppartiene(); // segna appartenenza
        }
    }

    public static ArrayList<Giudice> getGiudici() {
        return listaGiudici;
    }

    public static boolean verificaPassword(Giudice g, String password) {
        return passwordGiudici.getOrDefault(g, "").equals(password);
    }
}
