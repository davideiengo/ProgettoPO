package Models;

import Entity.Giudice;
import Entity.HackaThon;
import dao.GiudiceDAO;
import PostgresDAO.PostgresGiudiceDAO;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GiudiceModel {
    private static GiudiceModel instance;
    private ArrayList<Giudice> giudici = new ArrayList<>();
    private HashMap<Giudice, String> passwordGiudici = new HashMap<>();
    private GiudiceDAO giudiceDAO = new PostgresGiudiceDAO();


    private GiudiceModel() {}

    public boolean verificaPassword(Giudice g, String password) {
        return giudiceDAO.verificaPassword(g, password);
    }

    // Restituisce dal DB tutti i giudici collegati a un hackathon
    public List<Giudice> trovaPerHackathon(String titoloHackathon) {
        return giudiceDAO.getGiudiciPerHackathon(titoloHackathon);
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
            g.setRegistrato(true);
            h.aggiungiGiudice(g, password);
            giudiceDAO.aggiungiGiudice(g, h, password);
        }
    }


}

