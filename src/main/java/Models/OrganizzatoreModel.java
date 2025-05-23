package Models;

import Entity.HackaThon;
import Entity.Organizzatore;

import java.util.ArrayList;

public class OrganizzatoreModel {
    public static ArrayList<Organizzatore> organizzatori = new ArrayList<>();

    public HackaThon creaOrganizzatoreEHackathon(String nome, String titolo, int dimensioneMaxTeam) {
        Organizzatore o = new Organizzatore(nome);
        organizzatori.add(o);
        return new HackaThon(dimensioneMaxTeam, o, titolo);
    }
}

