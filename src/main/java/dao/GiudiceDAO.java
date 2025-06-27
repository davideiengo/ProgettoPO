package dao;

import Entity.Giudice;
import Entity.HackaThon;

import java.util.List;

public interface GiudiceDAO {
    void aggiungiGiudice(Giudice giudice, HackaThon hackathon, String password);
    boolean verificaPassword(Giudice giudice, String password);
    List<Giudice> getGiudiciPerHackathon(String titoloHackathon);
}
