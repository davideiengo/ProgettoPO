package Models;

import Entity.Giudice;
import Entity.HackaThon;

import java.util.ArrayList;
import java.util.HashMap;

public class GiudiceModel {
    private static GiudiceModel instance;
    private ArrayList<Giudice> giudici = new ArrayList<>();
    private HashMap<Giudice, String> passwordGiudici = new HashMap<>();

    private GiudiceModel() {}

    public boolean verificaPassword(Giudice g, String password) {
        return passwordGiudici.getOrDefault(g, "").equals(password);
    }


    public static GiudiceModel getInstance() {
        if (instance == null) {
            instance = new GiudiceModel();
        }
        return instance;
    }

    public void aggiungiGiudice(Giudice g, HackaThon h, String password) {
        if (!giudici.contains(g)) {
            giudici.add(g);
            passwordGiudici.put(g, password);
            g.setRegistrato(true);
            h.aggiungiGiudice(g, password);
        } else if (!passwordGiudici.containsKey(g)) {
            passwordGiudici.put(g, password); // Aggiorna password se non presente
        }
    }

}

