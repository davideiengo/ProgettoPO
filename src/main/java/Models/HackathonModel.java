package Models;

import Entity.HackaThon;
import Entity.Organizzatore;
import dao.HackathonDAO;
import PostgresDAO.PostgresHackathonDAO;


import java.util.ArrayList;

public class HackathonModel {
    private static HackathonModel instance = null;
    private ArrayList<HackaThon> hackathonList = new ArrayList<>();
    private HackathonDAO hackathonDAO = new PostgresHackathonDAO();



    private HackathonModel() {}

    public static HackathonModel getInstance() {
        if (instance == null) {
            instance = new HackathonModel();
        }
        return instance;
    }

    public void aggiungiHackathon(HackaThon h) {
        hackathonList.add(h);
        hackathonDAO.salvaHackathon(h); // Salva nel DB
    }


    public HackaThon getHackathonByTitolo(String titolo) {
        return hackathonDAO.trovaPerTitolo(titolo);
    }

    public void aggiornaRegistrazioni(HackaThon h) {
        hackathonDAO.aggiorna(h);
    }



    public boolean esisteHackathonConTitolo(String titolo) {
        for (HackaThon h : hackathonList) {
            if (h.getTitoloIdentificativo().equalsIgnoreCase(titolo)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<HackaThon> getTutti() {
        return new ArrayList<>(hackathonDAO.trovaTutti());
    }

}



