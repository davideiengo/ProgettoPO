package dao;

import Entity.HackaThon;

import java.util.List;

public interface HackathonDAO {
    void salvaHackathon(HackaThon hackathon);
    HackaThon trovaPerTitolo(String titolo);
    List<HackaThon> trovaTutti();
    void aggiorna(HackaThon hackathon);
    void elimina(String titolo);
}
