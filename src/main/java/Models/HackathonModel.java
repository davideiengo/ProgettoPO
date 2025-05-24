package Models;

import Entity.HackaThon;
import Entity.Organizzatore;

import java.util.ArrayList;

public class HackathonModel {
    private static HackathonModel instance = null;
    private ArrayList<HackaThon> hackathonList = new ArrayList<>();

    private HackathonModel() {}

    public static HackathonModel getInstance() {
        if (instance == null) {
            instance = new HackathonModel();
        }
        return instance;
    }

    public void aggiungiHackathon(HackaThon h) {
        hackathonList.add(h);
    }

    public HackaThon getHackathonByTitolo(String titolo) {
        for (HackaThon h : hackathonList) {
            if (h.getTitoloIdentificativo().equalsIgnoreCase(titolo)) {
                return h;
            }
        }
        return null;
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
        return hackathonList;
    }
}



